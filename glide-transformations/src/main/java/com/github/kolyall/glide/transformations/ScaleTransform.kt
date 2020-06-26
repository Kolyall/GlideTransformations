package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

class ScaleTransform
constructor(
    private val scale: Float = 1.2f
) : BaseBitmapTransformation() {
    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val halfWidth = width * 0.5f
        val halfHeight = height * 0.5f
        val out = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val xform = Matrix()
        xform.postScale(scale, scale, halfWidth, halfHeight)
        canvas.matrix = xform
        canvas.drawBitmap(source, null, Rect(0, 0, width, height), null)
        if (out != source) {
            source.recycle()
        }
        return out
    }

    override fun key(): String {
        return "ScaleTransform$scale"
    }

}