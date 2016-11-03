package com.testmanage.oldtest.util.QRcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.testmanage.oldtest.util.QRcode.zxing.camera.RGBLuminanceSource;
import com.testmanage.oldtest.util.QRcode.zxing.decoding.DecodeFormatManager;

import java.util.Hashtable;
import java.util.Vector;


/**
 * Desc: 操作二维码
 * Created by 庞承晖
 * Date: 2015/12/2.
 * Time: 11:57
 */
public class QRcodeUtil {

    public interface QRcodeResultListener {
        void result(Result result);

        void fail();
    }

    /**
     * 处理识别结果
     *
     * @param context
     * @param str
     * @return true ,str成立成功, false 处理失败
     */
    public static boolean handleResult(Context context, String str) {
//        String[] key = str.split("=");
//        if (str.startsWith("http")) {
//            WebViewActivity.startAction(context, "", str);
//            return false;
//        }
//        if (key.length == 1) return false;
//        if (key[0].equals("friendId")) {//扫描好友
//            String userId = key[1];
//            UserCoverActivity.startAction(context, userId, "", "");
//        } else if (key[0].equals("crowdId")) {//扫描群
//            String groupId = key[1];
//            Intent intent = new Intent(context, GroupDetailActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putLong(GConstant.KEY_GROUP_ID, Long.valueOf(groupId));
//            intent.putExtras(bundle);
//            context.startActivity(intent);
//        } else {//其他情况
//            return false;
//        }
        return true;
    }

    /**
     * 解析二维码(默认操作)
     *
     * @param context
     * @param uri
     */
    public static void parseQRcodeUrl(Context context, Uri uri) {
        parseQRcodeUrl(context, uri, null);
    }

    /**
     * 解析二维码（含回调）
     *
     * @param context
     * @param listener
     */
    public static void parseQRcodeUrl(final Context context, Uri uri, final QRcodeResultListener listener) {
        //获取网络图片
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
//                .setProgressiveRenderingEnabled(true)
//                .setResizeOptions(new ResizeOptions(300, 300))
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {

                                 @Override
                                 public void onNewResultImpl(@Nullable Bitmap bitmap) {
                                     Result result = parseQRcodeBitmap(bitmap);
//                                     if (result == null) {
//                                         ToastUitl.showShort(context, "识别二维码失败");
//                                         return;
//                                     }
                                     if (result == null) {
                                         if (listener != null)
                                             listener.fail();
                                         return;
                                     }
                                     handleResult(context, result.getText());
                                     if (listener != null)
                                         listener.result(result);
                                 }

                                 @Override
                                 public void onFailureImpl(DataSource dataSource) {
                                     if (listener != null)
                                         listener.fail();
                                 }
                             },
                CallerThreadExecutor.getInstance());
    }

    /**
     * 解析二维码(bitmap)
     *
     * @param bitmap
     * @return
     */
    public static Result parseQRcodeBitmap(Bitmap bitmap) {
//        BitmapUtil.saveImageToGallery(GApplication.getInstance(), bitmap);
        //解析转换类型UTF-8
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        Vector<BarcodeFormat> decodeFormats = null;
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<BarcodeFormat>();
            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        //开始解析
        Result result = null;
        try {
//            result = reader.decode(binaryBitmap, hints);
            result = multiFormatReader.decodeWithState(binaryBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
        } catch (Exception re) {
            // continue
        } finally {
            multiFormatReader.reset();
        }
        return result;
    }

    /**
     * 生成二维码
     *
     * @param key
     * @return
     */
    public static Bitmap createQRImage(String key) {
        return createQRImage(key, 600, 600);
    }

    /**
     * 生成二维码
     *
     * @param key
     * @return
     */
    public static Bitmap createQRImageGrayBackground(String key) {
        return createQRImage(key, 600, 600, Color.BLACK, Color.parseColor("#FFF5F5F5"));
    }

    /**
     * 生成二维码
     *
     * @param key
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createQRImage(String key, int width, int height) {
//        return createQRImage(key, width, height, 0xFF000000, 0x00000000);
        return createQRImage(key, width, height, Color.BLACK, Color.WHITE);
        //
//        return createQRImage(key, width, height, Color.BLACK, Color.parseColor("#FFF5F5F5"));
    }

    /**
     * 生成二维码
     *
     * @param key
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createQRImage(String key, int width, int height, int pointColor, int backgroundColor) {
        try {
            int QR_WIDTH = width;
            int QR_HEIGHT = height;

            //判断URL合法性
            if (key == null || "".equals(key) || key.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(key, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = pointColor;//点的颜色
                    } else {
                        pixels[y * QR_WIDTH + x] = backgroundColor;//背景颜色
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_4444);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            return bitmap;
            //显示到一个ImageView上面
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public enum QRTYPE {
        SHOP, USER, ORDER//商家/用户/订单
    }

    //生成二维码
    public static Bitmap createQR(String url) {
       /* if (!(type instanceof QRTYPE) || TextUtils.isEmpty(id))
            return null;
        String temp;
        if (type == QRTYPE.SHOP) {
            temp = String.format(GAddress.getInstance().QR_CODE_URL, "shop", id);
        } else if (type == QRTYPE.USER) {
            temp = String.format(GAddress.getInstance().QR_CODE_URL, "user", id);
        } else if (type == QRTYPE.ORDER) {
            temp = "orderToken=" + id;
        } else {
            return null;
        }*/
        return QRcodeUtil.createQRImage(url);
    }

    //解析二维码
    public static void analysisQRCode(String qr_code, QRcodeParseListener listener) {
        if (TextUtils.isEmpty(qr_code) || (!qr_code.contains("shop")
                && !qr_code.contains("user"))
                || !qr_code.contains("id")) {
            if (!qr_code.contains("orderToken")) {
                if (listener != null)
                    listener.fail("无效二维码,请确认!");
                return;
            }
        }
        String id = null;
        QRTYPE type = null;
        if (qr_code.contains("id")) {
            id = qr_code.substring(qr_code.indexOf("id") + 3, qr_code.length());
        } else if (qr_code.contains("orderToken")) {
            id = qr_code.substring(qr_code.indexOf("orderToken") + 11, qr_code.length());
        }
        if (qr_code.contains("user")) {
            type = QRTYPE.USER;
        } else if (qr_code.contains("shop")) {
            type = QRTYPE.SHOP;
        } else if (qr_code.contains("orderToken")) {
            type = QRTYPE.ORDER;
        }
        if (listener != null)
            listener.success(type, id);

    }

    public interface QRcodeParseListener {
        void success(QRTYPE type, String id);

        void fail(String error);
    }
}
