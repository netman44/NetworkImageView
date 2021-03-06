package com.network44.networkimage;

import android.content.res.ColorStateList;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView.ScaleType;

/**
 * @author github.com
 * @version 1.0.0
 * https://github.com/vinc3m1/RoundedImageView
 */
public class test extends Drawable {
    /**  TAG  */
    public static final String TAG = "RoundedDrawable";
    /**  DEFAULT_BORDER_COLOR  */
    public static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private final RectF mBounds = new RectF();
    private final RectF mDrawableRect = new RectF();
    private final RectF mBitmapRect = new RectF();
    private final BitmapShader mBitmapShader;
    private final Paint mBitmapPaint;
    private final int mBitmapWidth;
    private final int mBitmapHeight;
    private final RectF mBorderRect = new RectF();
    private final Paint mBorderPaint;
    private final Matrix mShaderMatrix = new Matrix();
    private float mCornerRadius = 0;
    private boolean mOval = false;
    private float mBorderWidth = 0;
    private ColorStateList mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
    private ScaleType mScaleType = ScaleType.FIT_CENTER;
    private Bitmap mBackgroundBitmap;

    /**
     * RoundedDrawable
     * @param bitmap Bitmap
     */
    public test(Bitmap bitmap) {
        mBackgroundBitmap = bitmap;
        mBitmapWidth = bitmap.getWidth();
        mBitmapHeight = bitmap.getHeight();
        mBitmapRect.set(0, 0, mBitmapWidth, mBitmapHeight);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setFilterBitmap(true);
        mBitmapPaint.setShader(mBitmapShader);
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    /**
     * @param bitmap Bitmap
     * @return RoundedDrawable
     */
    public static test fromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            return new test(bitmap);
        } else {
            return null;
        }
    }

    /**
     * @param drawable Drawable
     * @return Drawable
     */
    public static Drawable fromDrawable(Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof RoundedDrawable) {
// just return if it's already a RoundedDrawable
                return drawable;
            } else if (drawable instanceof LayerDrawable) {
                LayerDrawable ld = (LayerDrawable) drawable;
                int num = ld.getNumberOfLayers();
// loop through layers to and change to RoundedDrawables if possible
                for (int i = 0; i < num; i++) {
                    Drawable d = ld.getDrawable(i);
                    ld.setDrawableByLayerId(ld.getId(i), fromDrawable(d));
                }
                return ld;
            }
