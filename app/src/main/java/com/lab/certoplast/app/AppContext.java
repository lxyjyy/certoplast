package com.lab.certoplast.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import com.lab.certoplast.bean.IP;
import com.lab.certoplast.bean.User;

import java.util.Hashtable;
import java.util.Properties;

public class AppContext extends Application {

	public static String TAG;
	private boolean login = false; // 登录状态

	// private IECManager ecManager;

	private static AppContext myAppContext = null;

	public static AppContext getInstance() {
		return myAppContext;
	}

	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();



	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 注册App异常崩溃处理器 //开发时候未注册异常崩溃处理器
//		Thread.setDefaultUncaughtExceptionHandler(AppException
//				.getAppExceptionHandler());

		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		// SDKInitializer.initialize(this);


		TAG = this.getClass().getSimpleName();
		// 由于Application类本身已经单例，所以直接按以下处理即可。
		myAppContext = this;

	}


	/**
	 * 用户注销
	 */
	public void Logout() {
		this.login = false;
	}


	/**
	 * 用户是否登录
	 *
	 * @return
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}


	public String ipPort(){
		IP ip = getIPPort();
		String host = ip.getIpAddress() + ":" + ip.getPort();
		return host;
	}

	/**
	 * 将对象保存到内存缓存中
	 * 
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {
		memCacheRegion.put(key, value);
	}



	/**
	 * 初始化用户登录信息
	 */
	public void initLoginInfo() {
		User loginUser = getLoginInfo();
		if (loginUser != null && !TextUtils.isEmpty(loginUser.getUser_Name())) {
			// 用户名和密码不为空
			this.login = true;
		} else {
			this.Logout();
		}
	}


	/**
	 * 保存登录信息
	 *
	 * @param
	 * @param
	 */
	public void saveLoginInfo(final User user) {
		setProperties(new Properties() {
			{
				setProperty("user.username", user.getUser_Name());
			}
		});
	}

	public void saveIPPort(final IP ip){
		setProperties(new Properties() {
			{
				setProperty("ip.Address", ip.getIpAddress());
				setProperty("ip.port",ip.getPort());
			}
		});
	}

	public IP getIPPort(){
		IP ip = new IP();
		ip.setIpAddress(getProperty("ip.Address"));
		ip.setPort(getProperty("ip.port"));
		return ip;
	}


	/**
	 * 清除登录信息
	 */
	public void cleanLoginInfo() {
		removeProperty("user.username");
	}

	/**
	 * 获取登录信息
	 *
	 * @return
	 */
	public User getLoginInfo() {
		User lu = new User();
		lu.setUser_Name(getProperty("user.username"));

		return lu;
	}


	public void setProperties(Properties ps) {
		AppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties() {
		return AppConfig.getAppConfig(this).get();
	}

	public void setProperty(String key, String value) {
		AppConfig.getAppConfig(this).set(key, value);
	}

	public String getProperty(String key) {
		return AppConfig.getAppConfig(this).get(key);
	}

	public void removeProperty(String... key) {
		AppConfig.getAppConfig(this).remove(key);
	}

}
