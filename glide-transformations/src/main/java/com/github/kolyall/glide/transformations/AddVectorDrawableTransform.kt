package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

class AddVectorDrawableTransform constructor(
    private val drawable: Drawable,
    @ColorInt var tintColor: Int
) : BaseBitmapTransformation() {

    override fun transform(source: Bitmap): Bitmap { // Create another bitmap that will hold the results of the filter.
        val width = source.width
        val height = source.height
        val out = Bitmap.createBitmap(width, height, source.config)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawBitmap(source, 0f, 0f, paint)
        var drawable = drawable.mutate()
        drawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, tintColor)
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
        //        mDrawable.setColorFilter( mTintColor, PorterDuff.Mode.MULTIPLY);
        drawable.setBounds(width / 4, height / 4, 3 * width / 4, 3 * height / 4)
        drawable.draw(canvas)
        return out
    }

    override fun key(): String {
        return "AddDrawableTransform:$drawable, $tintColor"
    }

}