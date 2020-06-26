package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation
import kotlin.math.max

/**
scale image
from WidthHeight
to targetWidthtargetHeight
with centerCrop
 */
class ScaleCropTransform(private val targetWidth: Int, private val targetHeight: Int)
    : BaseBitmapTransformation() {

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        val targetWidthScale = targetWidth.toFloat() / width
        val targetHeightScale = targetHeight.toFloat() / height

        val targetScale = max(targetWidthScale, targetHeightScale)
        val scaledWidth = (width * targetScale).toInt()
        val scaledHeight = (height * targetScale).toInt()
        val out = Bitmap.createScaledBitmap(source, scaledWidth, scaledHeight, true)
        if (out != source) {
            source.recycle()
        }
        return out
    }

    override fun key(): String {
        return "com.github.kolyall.glide.transformations.ScaleCropTransform($targetWidth,$targetHeight)"
    }
}
