package com.lab.certoplast.bean;

import android.content.Context;

import com.lab.certoplast.parser.BaseParser;

import java.util.HashMap;

public class RequestVo extends Base {

	public String requestUrl;
	public String methodName;
	public Context context;
	public HashMap<String, String> requestDataMap;
	public BaseParser<?> jsonParser;

	public boolean isShowDialog = false;
	public String Message = "";

	public RequestVo() {
	}

	public RequestVo(String methodName, Context context,
					 HashMap<String, String> requestDataMap, BaseParser<?> jsonParser) {
		super();
		this.methodName = methodName;
		this.context = context;
		this.requestDataMap = requestDataMap;
		this.jsonParser = jsonParser;
	}
}
