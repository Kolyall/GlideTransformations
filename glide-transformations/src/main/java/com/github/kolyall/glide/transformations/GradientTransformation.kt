package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import androidx.annotation.ColorInt
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

/**
 * Created by User on 06.05.2017.
 */
class GradientTransformation constructor(
    @ColorInt var startColor: Int = Color.TRANSPARENT,
    @ColorInt var endColor: Int = Color.argb(240, 0, 0, 0)
) : BaseBitmapTransformation() {

    override fun transform(source: Bitmap): Bitmap {
        val x = source.width
        val y = source.height
        val out = source.copy(source.config, true)
        val canvas = Canvas(out)
        //left-top == (0,0) , right-bottom(x,y);
        val grad = LinearGradient(0f, 0f, 0f, y.toFloat(), startColor, endColor, Shader.TileMode.CLAMP)
        val paint = Paint(Paint.DITHER_FLAG)
        paint.shader = null
        paint.isDither = true
        paint.isFilterBitmap = true
        paint.shader = grad
        canvas.drawPaint(paint)
        return out
    }


    override fun key(): String {
        return "GradientTransformation:$startColor:$endColor"
    }
}