package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

class RingTransform constructor(
    var color: Int  = Color.WHITE,
    var strokePercent: Float  = 0.04f,
    var strokeSize: Float  = 0f
) : BaseBitmapTransformation() {

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val halfWidth = width * 0.5f
        val halfHeight = height * 0.5f
        val strokeWidth = if (strokeSize != 0f) strokeSize else strokePercent * width
        val out = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val rect = Rect(0, 0, width, source.height)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val xform = Matrix()
        xform.postScale(1 - strokeWidth / width, 1 - strokeWidth / height, halfWidth, halfHeight)
        canvas.matrix = xform
        paint.isFilterBitmap = true
        paint.isDither = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawBitmap(source, rect, rect, paint)
        paint.color = color
        paint.strokeWidth = strokeWidth
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(halfWidth, halfHeight, halfWidth, paint)
        if (out != source) {
            source.recycle()
        }
        return out
    }

    override fun key(): String {
        return "RingTransform:$color:$strokePercent:$strokeSize"
    }

}