package com.network44.networkimage;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 在此写用途
 *
 * @author: zhiwei
 * @date: 2016-12-10 18:04
 * @version: 9.1.0
 */
public class NetworkImageView extends ImageView{
    private static final String TAG = "NetworkImageView";
    private ScaleType mScaleType;

    public NetworkImageView(Context context) {
        super(context);
    }

    public NetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    private int mPlaceHodler = -1;
    private int mErrorImage = -1;
    private boolean mIsCircle;
    private int mCircleBorderWidth;
    private ColorStateList mCircleBorderColor;
    private int mBorderRadius;


    private static final ScaleType[] SCALE_TYPES = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NetworkImageView);
        mPlaceHodler = a.getResourceId(R.styleable.NetworkImageView_placeHolder, -1);
        mErrorImage = a.getResourceId(R.styleable.NetworkImageView_errorImage, -1);
        mIsCircle = a.getBoolean(R.styleable.NetworkImageView_circle, false);
        mCircleBorderWidth = a.getDimensionPixelSize(R.styleable.NetworkImageView_BorderWidth, 0);
        mCircleBorderColor = a.getColorStateList(R.styleable.NetworkImageView_BorderColor);

        mBorderRadius = a.getDimensionPixelSize(R.styleable.NetworkImageView_borderRadius, 0);
        int index = a.getInt(R.styleable.NetworkImageView_android_scaleType, -1);
        if (index > 0) {
            setScaleType(SCALE_TYPES[index]);
        } else {
            // default scaletype to FIT_CENTER
            setScaleType(ScaleType.FIT_CENTER);
        }

        a.recycle();
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        assert scaleType != null;
        if (isRoundOrCorner()) {
            if (mScaleType != scaleType) {
                mScaleType = scaleType;
                switch (scaleType) {
                    case CENTER:
                    case CENTER_CROP:
                    case CENTER_INSIDE:
                    case FIT_CENTER:
                    case FIT_START:
                    case FIT_END:
                    case FIT_XY:
                        super.setScaleType(ScaleType.FIT_XY);
                        break;
                    default:
                        super.setScaleType(scaleType);
                        break;
                }
//                updateDrawableAttrs4Circle();
//                updateBackgroundDrawableAttrs4Circle(false);
                invalidate();
            }
        } else {
            mScaleType = scaleType;

            super.setScaleType(scaleType);
        }
    }

    private boolean isRoundOrCorner() {
        return mIsCircle || mBorderRadius > 0;
    }
}
