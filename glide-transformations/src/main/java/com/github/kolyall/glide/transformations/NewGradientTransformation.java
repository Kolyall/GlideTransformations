package com.github.kolyall.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Shader.TileMode.CLAMP;

/**
 * Created by User on 06.05.2017.
 */

/**
 * Overlays a gradient on the bitmap to be transformed.
 *
 * @since 2.1.0
 */
public class NewGradientTransformation extends BaseBitmapTransformation {
    private static final String TAG = GradientTransformation.class.getSimpleName();
    private static final int[] sDefColors = {TRANSPARENT, TRANSPARENT}; // [0] set in constructor
    private static final float[] sDefPositions = {0.5f, 1.0f};

    private final Orientation mOrient;
    private final int[] mColors;
    private final float[] mPositions;

    /**
     * Use the default colors and positions. The first half of the overlay will be dark and
     * transparent. The second half will fade from dark and transparent to completely transparent.
     */
    public NewGradientTransformation(Context context, Orientation orientation) {
        this(orientation, sDefColors, sDefPositions);
        if (sDefColors[0] == TRANSPARENT) {
            sDefColors[0] = Color.BLACK;
        }
    }

    /**
     * Use the color resource and default positions. The first half of the overlay will be the
     * color. The second half will fade from the color to transparent.
     *
     * @since 2.4.0
     */
    public NewGradientTransformation(Context context, Orientation orientation, @ColorRes int colorId) {
        this(orientation, new int[]{context.getResources().getColor(colorId), TRANSPARENT},
                sDefPositions);
    }

    /**
     * Apply a gradient of the colors at the positions in the direction of the orientation.
     *
     * @see android.graphics.LinearGradient
     */
    public NewGradientTransformation(Context context, Orientation orientation, @ColorRes int[] colorRes,
                                     @FloatRange(from = 0.0, to = 1.0) float[] positions) {
        int[] colors = new int[colorRes.length];
        for (int i = 0; i < colorRes.length; i++) {
            colors[i] = context.getResources().getColor(colorRes[i]);
        }
        mOrient = orientation;
        mColors = colors;
        mPositions = positions;
    }

    /**
     * Apply a gradient of the colors at the positions in the direction of the orientation.
     *
     * @see android.graphics.LinearGradient
     */
    public NewGradientTransformation(Orientation orientation, @ColorInt int[] colors,
                                     @FloatRange(from = 0.0, to = 1.0) float[] positions) {
        mOrient = orientation;
        mColors = colors;
        mPositions = positions;
    }

    @NotNull
    @Override
    public Bitmap transform(@NotNull Bitmap source) {
        Bitmap out = mutable(source);
        if (out == null) {
            Log.e(TAG, "bitmap could not be copied, returning untransformed");
            return source;
        }
        Canvas canvas = new Canvas(out);
        Paint paint = new Paint();
        int width = out.getWidth();
        int height = out.getHeight();
        float x0 = 0.0f, y0 = 0.0f, x1 = 0.0f, y1 = 0.0f;
        switch (mOrient) {
            case BOTTOM_TOP:
                y0 = height;
                break;
            case BL_TR:
                y0 = height;
                x1 = width;
                break;
            case LEFT_RIGHT:
                x1 = width;
                break;
            case TL_BR:
                x1 = width;
                y1 = height;
                break;
            case TOP_BOTTOM:
                y1 = height;
                break;
            case TR_BL:
                x0 = width;
                y1 = height;
                break;
            case RIGHT_LEFT:
                x0 = width;
                break;
            case BR_TL:
                x0 = width;
                y0 = height;
                break;
        }
        paint.setShader(new LinearGradient(x0, y0, x1, y1, mColors, mPositions, CLAMP));
        canvas.drawRect(0.0f, 0.0f, width, height, paint);
        return out;
    }

    /**
     * If the bitmap is immutable, get a mutable copy of it. After a copy is created, the source
     * bitmap will be recycled. If the bitmap is already mutable, it will be returned.
     *
     * @return null if a copy could not be made
     */
    @Nullable
    public static Bitmap mutable(Bitmap source) {
        if (source.isMutable()) {
            return source;
        }
        Bitmap.Config config = source.getConfig();
        Bitmap bm = source.copy(config != null ? config : ARGB_8888, true);
        if (bm != null) {
            source.recycle();
        }
        return bm;
    }

    @Override
    public String key() {
        return "Gradient:" + mOrient + "-" + Arrays.toString(mColors) + "-" + Arrays.toString(mPositions);
    }
}