package com.lab.certoplast.app;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Looper;
import android.text.TextUtils;

import com.lab.certoplast.R;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 应用程序异常类：用于捕获异常和提示错误信息
 * 
 * @author
 * @version 1.0
 * @created
 */
public class AppException extends Exception implements UncaughtExceptionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -117762674324780233L;

	private final static boolean Debug = false;// 是否保存错误日志

	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private AppException() {
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	public enum TaskError {
		// 无网络链接
		noneNetwork,
		// 连接超时
		timeout,
		// 返回数据不合法
		resultIllegal
	}

	private String errorCode;

	private String errorMsg;

	public AppException(String errorCode, String errorMsg) {
		this(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public AppException(String errorCode) {
		this.errorCode = errorCode;

		try {
			TaskError error = TaskError.valueOf(errorCode);
			if (TaskError.noneNetwork == error)
				errorMsg = AppContext.getInstance().getResources()
						.getString(R.string.noneNetwork);
			else if (TaskError.timeout == error)
				errorMsg = AppContext.getInstance().getResources()
						.getString(R.string.timeout);
			else if (TaskError.resultIllegal == error)
				errorMsg = AppContext.getInstance().getResources()
						.getString(R.string.resultIllegal);
		} catch (Exception e) {
		}
	}

	@Override
	public String getMessage() {
		if (!TextUtils.isEmpty(errorMsg))
			return errorMsg;

		return super.getMessage();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}


	/**
	 * 获取APP异常崩溃处理对象
	 * 
	 * @param
	 * @return
	 */
	public static AppException getAppExceptionHandler() {
		return new AppException();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}

	}

	/**
	 * 自定义异常处理:收集错误信息&发送错误报告
	 * 
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}

		final Context context = AppManager.getAppManager().currentActivity();

		if (context == null) {
			return false;
		}

		final String crashReport = getCrashReport(context, ex);
		// 显示异常信息&发送报告
		new Thread() {
			public void run() {
				Looper.prepare();
				sendAppCrashReport(context, crashReport);
				Looper.loop();
			}

		}.start();
		return true;
	}

	/**
	 * 发送App异常崩溃报告
	 * 
	 * @param cont
	 * @param crashReport
	 */
	public static void sendAppCrashReport(final Context cont,
			final String crashReport) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
//		builder.setIcon(android.R.drawable.ic_dialog_info);
//		builder.setTitle(R.string.app_error);
//		builder.setMessage(R.string.app_error_message);
//		builder.setPositiveButton(R.string.submit_report,
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						// 发送异常报告
//						MobclickAgent.reportError(cont, crashReport);
//						// 退出
//						AppManager.getAppManager().AppExit(cont);
//					}
//				});
//		builder.setNegativeButton(R.string.sure,
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						// 退出
//						AppManager.getAppManager().AppExit(cont);
//					}
//				});
//		builder.show();
	}

	/**
	 * 获取APP崩溃异常报告
	 * 
	 * @param ex
	 * @return
	 */
	private String getCrashReport(Context context, Throwable ex) {
		PackageInfo pinfo = ((AppContext) context.getApplicationContext())
				.getPackageInfo();
		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Version: " + pinfo.versionName + "("
				+ pinfo.versionCode + ")\n");
		exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE
				+ "(" + android.os.Build.MODEL + ")\n");
		exceptionStr.append("Exception: " + ex.getMessage() + "\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString() + "\n");
		}
		return exceptionStr.toString();
	}

}
