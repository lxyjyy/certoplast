package com.lab.certoplast.bean;

/**
 * 错误提示
 * 
 * @author
 * 
 */
public class ErrorMessage extends Base {
	/**
	 * 错误提示码
	 */
	private String errorcode;
	/**
	 * 错误文本具体信息
	 */
	private String text= "";

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
