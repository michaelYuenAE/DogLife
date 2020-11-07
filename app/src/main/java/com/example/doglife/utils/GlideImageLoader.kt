package com.example.doglife.utils

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.doglife.R
import com.lzy.imagepicker.loader.ImageLoader
import java.io.File

class GlideImageLoader: ImageLoader {
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
        TODO("Not yet implemented")
    }
}