// try to get a bitmap from the drawable and
            Bitmap bm = drawableToBitmap(drawable);
            if (bm != null) {
                return new test(bm);
            } else {
            }
        }
        return drawable;
    }

    /**
     * @param drawable Drawable
     * @return Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 1);
        int height = Math.max(drawable.getIntrinsicHeight(), 1);
        try {
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }
    @Override
    public boolean isStateful() {
        return mBorderColor.isStateful();
    }
    @Override
    protected boolean onStateChange(int[] state) {
        int newColor = mBorderColor.getColorForState(state, 0);
        if (mBorderPaint.getColor() != newColor) {
            mBorderPaint.setColor(newColor);
            return true;
        } else {
            return super.onStateChange(state);
        }
    }
    private void updateShaderMatrix() {
        float scale;
        float dx;
        float dy;
        final float half = 0.5f;
        switch (mScaleType) {
            case CENTER:
                mBorderRect.set(mBounds);
                mBorderRect.inset((mBorderWidth) / 2, (mBorderWidth) / 2);
                mShaderMatrix.reset();
                mShaderMatrix.setTranslate((int) ((mBorderRect.width() - mBitmapWidth) * half + half),
                        (int) ((mBorderRect.height() - mBitmapHeight) * half + half));
                break;
            case CENTER_CROP:
                mBorderRect.set(mBounds);
                mBorderRect.inset((mBorderWidth) / 2, (mBorderWidth) / 2);
                mShaderMatrix.reset();
                dx = 0;
                dy = 0;
                if (mBitmapWidth * mBorderRect.height() > mBorderRect.width() * mBitmapHeight) {
                    scale = mBorderRect.height() / (float) mBitmapHeight;
                    dx = (mBorderRect.width() - mBitmapWidth * scale) * half;
                } else {
                    scale = mBorderRect.width() / (float) mBitmapWidth;
                    dy = (mBorderRect.height() - mBitmapHeight * scale) * half;
                }
                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate((int) (dx + half) + mBorderWidth,
                        (int) (dy + half) + mBorderWidth);
                break;
            case CENTER_INSIDE:
                mShaderMatrix.reset();
                if (mBitmapWidth <= mBounds.width() && mBitmapHeight <= mBounds.height()) {
                    scale = 1.0f;
                } else {
                    scale = Math.min(mBounds.width() / (float) mBitmapWidth,
                            mBounds.height() / (float) mBitmapHeight);
                }
                dx = (int) ((mBounds.width() - mBitmapWidth * scale) * half + half);
                dy = (int) ((mBounds.height() - mBitmapHeight * scale) * half + half);
                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate(dx, dy);
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset((mBorderWidth) / 2, (mBorderWidth) / 2);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;
            default:
            case FIT_CENTER:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.CENTER);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset((mBorderWidth) / 2, (mBorderWidth) / 2);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;
            case FIT_END:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.END);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset((mBorderWidth) / 2, (mBorderWidth) / 2);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;
            case FIT_START:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.START);
                mShaderMatrix.mapRect(mBorderRect);
                mBorderRect.inset((mBorderWidth) / 2, (mBorderWidth) / 2);
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;
            case FIT_XY:
                mBorderRect.set(mBounds);
                mBorderRect.inset((mBorderWidth) / 2, (mBorderWidth) / 2);
                mShaderMatrix.reset();
                mShaderMatrix.setRectToRect(mBitmapRect, mBorderRect, Matrix.ScaleToFit.FILL);
                break;
        }
        mDrawableRect.set(mBorderRect);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mBounds.set(bounds);
        updateShaderMatrix();
    }
    @Override
    public void draw(Canvas canvas) {
        if (mOval) {
            if (mBorderWidth > 0) {
                canvas.drawOval(mDrawableRect, mBitmapPaint);
                canvas.drawOval(mBorderRect, mBorderPaint);
            } else {
                canvas.drawOval(mDrawableRect, mBitmapPaint);
            }
        } else {
            if (mBorderWidth > 0) {
                if (mBitmapHeight == 1 && mBitmapWidth == 1) {
                    mBitmapPaint.setColor(mBackgroundBitmap.getPixel(0, 0));
                }

                canvas.drawRoundRect(mDrawableRect, Math.max(mCornerRadius, 0),
                        Math.max(mCornerRadius, 0), mBitmapPaint);
                canvas.drawRoundRect(mBorderRect, mCornerRadius, mCornerRadius, mBorderPaint);
            } else {
                canvas.drawRoundRect(mDrawableRect, mCornerRadius, mCornerRadius, mBitmapPaint);
            }
        }
    }
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    @Override
    public void setAlpha(int alpha) {
        mBitmapPaint.setAlpha(alpha);
        invalidateSelf();
    }
    @Override
    public void setColorFilter(ColorFilter cf) {
        mBitmapPaint.setColorFilter(cf);
        invalidateSelf();
    }
    @Override public void setDither(boolean dither) {
        mBitmapPaint.setDither(dither);
        invalidateSelf();
    }
    @Override public void setFilterBitmap(boolean filter) {
        mBitmapPaint.setFilterBitmap(filter);
        invalidateSelf();
    }
    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth;
    }
    @Override
    public int getIntrinsicHeight() {
        return mBitmapHeight;
    }

    /**
     * @return float
     */
    public float getCornerRadius() {
        return mCornerRadius;
    }

    /**
     * @param radius float
     * @return RoundedDrawable
     */
    public test setCornerRadius(float radius) {
        mCornerRadius = radius;
        return this;
    }

    /**
     * @return float
     */
    public float getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * @param width float
     * @return RoundedDrawable
     */
    public test setBorderWidth(float width) {
        mBorderWidth = width;
        mBorderPaint.setStrokeWidth(mBorderWidth);
        return this;
    }

    /**
     * @return int
     */
    public int getBorderColor() {
        return mBorderColor.getDefaultColor();
    }


    /**
     *
     * @param colors ColorStateList
     * @return RoundedDrawable
     */
    public test setBorderColor(ColorStateList colors) {
        mBorderColor = colors != null ? colors : ColorStateList.valueOf(0);
        mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
        return this;
    }

    /**
     *
     * @return boolean
     */
    public boolean isOval() {
        return mOval;
    }

    /**
     *
     * @param oval boolean
     * @return RoundedDrawable
     */
    public test setOval(boolean oval) {
        mOval = oval;
        return this;
    }

    /**
     *
     * @return ScaleType
     */
    public ScaleType getScaleType() {
        return mScaleType;
    }

    /**
     *
     * @param scaleType ScaleType
     * @return RoundedDrawable
     */
    public test setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            scaleType = ScaleType.FIT_CENTER;
        }
        mScaleType = scaleType;
        updateShaderMatrix();
        return this;
    }

    /**
     *
     * @return Bitmap
     */
    public Bitmap toBitmap() {
        return drawableToBitmap(this);
    }

    /**
     *
     * @return Paint
     */
    public Paint getBitmapPaint() {
        return mBitmapPaint;
    }
}
