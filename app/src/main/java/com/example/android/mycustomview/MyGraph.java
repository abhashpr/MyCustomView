package com.example.android.mycustomview;

import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class MyGraph extends View implements ValueAnimator.AnimatorUpdateListener {
    private Paint mBarPaint;
    private Paint mGridPaint;
    private Paint mGuidelinePaint;
    private Paint mGraphLinePaint;
    private Paint mGridBaseAxis;

    private int barColor = getResources().getColor(R.color.blue);
    private int gridColor = getResources().getColor(R.color.teal_700);
    private int guidelineColor = getResources().getColor(R.color.gray);
    private int graphLineColor = getResources().getColor(R.color.blue);
    private int graphGridBaseColor = getResources().getColor(R.color.darkGray);

    private int gridThicknessInPx = 5;
    private int guidelineThicknessInPx = 5;
    private int getGraphLineThicknessInPx = 4;
    private int getGraphBaseThicknessInPx = 5;

    private float mPadding = 20;
    private float[] data = {10f, 20f, 25f, 30f, 35f, 55f, 70f, 80f, 67f, 55f, 33f, 21f, 5f,
            5f, 10f, 15f, 20f, 35f, 55f, 45f, 43f, 34f, 29f, 15f, 5f, 2f,
            2f, 5f, 8f, 10f, 17f, 26f, 21f, 19f, 16f, 13f, 8f, 2f, 1f,};
    private int dataCount = data.length;

    private ValueAnimator mAnimator;
    private float mAnimatingFraction;

    public MyGraph(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBarPaint = new Paint();
        mBarPaint.setStyle(Paint.Style.FILL);
        mBarPaint.setColor(barColor);

        mGridPaint = new Paint();
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(gridColor);
        mGridPaint.setStrokeWidth(gridThicknessInPx);

        mGuidelinePaint = new Paint();
        mGuidelinePaint.setStyle(Paint.Style.STROKE);
        mGuidelinePaint.setColor(guidelineColor);
        mGuidelinePaint.setStrokeWidth(guidelineThicknessInPx);

        mGraphLinePaint = new Paint();
        mGraphLinePaint.setStyle(Paint.Style.STROKE);
        mGraphLinePaint.setColor(graphLineColor);
        mGraphLinePaint.setStrokeWidth(getGraphLineThicknessInPx);

        mGridBaseAxis = new Paint();
        mGridBaseAxis.setStyle(Paint.Style.STROKE);
        mGridBaseAxis.setColor(graphGridBaseColor);
        mGridBaseAxis.setStrokeWidth(getGraphBaseThicknessInPx);

        mAnimator = new ValueAnimator();
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addUpdateListener(this);

        mAnimator.setFloatValues(0f, 1f);
        mAnimator.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        // Get our interpolated float from the animation
        mAnimatingFraction = animation.getAnimatedFraction();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int width = getWidth();
        final float gridLeft = mPadding;
        final float gridBottom = height - mPadding;
        final float gridTop = mPadding;
        final float gridRight = width - mPadding;

        // Draw Gridlines
        // canvas.drawLine(gridLeft, gridBottom, gridLeft, gridTop, mGridBaseAxis);
        canvas.drawLine(gridLeft, gridBottom, gridRight, gridBottom, mGridBaseAxis);

        // Draw guidelines
        float guideLineSpacing = (gridBottom - gridTop) / 5f;
        float y;
        for (int i = 0; i < 5; i++) {
            y = gridTop + i * guideLineSpacing;
            canvas.drawLine(gridLeft, y, gridRight, y, mGuidelinePaint);
        }

        // Draw bars
        float spacing = 2f;
        float totalColumnSpacing = spacing * (dataCount + 1);
        float columnWidth = (gridRight - gridLeft - totalColumnSpacing) / dataCount;
        float columnLeft = gridLeft + spacing;
        float columnRight = columnLeft + columnWidth;
//        for (float percentage: data) {
//            // Calculate top of the column based on percentage
//            float top = gridTop + height * (1f - (percentage * mAnimatingFraction) / 100) - 1.5f * mPadding;
//            canvas.drawRect(columnLeft, top, columnRight, gridBottom, mBarPaint);
//            // Shift over left/right column bounds
//            columnLeft = columnRight + spacing;
//            columnRight = columnLeft + columnWidth;
//        }

        int counter = 1;
        float prevX = 0;
        float prevY = 0;
        float X = gridLeft + spacing + columnWidth/2;
        float Y;
        // Draw bar peaks
        for (float percentage: data) {
            // Calculate top of each bar
            Y = gridTop + height * (1f - (percentage * mAnimatingFraction) / 100) - 1.5f * mPadding;
            if (counter > 1) {
                X = prevX + columnWidth + spacing;
                //canvas.drawCircle(prevX, prevY, 5, mGridPaint);
                canvas.drawLine(prevX, prevY, X, Y, mGraphLinePaint);
            }
            prevX = X;
            prevY = Y;
            counter++;
        }
    }


}
