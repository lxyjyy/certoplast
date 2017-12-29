package com.lab.certoplast.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.lab.certoplast.api.ApiClient;
import com.lab.certoplast.app.AppContext;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.Constant;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.utils.Sputil;
import com.lab.certoplast.utils.StringUtils;
import com.lab.certoplast.utils.ThreadPoolManager;

import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Vector;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends Activity {

    protected static String TAG;

    protected AppContext appContext;
    protected Sputil sputil;
    protected Context mContext;
    private ThreadPoolManager threadPoolManager;
    private ProgressDialog dialog;

    private Unbinder mUnbinder;

    private List<BaseTask> record = new Vector<BaseTask>();

    public BaseActivity() {
        threadPoolManager = ThreadPoolManager.getInstance();
    }

    public static enum NetType {
        POST, GET, UPLOADFILE, DELETE, PUT
    }

    public int getRecord() {

        return record.size();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        // TODO Auto-geneated method stub
        super.onCreate(bundle);

        setContentView(initView());
        TAG = this.getClass().getSimpleName();
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initConfigure();
        initData();

    }

    protected abstract View initView();

    protected abstract void initData();


    public void showProgress(Activity context, String message) {
        if ((!this.isFinishing()) && (dialog == null)) {
            dialog = new ProgressDialog(this);
        }

        dialog.setMessage(message);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void closeProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void initConfigure() {
        mContext = this;
        if (null == appContext) {
            appContext = AppContext.getInstance();
        }
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        if (null == sputil) {
            sputil = new Sputil(this, Constant.PRE_NAME);
        }

    }

    /**
     * Activity跳转
     *
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public void redictToActivity(Context context, Class<?> targetActivity,
                                 Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        EventBus.getDefault().unregister(this);
        record.clear();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();

        record = null;
        threadPoolManager = null;

        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     *
     * @author Mathew
     *
     */
    @SuppressWarnings("unchecked")
    class BaseHandler extends Handler {
        private Context context;
        private DataCallback callBack;
        private RequestVo reqVo;

        public BaseHandler(Context context, DataCallback callBack,
                           RequestVo reqVo) {
            this.context = context;
            this.callBack = callBack;
            this.reqVo = reqVo;

        }

        public void handleMessage(Message msg) {
            closeProgress();
            if (msg.what == Constant.SUCCESS) {
                // 请求成功并且obj!=null
                callBack.processData(msg.obj, true);

            } else if (msg.what == Constant.APP_Exception) {
                callBack.onFailure((AppException) msg.obj);
            } else if (msg.what == Constant.ERROR) {
                callBack.onError((ErrorMessage) msg.obj);
            } else if (msg.what == Constant.LOGOUT) {
                // 登陆过期
                // finish 所有的activity ,然后跳转到登陆界面
//				AppManager.getAppManager().finishAllActivity();
//				startActivity(new Intent(BaseActivity.this, LoginActivity.class));
//				UiCommon.INSTANCE.showTip("登陆过期，请重新登陆!");

            }
        }
    }

    class BaseTask implements Runnable {
        private Context context;
        private RequestVo reqVo;
        private Handler handler;
        private NetType object;

        public BaseTask(Context context, RequestVo reqVo, Handler handler,
                        NetType object) {
            this.context = context;
            this.reqVo = reqVo;
            this.handler = handler;
            this.object = object;
        }

        @Override
        public void run() {
            Object obj = null;
            Message msg = Message.obtain();
            try {
                switch (object) {
                    case GET:
                        obj = ApiClient.http_get(appContext, reqVo);
                        break;

                    case POST:
                        obj = ApiClient._post(appContext, reqVo);
                        break;
                }
                if (obj instanceof ApiClient.Status) {

                    msg.obj = obj;
                    msg.what = Constant.LOGOUT;
                } else if (obj instanceof ErrorMessage) {
                    msg.obj = obj;
                    msg.what = Constant.ERROR;
                } else {
                    msg.what = Constant.SUCCESS;
                    msg.obj = obj;

                }
            } catch (AppException e) {
                msg.what = Constant.APP_Exception;
                msg.obj = e;
            }

            removeCurrentThread();
            handler.sendMessage(msg);
        }

    }

    private void removeCurrentThread() {
        if (record != null && record.size() > 0) {
            record.remove(this);
        }
    }


    /**
     * 从服务器上获取数据，并回调处理 get方式
     *
     * @param reqVo
     * @param callBack
     */
    protected void doGet(RequestVo reqVo, DataCallback callBack) {
        if (reqVo.isShowDialog) {
            showProgress(this, StringUtils.isEmpty(reqVo.Message) ? "正在获取数据..."
                    : reqVo.Message);
        }
        BaseHandler handler = new BaseHandler(this, callBack, reqVo);
        BaseTask taskThread = new BaseTask(this, reqVo, handler, NetType.GET);
        record.add(taskThread);
        this.threadPoolManager.addTask(taskThread);
    }

    /**
     * 从服务器上获取数据，并回调处理 post方式
     *
     * @param reqVo
     * @param callBack
     */
    protected void doPost(RequestVo reqVo, DataCallback callBack) {

        if (reqVo.isShowDialog) {
            showProgress(this, TextUtils.isEmpty(reqVo.Message) ? "正在上传数据..."
                    : reqVo.Message);
        }
        BaseHandler handler = new BaseHandler(this, callBack, reqVo);
        BaseTask taskThread = new BaseTask(this, reqVo, handler, NetType.POST);
        record.add(taskThread);
        this.threadPoolManager.addTask(taskThread);
    }

}
