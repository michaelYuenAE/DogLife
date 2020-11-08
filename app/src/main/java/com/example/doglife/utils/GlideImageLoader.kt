package com.example.doglife.utils

import android.app.Activity
import android.content.Context
import android.util.LruCache
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.doglife.R
import com.lzy.imagepicker.loader.ImageLoader
import java.io.File

class GlideImageLoader(context: Context): ImageLoader {

    private var mContext: Context? = context

    private var mDefaultImageBubbleRo: RequestOptions? = null

    private var mSizeCache: LruCache<String, Size>? = null

    init {
        mDefaultImageBubbleRo = createImageOptions().centerCrop()
        mSizeCache = LruCache<String, Size>(100)
    }

    private fun createImageOptions(): RequestOptions {
        return RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    }


    fun loadProfileCover(url: String?, imageView: ImageView, @DrawableRes placeHolder: Int) {
        Glide.with(mContext!!)
            .load(url)
            .apply(
                createImageOptions()
                    .centerCrop()
                    .placeholder(placeHolder)
            )
            .into(imageView)
    }

    override fun displayImage(
        activity: Activity?,
        path: String?,
        imageView: ImageView?,
        width: Int,
        height: Int
    ) {
        if (imageView != null && path != null) {
            Glide.with(activity!!)
                .load(if (path.contains("http")) path else File(path))
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .fitCenter()
                .into(imageView)
        }
    }

    override fun clearMemoryCache() {
        //DO NOTHING
    }


    data class Size(var width: Int, var height: Int)
}