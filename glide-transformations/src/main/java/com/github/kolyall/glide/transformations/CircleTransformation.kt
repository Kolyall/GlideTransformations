package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation
import kotlin.math.min

/**
 * Created by Nikolay Unuchek on 29.04.2016.
 */
class CircleTransformation : BaseBitmapTransformation() {
    override fun transform(source: Bitmap): Bitmap {
        val size = min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        val out = Bitmap.createBitmap(size, size, source.config)
        val canvas = Canvas(out)
        val paint = Paint()
        val shader = BitmapShader(
            squaredBitmap,
            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        )
        paint.shader = shader
        paint.isAntiAlias = true
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        squaredBitmap.recycle()
        if (out != squaredBitmap) {
            squaredBitmap.recycle()
        }
        return out
    }

    override fun key(): String {
        return "CircleTransformation"
    }
}