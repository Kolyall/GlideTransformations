package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

/**
 * Created by Nick Unuchek on 19.05.2017.
 */
class RoundedCornersTransform(private val targetRadius: Int) : BaseBitmapTransformation() {
    override fun transform(source: Bitmap): Bitmap { //        int size = Math.min(source.getWidth(), source.getHeight());
//        mRadius = size/mRadius;
        val out = Bitmap.createBitmap(
            source.width, source.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(out)
        val paint = Paint()
        val rect = Rect(0, 0, source.width, source.height)
        val rectF = RectF(rect)
        // prepare canvas for transfer
        paint.isAntiAlias = true
        paint.color = -0x1
        paint.style = Paint.Style.FILL
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawRoundRect(rectF, targetRadius.toFloat(), targetRadius.toFloat(), paint)
        // draw source
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(source, rect, rect, paint)
        return out
    }

    override fun key(): String {
        return "RoundedCornersTransformRadius$targetRadius"
    }

}