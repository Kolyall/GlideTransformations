package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

class InnerResizeTransform(private val scale: Float) : BaseBitmapTransformation() {
    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scale, scale)
        // "RECREATE" THE NEW BITMAP
        val scaled = Bitmap.createBitmap(source, 0, 0, width, height, matrix, false)
        val out = Bitmap.createBitmap(width, height, source.config)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val dx = (width - scaled.width) / 2
        val dy = (height - scaled.height) / 2
        canvas.drawBitmap(scaled, dx.toFloat(), dy.toFloat(), paint)
        if (out != scaled) {
            scaled.recycle()
        }
        return out
    }

    override fun key(): String {
        return "InnerResizeTransform:$scale"
    }

}