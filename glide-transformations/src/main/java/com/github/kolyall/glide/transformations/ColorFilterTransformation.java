package com.github.kolyall.glide.transformations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation;
import org.jetbrains.annotations.NotNull;

public class ColorFilterTransformation extends BaseBitmapTransformation {

    private int mColor;

    public ColorFilterTransformation(int color) {
        mColor = color;
    }

    @NotNull
    @Override public Bitmap transform(Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColorFilter(new PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(source, 0, 0, paint);

        return bitmap;
    }

    @NotNull
    @Override public String key() {
        return "ColorFilterTransformation(color=" + mColor + ")";
    }
}