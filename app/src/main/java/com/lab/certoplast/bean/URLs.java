package com.lab.certoplast.bean;

import java.io.Serializable;

/**
 * 接口URL实体类
 * 
 * @author
 * @version 1.0
 * @created
 */
public class URLs implements Serializable {

	public final static String HOST = "222.92.231.162:8002";
	public final static String HTTP = "http://";


	private final static String URL_SPLITTER = "/";

	private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;


	public final static String WSDI_URI = URL_API_HOST + "REDERP_WEBSERVICE.asmx";

	public final static String NAMESPACE = "http://tempuri.org/";



}
