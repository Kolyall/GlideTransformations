package com.github.kolyall.glide.transformations;

import android.graphics.Bitmap;
import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsicBlur;

import com.github.kolyall.glide.transformations.base.BaseBitmapTransformation;
import org.jetbrains.annotations.NotNull;


public class BlurTransform extends BaseBitmapTransformation {
    private static final float BLUR_RADIUS = 25f;

    private final RenderScript mRenderScript;
    private final int mInSampleSize;

    public BlurTransform(RenderScript renderScript, int inSampleSize) {
        super();
        mRenderScript = renderScript;
        mInSampleSize = inSampleSize;
    }

    @NotNull
    @Override
    public Bitmap transform(@NotNull Bitmap source) {
        return blurBitmap(mRenderScript, source, BLUR_RADIUS, mInSampleSize);
    }

    /**
     * Blurs a bitmap using {@link ScriptIntrinsicBlur} with the specified mRadius. The {@code sizeReductionFactor} is used as a trick to
     * blur the image more than the highest allowed mRadius by the script.
     *
     * @param sizeReductionFactor Factor to reduce the image by before blurring.
     */
    public static Bitmap blurBitmap(RenderScript renderscript, Bitmap source, float radius, int sizeReductionFactor) {
        sizeReductionFactor = Integer.highestOneBit(sizeReductionFactor);

        if (sizeReductionFactor == 1) {
            return blurBitmap(renderscript, source, radius);
        }

        int originalWidth = source.getWidth();
        int originalHeight = source.getHeight();
        int scaledWidth = originalWidth / sizeReductionFactor;
        int scaledHeight = originalHeight / sizeReductionFactor;

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(source, scaledWidth, scaledHeight, false);

        // Create an Intrinsic Blur Script using Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(renderscript, Element.U8_4(renderscript));

        // Create the in/out Allocations with Renderscript and the in/out bitmaps
        Allocation allIn =
                Allocation.createFromBitmap(renderscript, scaledBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation
                        .USAGE_GRAPHICS_TEXTURE);
        Allocation allOut = Allocation.createFromBitmap(renderscript, outBitmap);

        // Set the mRadius of the blur
        blurScript.setRadius(radius);

        // Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        // Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        // Recycle the scaled bitmap
        scaledBitmap.recycle();
        Bitmap out = Bitmap.createScaledBitmap(outBitmap, originalWidth, originalHeight, false);
        outBitmap.recycle();

        return out;
    }

    /**
     * Blurs a bitmap using {@link ScriptIntrinsicBlur} with the specified mRadius.
     */
    public static Bitmap blurBitmap(RenderScript renderscript, Bitmap bitmap, float radius) {

        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        // Create an Intrinsic Blur Script using Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(renderscript, Element.U8_4(renderscript));

        // Create the in/out Allocations with Renderscript and the in/out bitmaps
        Allocation allIn =
                Allocation.createFromBitmap(renderscript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_GRAPHICS_TEXTURE);
        Allocation allOut = Allocation.createFromBitmap(renderscript, outBitmap);

        // Set the mRadius of the blur
        blurScript.setRadius(radius);

        // Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        // Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        bitmap.recycle();
        return outBitmap;
    }

    @Override
    public String key() {
        return "BlurTransform"+":"+mInSampleSize;
    }

}
