package com.lab.certoplast.utils;

import android.widget.Toast;

import com.lab.certoplast.app.AppContext;

public enum UiCommon {
	INSTANCE;

	/**
	 * 提示信息
	 * 
	 * @param aFormatMsg
	 * @param aMsgArgs
	 */
	public void showTip(String aFormatMsg, Object... aMsgArgs) {
		String outString = String.format(aFormatMsg, aMsgArgs);
		int duration = (outString.length() > 10) ? Toast.LENGTH_LONG
				: Toast.LENGTH_SHORT;
		Toast.makeText(AppContext.getInstance(), outString, duration).show();
	}

}
