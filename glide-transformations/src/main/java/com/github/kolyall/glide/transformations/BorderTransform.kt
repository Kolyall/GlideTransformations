package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.annotation.ColorInt
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation
import kotlin.math.max

/**
 * Created by Nick Unuchek on 18.07.2017.
 */
class BorderTransform constructor(
    @ColorInt private val borderColor: Int = Color.BLACK,
    private val borderSize: Int = 0
) : BaseBitmapTransformation() {

    override fun transform(source: Bitmap): Bitmap {
        val borderSize = if (borderSize == 0) {
            max(source.width / 7, source.height / 7) / 4
        } else {
            borderSize
        }
        val width = source.width + borderSize * 2
        val height = source.height + borderSize * 2
        val out = Bitmap.createBitmap(width, height, source.config)
        val canvas = Canvas(out)
        canvas.drawColor(borderColor)
        canvas.drawBitmap(source, borderSize.toFloat(), borderSize.toFloat(), null)
        return out
    }

    override fun key(): String {
        return "BorderTransform:$borderColor:$borderSize"
    }
}