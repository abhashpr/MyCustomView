package com.example.android.mycustomview;

import static android.graphics.BlendMode.COLOR;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.Nullable;
import java.util.HashMap;

public class ShapeSelectorView extends View implements ValueAnimator.AnimatorUpdateListener {

    private int shapeColor;
    private boolean displayShapeName;

    private Paint paintShape;
    private Paint textPaint, subTextPaint, miniTextPaint, dividerPaint, needle, p;

    private int xTextPos = 0;
    private int yTextPos = 0;

    public HashMap<String, Float> vitals;

    private ValueAnimator mAnimator;
    private float mAnimatingFraction;

    public ShapeSelectorView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        SetupAttributes(attrs);
        setupPaint();

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getResources().getColor(R.color.shady));
        textPaint.setTextSize(60f);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        subTextPaint = new Paint();
        subTextPaint.setAntiAlias(true);
        subTextPaint.setColor(getResources().getColor(R.color.shady));
        subTextPaint.setTextSize(25f);

        miniTextPaint = new Paint();
        miniTextPaint.setAntiAlias(true);
        miniTextPaint.setColor(getResources().getColor(R.color.white));
        miniTextPaint.setTextSize(20f);
        miniTextPaint.setStrokeWidth(5f);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setColor(getResources().getColor(R.color.white));
        miniTextPaint.setStrokeWidth(1f);

        needle = new Paint();
        needle.setAntiAlias(true);
        needle.setColor(getResources().getColor(R.color.white));

        p = new Paint();
        p.setAntiAlias(true);
        p.setColor(getResources().getColor(R.color.light));

        mAnimator = new ValueAnimator();
        mAnimator.setDuration(2000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addUpdateListener(this);

        mAnimator.setFloatValues(0f, 1f);
        mAnimator.start();

        setupVitals();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        // Get our interpolated float from the animation
        mAnimatingFraction = animation.getAnimatedFraction();
        invalidate();
    }

    private void setupVitals() {
        vitals = new HashMap<String, Float>();
        vitals.put("bpm", 0f);
        vitals.put("spo2", 0f);
        vitals.put("si", 0f);
    }
    private void SetupAttributes(AttributeSet attrs) {
        // Obtain a typed array of attributes
        TypedArray a = getContext()
                .getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ShapeSelectorView, 0, 0);

        // Extract custom attributes into member variables
        try {
            shapeColor = a.getColor(R.styleable.ShapeSelectorView_shapeColor, Color.BLACK);
            displayShapeName = a.getBoolean(R.styleable.ShapeSelectorView_displayShapeName, false);

        } finally {
            // Typed objects are stored and must be recycled
            a.recycle();
        }
    }

    public boolean isDisplayShapeName() {
        return displayShapeName;
    }

    public void setDisplayingShapeName(boolean state) {
        this.displayShapeName = state;
        invalidate();
        requestLayout();
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int color) {
        this.shapeColor = color;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRoundReadingShape(canvas);
    }

    private void drawRoundReadingShape(Canvas canvas) {

        float screenWidth = getWidth();
        float screenHeight = getHeight();
        float screenWidthFrac = 0.9f;
        float topMarginFrac = 0.85F;

        String s;
        float strPos = 0f;
        float linePos = 0f;
        int bw, bh;
        float bp = 0f;

        float rectLeft = screenWidth * (1 - screenWidthFrac)/2;
        float rectTop = screenHeight * topMarginFrac;
        float rectWidth = screenWidth * screenWidthFrac;
        float rectHeight = screenHeight * 0.15f;
        float rectRight = rectLeft + rectWidth;
        float rectBottom = rectTop + rectHeight;
        float cy = rectTop + rectHeight/2;
        float dividerTopY = cy - 0.3f * rectHeight;
        float dividerBottomY = cy + 0.3f * rectHeight;

        canvas.drawRoundRect(rectLeft, rectTop, rectRight, rectBottom, 20f, 20f, paintShape);

        float leftMargin = (screenWidth * screenWidthFrac) * 0.15f;
        float rightMargin = leftMargin;

        float bpm = vitals.get("bpm");
        strPos = rectLeft + leftMargin;

        if (vitals.get("bpm") == 0f)
            drawSpinner(canvas, strPos, cy, rectHeight);
        else
            drawReadings(canvas, strPos, String.valueOf(bpm), "HEART RATE", cy, bp);

        linePos = screenWidth * (1 - screenWidthFrac)/2 + rectWidth/3;
        canvas.drawLine(linePos, dividerTopY, linePos, dividerBottomY, dividerPaint);

        strPos = screenWidth * (1 - screenWidthFrac)/2 + rectWidth/2;
        drawReadings(canvas, strPos, "99", "SPO2 (%)", cy, bp);

        linePos = screenWidth * (1 - screenWidthFrac)/2 + 2 * (rectWidth/3);
        canvas.drawLine(linePos, dividerTopY, linePos, dividerBottomY, dividerPaint);

        strPos = rectLeft + rectWidth - rightMargin;
        drawReadings(canvas, strPos, "3", "SI(/10)", cy, bp);

    }

    private void drawDivider(Canvas canvas, float linePos, float verticalPos, float rectHeight) {
        canvas.drawLine(linePos,
                verticalPos - 0.3f * rectHeight,
                linePos,
                verticalPos + 0.3f * rectHeight,
                dividerPaint);
    }

    private void drawSpinner(Canvas canvas, float xStartPos, float yStart,
                             float rectHeight) {
        float radius = 0.3f * rectHeight;
        RectF oval = new RectF(xStartPos - radius, yStart - radius,
                xStartPos + radius, yStart + radius);

        canvas.drawCircle(xStartPos, yStart,
                    0.3f * rectHeight, needle);
        float startAngle = -90f + 360f * mAnimatingFraction;
        float endAngle = 90f + 360f * mAnimatingFraction;
        canvas.drawArc(oval, startAngle, 90f, true, paintShape);

//        canvas.drawArc(xStartPos - 0.3f * rectHeight,
//                yStart - 0.3f * rectHeight,
//                xStartPos + 0.3f * rectHeight,
//                yStart + 0.3f * rectHeight,
//                startAngle, 50f, true,
//                paintShape);

        canvas.drawCircle(xStartPos, yStart, 0.25f * rectHeight, paintShape);

    }

    private void drawReadings(Canvas canvas, float strPos, String valReading, String descReading,
                              float verticalPos, float textMargin) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(valReading, 0, valReading.length(), bounds);
        float boundsHeight = bounds.height();
        float boundsWidth = bounds.width();
        float xPos = strPos - boundsWidth/2;
        float yPos = verticalPos;
        canvas.drawText(valReading, xPos, yPos, textPaint);

        subTextPaint.getTextBounds(descReading, 0, descReading.length(), bounds);
        boundsWidth = bounds.width();
        xPos = strPos - boundsWidth/2;
        yPos = verticalPos + boundsHeight + textMargin;
        canvas.drawText(descReading, xPos, yPos, subTextPaint);
    }

    private void setupPaint() {
        paintShape = new Paint();
        paintShape.setStyle(Paint.Style.FILL);
        paintShape.setColor(shapeColor);
        paintShape.setTextSize(30);
    }

}

