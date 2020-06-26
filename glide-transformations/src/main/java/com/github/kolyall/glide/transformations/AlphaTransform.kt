package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.FloatRange
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

class AlphaTransform constructor(
    @FloatRange(from = 0.0, to = 1.0) private val alpha: Float
) : BaseBitmapTransformation() {
    override fun transform(source: Bitmap): Bitmap { // Create another bitmap that will hold the results of the filter.
        val width = source.width
        val height = source.height
        val out = Bitmap.createBitmap(width, height, source.config)
        val canvas = Canvas(out)
        canvas.drawARGB(0, 0, 0, 0)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.alpha = (alpha * 255).toInt()
        canvas.drawBitmap(source, 0f, 0f, paint)
        if (out != source) {
            source.recycle()
        }
        return out
    }

    override fun key(): String {
        return "AlphaTransform: ${(alpha * 255).toInt()}"
    }

}