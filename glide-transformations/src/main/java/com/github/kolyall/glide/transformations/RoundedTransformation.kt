package com.github.kolyall.glide.transformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation

class RoundedTransformation : BaseBitmapTransformation() {
    override fun transform(source: Bitmap): Bitmap {
        val radius = source.width
        val scaled = if (source.height != radius) Bitmap.createScaledBitmap(source, radius, radius, false) else source
        val out = Bitmap.createBitmap(scaled.width, scaled.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint()
        val rect = Rect(0, 0, scaled.width, scaled.height)
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = Color.parseColor("#BAB399")
        canvas.drawCircle(scaled.width / 2 + 0.7f, scaled.height / 2 + 0.7f, scaled.width / 2 + 0.1f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(scaled, rect, rect, paint)
        if (out != scaled) {
            scaled.recycle()
        }
        if (out != source) {
            source.recycle()
        }
        return out
    }

    override fun key(): String {
        return "RoundedTransformation"
    }
}