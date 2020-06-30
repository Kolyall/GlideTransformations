package com.github.kolyall.glide.transformations

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.renderscript.RSRuntimeException
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation
import com.github.kolyall.glide.transformations.base.FastBlur
import com.github.kolyall.glide.transformations.base.RSBlur

class BlurTransformation @JvmOverloads constructor(
    context: Context,
    private val radius: Int = MAX_RADIUS,
    private val sampling: Int = DEFAULT_DOWN_SAMPLING
) : BaseBitmapTransformation() {

    private val context: Context = context.applicationContext

    override fun transform(source: Bitmap): Bitmap {
        val scaledWidth = source.width / sampling
        val scaledHeight = source.height / sampling
        var bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.scale(1 / sampling.toFloat(), 1 / sampling.toFloat())
        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(source, 0f, 0f, paint)
        bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                RSBlur.blur(context, bitmap, radius)
            } catch (e: RSRuntimeException) {
                FastBlur.blur(bitmap, radius, true)
            }
        } else {
            FastBlur.blur(bitmap, radius, true)
        }
        return bitmap
    }

    override fun key(): String {
        return "BlurTransformation(radius=$radius, sampling=$sampling)"
    }

    companion object {
        private const val MAX_RADIUS = 25
        private const val DEFAULT_DOWN_SAMPLING = 1
    }
}