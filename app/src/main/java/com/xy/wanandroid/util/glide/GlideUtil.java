package com.xy.wanandroid.util.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by jxy on 2017/8/9.
 */

public class GlideUtil {
    /**
     * 加载res
     */
    public static void loadImage(Context context, Object resId, ImageView imageView) {
        Glide.with(context)
                .load(resId)
                .into(imageView);
    }

    /**
     * 加载圆角
     */
    public static void loadRoundImage(Context context, Object resId, ImageView imageView) {
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context).
                load(resId).
                apply(mRequestOptions)
                .into(imageView);
    }

//    /**
//     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
//     */
//    public static void loadIntoUseFitWidth(Context context, final String imageUrl, final ImageView imageView) {
//        Glide.with(context)
//                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .listener(new RequestListener<String, GlideDrawable>() {
//
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        if (imageView == null) {
//                            return false;
//                        }
//                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                        }
//                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
//                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
//                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
//                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
//                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
//                        imageView.setLayoutParams(params);
//                        return false;
//                    }
//                })
//                .into(imageView);
//    }
//
//    public static void loadUrl(Context context, String imageUrl, int errorImageId, final ImageView imageView){
//        if (imageUrl == null){
//            imageUrl = "";
//        }
//        Glide.with(context)
//                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(errorImageId)
//                .error(errorImageId)
//                .into(imageView);
//    }
//    public static void loadUrl(Context context,  String imageUrl, int errorImageId,  ImageView imageView,int version){
//        if (imageUrl == null){
//            imageUrl = "";
//        }
//        Glide.with(context)
//                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .signature(new StringSignature(version+""))
//                .placeholder(errorImageId)
//                .error(errorImageId)
//                .into(imageView);
//    }
//    public static void loadCircleUrl(Context context, String imageUrl, int errorImageId, final ImageView imageView){
//        if (imageUrl == null){
//            imageUrl = "";
//        }
//        Glide.with(context)
//                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(errorImageId)
//                .error(errorImageId)
//                .transform(new GlideCircleTransform(context))
//                .into(imageView);
//    }
//    public static void loadCircleUrl(Context context,  String imageUrl, int errorImageId,  ImageView imageView,int version){
//        if (imageUrl == null){
//            imageUrl = "";
//        }
//        Glide.with(context)
//                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .signature(new StringSignature(version+""))
//                .placeholder(errorImageId)
//                .error(errorImageId)
//                .transform(new GlideCircleTransform(context))
//                .into(imageView);
//    }
//    public static void loadRoundUrl(Context context, String imageUrl, int errorImageId, final ImageView imageView){
//        if (imageUrl == null){
//            imageUrl = "";
//        }
//        Glide.with(context)
//                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(errorImageId)
//                .error(errorImageId)
//                .transform(new GlideRoundTransform(context))
//                .into(imageView);
//    }
//    public static void loadRoundUrl(Context context,  String imageUrl, int errorImageId,  ImageView imageView,int version){
//        if (imageUrl == null){
//            imageUrl = "";
//        }
//        Glide.with(context)
//                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .signature(new StringSignature(version+""))
//                .placeholder(errorImageId)
//                .error(errorImageId)
//                .transform(new GlideRoundTransform(context))
//                .into(imageView);
//    }
//

}
