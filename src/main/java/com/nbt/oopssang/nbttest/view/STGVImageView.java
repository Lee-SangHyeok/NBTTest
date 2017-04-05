package com.nbt.oopssang.nbttest.view;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class STGVImageView extends ImageView {

    public int mWidth = 0;
    public int mHeight = 0;

    private static final float Trans = -25f;

    private final static float[] BT_SELECTED = new float[]{
            1, 0, 0, 0, Trans,
            0, 1, 0, 0, Trans,
            0, 0, 1, 0, Trans,
            0, 0, 0, 1, 0};

    private final static float[] BT_NOT_SELECTED = new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0};

    private ColorMatrixColorFilter mPressFilter;
    private ColorMatrixColorFilter mNormalFilter;

    public STGVImageView(Context context) {
        super(context);
    }

    public STGVImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public STGVImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightC = width * mHeight / mWidth;

        setMeasuredDimension(width, heightC);
    }

}
