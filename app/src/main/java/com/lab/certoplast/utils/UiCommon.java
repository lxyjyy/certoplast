package com.lab.certoplast.utils;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.certoplast.app.AppContext;

public enum UiCommon {
	INSTANCE;


	private Toast mToast;

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
//		Toast.makeText(AppContext.getInstance(), outString, duration).show();

		if (mToast == null) {
			mToast = Toast.makeText(AppContext.getInstance(), "",
					duration);
			LinearLayout layout = (LinearLayout) mToast.getView();
			TextView tv = (TextView) layout.getChildAt(0);
			tv.setTextSize(18);
		}
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.setText(outString);
		mToast.show();

	}

}
