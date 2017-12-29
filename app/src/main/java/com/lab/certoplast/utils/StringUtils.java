package com.lab.certoplast.utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * 
 * @author
 * @version 1.0
 * @created
 */
public class StringUtils {
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	// private final static SimpleDateFormat dateFormater = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// private final static SimpleDateFormat dateFormater2 = new
	// SimpleDateFormat("yyyy-MM-dd");

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater2.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate2(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将日期转换为字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String tostring(Date sdate) {
		return dateFormater2.get().format(sdate);
	}

	/**
	 * 将字符串转位日期类型的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String toDateString(String sdate) {
		try {
			return dateFormater.get().parse(sdate).toString();
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将字符串转位日期类型的字符串
	 *
	 * @param sdate
	 * @return
	 */
	public static String toDateString2(String sdate) {
		try {
			return dateFormater2.get().parse(sdate).toString();
		} catch (ParseException e) {
			return null;
		}
	}


	/**
	 * 获取当天的日期 格式为:xxxx-xx-xx
	 * 
	 * @return
	 */
	public static String getCurDate() {
		return dateFormater2.get().format(Calendar.getInstance().getTime());
	}

	/**
	 * 获取当天的日期 格式为:xxxx-xx-xx xx:xx:xx
	 * 
	 * @return
	 */
	public static String getCurTime() {
		return dateFormater.get().format(Calendar.getInstance().getTime());
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否不为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return s != null && !"".equals(s.trim());
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 字符串转浮点型
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static float toFloat(String str, int defValue) {
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	// 检测用户手机号码
	public static boolean isValidMobiNumber(String paramString) {
		String regex = "^1\\d{10}$";
		if (paramString.matches(regex)) {
			return true;
		}
		return false;
	}

	// 检测用户名称为3-7位的字母，数字或是中文
	public static boolean isValidUsername(String paramString) {
		String regex = "^[a-zA-Z0-9\u4e00-\u9fa5]{2,7}$";
		if (paramString.matches(regex)) {
			return true;
		}
		return false;
	}

	// 检测用户名密码为6-15为字母或数字
	public static boolean isValidPassword(String paramString) {
		String regex = "^[a-zA-Z0-9]{6,15}$";
		if (paramString.matches(regex)) {
			return true;
		}
		return false;
	}
	
	// 金钱转换 double转换为123.00
	public static double parseDouble(double price) {
		DecimalFormat df = new DecimalFormat("###,###.00");

		return toDouble(df.format(price), 0.00d);
	}
	
	/**
	 * 字符串转double
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static double toDouble(String str, double defValue) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
		}
		return defValue;
	}


	public static String xmlParser(String xml)throws XmlPullParserException, IOException{

		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(xml));
		int event = parser.getEventType();

		String result = "";
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
				case XmlPullParser.START_TAG:
					if ("string".equals(parser.getName())) {
						result = parser.nextText();

					}
					break;
				case XmlPullParser.END_TAG:
					break;
			}
			event = parser.next();
		}

		return result;

	}

}
