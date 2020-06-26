package com.github.kolyall.glide.transformations

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

/**
 * Created by Nick Unuchek on 19.05.2017.
 */
class GlowTransform constructor(
    context: Context,
    @DimenRes glowRadius: Int,
    @DimenRes margin: Int,
    @ColorRes glowColor: Int
) : BaseBitmapTransformation() {

    private var glowRadius: Int = context.resources.getDimensionPixelSize(glowRadius)
    private var margin: Int = context.resources.getDimensionPixelSize(margin)
    @ColorInt
    private var glowColor: Int = ContextCompat.getColor(context, glowColor)

    override fun transform(source: Bitmap): Bitmap {
        val halfMargin = margin / 2
        // The original image to use
// extract the alpha from the source image
        val alpha = source.extractAlpha()
        // The output bitmap (with the icon + glow)
        val out = Bitmap.createBitmap(
            source.width + margin,
            source.height + margin, Bitmap.Config.ARGB_8888
        )
        // The canvas to paint on the image
        val canvas = Canvas(out)
        val paint = Paint()
        paint.color = glowColor
        // outer glow
        paint.maskFilter = BlurMaskFilter(glowRadius.toFloat(), BlurMaskFilter.Blur.OUTER)
        canvas.drawBitmap(alpha, halfMargin.toFloat(), halfMargin.toFloat(), paint)
        // original icon
        canvas.drawBitmap(source, halfMargin.toFloat(), halfMargin.toFloat(), null)
        if (out != alpha) {
            alpha.recycle()
        }
        return out
    }

    override fun key(): String {
        return "GlowTransform:$glowRadius:$margin:$glowColor"
    }

}