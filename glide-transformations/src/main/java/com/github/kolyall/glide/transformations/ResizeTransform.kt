package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Matrix
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

class ResizeTransform : BaseBitmapTransformation{

    private val targetWidth: Int
    private val targetHeight: Int

    constructor(targetWidth: Int, targetHeight: Int) {
        this.targetWidth = targetWidth
        this.targetHeight = targetHeight
    }

    constructor(imageSize: Int) {
        this.targetWidth = imageSize
        this.targetHeight = imageSize
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val scaleWidth = targetWidth.toFloat() / width
        val scaleHeight = targetHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)
        // "RECREATE" THE NEW BITMAP
        val out = Bitmap.createBitmap(
            source, 0, 0, width, height, matrix, false
        )
        if (out != source) {
            source.recycle()
        }
        return out
    }

    override fun key(): String {
        return "ResizeTransform:$targetWidth:$targetHeight"
    }
}