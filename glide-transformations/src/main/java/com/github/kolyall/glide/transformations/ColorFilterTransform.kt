package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LightingColorFilter
import android.graphics.Paint
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

class ColorFilterTransform(private val multiply: Int, private val add: Int) : BaseBitmapTransformation() {
    override fun transform(source: Bitmap): Bitmap { // Create another bitmap that will hold the results of the filter.
        val width = source.width
        val height = source.height
        val out = Bitmap.createBitmap(width, height, source.config)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.colorFilter = LightingColorFilter(multiply, add)
        canvas.drawBitmap(source, 0f, 0f, paint)
        return out
    }

    override fun key(): String {
        return "ColorFilterTransform:$multiply:$add"
    }

}