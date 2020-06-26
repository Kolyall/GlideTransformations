package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.IntRange
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation
import kotlin.math.max
import kotlin.math.min

/**
 * Created by User on 03.05.2017.
 */
class ShadeTransform : BaseBitmapTransformation {
    /* The shade alpha of black to apply */
    private var alpha: Int

    /**
     * Integer Constructor
     *
     * @param alpha     the alpha shade to apply
     */
    constructor(@IntRange(from = 0, to = 255) alpha: Int) { // Clamp the alpha value to 0..255
        this.alpha = max(0, min(255, alpha))
    }

    /**
     * Float Constructor
     *
     * @param percent   the alpha percentage from 0..1
     */
    constructor(percent: Float) { // Clamp the float value to 0..1
        alpha = (max(0f, min(1f, percent)) * 255).toInt()
    }

    /**
     * Transform the source bitmap into a new bitmap. If you create a new bitmap instance, you must
     * call [Bitmap.recycle] on `source`. You may return the original
     * if no transformation is required.
     *
     * @param source
     */
    override fun transform(source: Bitmap): Bitmap {
        val paint = Paint()
        val out = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        // Create canvas and draw the source image
        val canvas = Canvas(out)
        canvas.drawBitmap(source, 0f, 0f, paint)
        // Setup the paint for painting the shade
        paint.color = Color.BLACK
        paint.alpha = alpha
        // Paint the shade
        canvas.drawPaint(paint)
        // Recycle and return
        return out
    }

    /**
     * Returns a unique key for the transformation, used for caching purposes. If the transformation
     * has parameters (e.g. size, scale factor, etc) then these should be part of the key.
     */
    override fun key(): String {
        return "ShadeTransform:$alpha"
    }
}