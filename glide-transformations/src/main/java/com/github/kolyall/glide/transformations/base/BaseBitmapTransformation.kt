package com.github.kolyall.glide.transformations.base

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

abstract class BaseBitmapTransformation : BitmapTransformation() {

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return transform(toTransform)
    }

    abstract fun transform(source: Bitmap): Bitmap

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(key().toByteArray(Charsets.UTF_8))
    }

    abstract fun key(): String

    override fun equals(o: Any?): Boolean {
        o?:return false
        return o::class.java == javaClass
    }

    override fun hashCode(): Int {
        return key().hashCode()
    }

}