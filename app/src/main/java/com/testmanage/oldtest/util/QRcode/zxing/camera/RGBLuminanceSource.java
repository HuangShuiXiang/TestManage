package com.testmanage.oldtest.util.QRcode.zxing.camera;

import android.graphics.Bitmap;

import com.google.zxing.LuminanceSource;

public final class RGBLuminanceSource extends LuminanceSource {
	private final byte[] lunminances;
	
	public RGBLuminanceSource(Bitmap bitmap) {
		super(bitmap.getWidth(), bitmap.getHeight());
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
		lunminances = new byte[width * height];
		for (int i = 0; i < height; i++) {
			int offset = i * width;
			for (int j = 0; j < width; j++) {
				int pixel = pixels[offset+j];
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = pixel & 0xff;
				if(r == g && g == b){
					lunminances[offset + j] = (byte) r;
				}else{
					lunminances[offset + j] = (byte) ((r + g + g + b)>>2);
				}
			}
		}
	}
	@Override
	  public byte[] getRow(int y, byte[] row) {
	    if (y < 0 || y >= getHeight()) {
	      throw new IllegalArgumentException("Requested row is outside the image: " + y);
	    }
	    int width = getWidth();
	    if (row == null || row.length < width) {
	      row = new byte[width];
	    }
	    return row;
	  }

	  // Since this class does not support cropping, the underlying byte array already contains
	  // exactly what the caller is asking for, so give it to them without a copy.
	  @Override
	  public byte[] getMatrix() {
	    return lunminances;
	  }
}
