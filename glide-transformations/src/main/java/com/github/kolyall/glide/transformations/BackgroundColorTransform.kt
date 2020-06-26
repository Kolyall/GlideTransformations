package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

class BackgroundColorTransform 
constructor(
    @ColorInt private val fillColor: Int
) : BaseBitmapTransformation() {

    override fun transform(source: Bitmap): Bitmap { // Create another bitmap that will hold the results of the filter.
        val width = source.width
        val height = source.height
        val out = Bitmap.createBitmap(width, height, source.config)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawColor(fillColor)
        canvas.drawBitmap(source, 0f, 0f, paint)
        return out
    }

    override fun key(): String {
        return "BackgroundColorTransform: $fillColor"
    }

}