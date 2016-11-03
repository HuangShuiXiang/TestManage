package com.testmanage.oldtest.util;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.testmanage.R;

/**
 * Created by Administrator on 2016/5/12 0012.
 */
public class FrescoUtil {
    /**
     * 这个是fresco
     * <p>
     * 这个方法是用来判断是否是网络图片来的
     * 不是网络图片就加file
     *
     * @param url
     * @param imageView
     */
    public static void displayImageHttpOrFile(String url, SimpleDraweeView imageView, int... withOrheight) {
        if (!TextUtils.isEmpty(url)) {
            /*Uri uri = Uri.parse(url);
			imageView.setImageURI(uri);*/
            if (url.startsWith("http") && url.contains(".gif")) {
                displayImageGifFile(url, imageView);
            } else if (url.startsWith("http://") || url.startsWith("https://")) {
                /*Uri uri = Uri.parse(url);
                imageView.setImageURI(uri);*/
                displayImageHttpFile(url, imageView, withOrheight);
            } else if (url.startsWith("file://")) {
                Uri uri = Uri.parse(url);
                imageView.setImageURI(uri);
            } else {
                //fresco加载本地图片OOM，暂时没有解决
//                imageLoader.displayImage("file://" + url, imageView);
                displayImageLocalFile(url, imageView, withOrheight);

            }
        } else {
            displayImageResFile(R.drawable.default_no_img, imageView);
        }
    }

    /**
     * 这个是fresco
     * <p>
     * 这个方法专门加载gif图片
     *
     * @param url
     * @param imageView
     */
    public static void displayImageGifFile(String url, SimpleDraweeView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
            hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            hierarchy.setProgressBarImage(R.drawable.default_ing_img);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).build();
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder().setImageRequest(request).setAutoPlayAnimations(true).build();
            imageView.setController(controller);
        }
    }

    /**
     * 这个是fresco
     * <p>
     * 这个方法专门加载网络裁剪图片
     *
     * @param url
     * @param imageView
     */
    public static void displayImageHttpFile(String url, SimpleDraweeView imageView, int... withOrheight) {
        /*GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
        hierarchy.setProgressBarImage(new ImageLoadingDrawable(),ScalingUtils.ScaleType.FIT_CENTER);*/
        if (withOrheight != null && withOrheight.length == 2) {
            ImageRequest request = ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse(url))
                    .setAutoRotateEnabled(true)
                    .setLocalThumbnailPreviewsEnabled(true)
                    .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    .setProgressiveRenderingEnabled(false)
                    .setResizeOptions(new ResizeOptions(withOrheight[0], withOrheight[1]))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .build();
            imageView.setController(controller);
        } else {
            Uri uri = Uri.parse(url);
            imageView.setImageURI(uri);
        }

    }

    /**
     * 这个是fresco
     * <p>
     * 这个方法专门加载本地图片
     *
     * @param url
     * @param imageView
     */
    public static void displayImageLocalFile(String url, SimpleDraweeView imageView, int... withOrheight) {
        ImageRequest request = null;
        if (withOrheight.length == 2) {
            request = ImageRequestBuilder
                    .newBuilderWithSource(uriFile(url))
                    .setAutoRotateEnabled(true)
                    .setLocalThumbnailPreviewsEnabled(true)
                    .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    .setProgressiveRenderingEnabled(false)
                    .setResizeOptions(new ResizeOptions(withOrheight[0], withOrheight[1]))
                    .build();
        } else {
            request = ImageRequestBuilder
                    .newBuilderWithSource(uriFile(url))
                    .setAutoRotateEnabled(true)
                    .setLocalThumbnailPreviewsEnabled(true)
                    .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    .setProgressiveRenderingEnabled(false)
                    .build();
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .build();
        imageView.setController(controller);
    }

    /**
     * 这个是fresco
     * <p>
     * 这个方法专门加载资源res图片
     *
     * @param resId
     * @param imageView
     */
    public static void displayImageResFile(int resId, SimpleDraweeView imageView) {
        Uri uri = Uri.parse("res:///" + resId);
        imageView.setImageURI(uri);
    }

    /**
     * 根据文件路径生成Uri
     *
     * @param path
     * @return
     */
    public static Uri uriFile(String path) {
        return Uri.parse("file://" + path);
    }

}
