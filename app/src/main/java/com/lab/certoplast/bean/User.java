package com.lab.certoplast.bean;

public class User extends Base {
	/**
	 * 用户名
	 */
	private String User_Name;
	/**
	 * 密码
	 */
	private String PassWord;

	/**
	 * 登陆时间
	 */
	private String LoginTime;


	public String getUser_Name() {
		return User_Name;
	}

	public void setUser_Name(String user_Name) {
		User_Name = user_Name;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	public String getLoginTime() {
		return LoginTime;
	}

	public void setLoginTime(String loginTime) {
		LoginTime = loginTime;
	}
}
