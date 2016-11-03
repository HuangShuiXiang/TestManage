package com.testmanage.oldtest.util.QRcode.zxing.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StringUtils {

	public static boolean isNull(String s){
		if(s == null || s.equals("") || s.length() == 0){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 在Android设备的媒体存储显示图
	 */
	public static void showPictures(Activity a, int type){
		Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
        String IMAGE_UNSPECIFIED = "image/*";
        innerIntent.setType(IMAGE_UNSPECIFIED);
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        a.startActivityForResult(wrapperIntent, type);
	}
	
	public static Bitmap getPicFromBytes(byte[] bytes,
										 BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

}
