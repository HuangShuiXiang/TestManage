package com.testmanage.oldtest.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast统一管理类
 *
 */
public class ToastUitl {


	private static Toast toast;
	private static Toast initToast(Context context, CharSequence message, int duration){
		if (toast == null) {
			toast = Toast.makeText(context, message, duration);
		} else {
			toast.setText(message);
			toast.setDuration(duration);
		}
		return toast;
	}
	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message) {
		if (TextUtils.isEmpty(message)) return;
		initToast(context,message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 *
	 * @param context
	 * @param strResId
	 */
	public static void showShort(Context context, int strResId) {
//		Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
		initToast(context,  context.getResources().getText(strResId), Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message) {
		initToast(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param context
	 * @param strResId
	 */
	public static void showLong(Context context, int strResId) {
		initToast(context, context.getResources().getText(strResId), Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 *
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration) {
		initToast(context, message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param strResId
	 * @param duration
	 */
	public static void show(Context context, int strResId, int duration) {
		initToast(context, context.getResources().getText(strResId), duration).show();
	}
}
