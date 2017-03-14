package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.lisheny.lenovo.myviewstudy.R;

/**
 * 旋转加载圆view
 * Created by LENOVO on 2016/11/1.
 */
public class ColorfulRingProgressView extends View {


    private float mPercent = 75;
    private float mStrokeWidth;
    private int mBgColor = 0xffe1e1e1;
    private float mStartAngle = 0;
    private int mFgColorStart = 0xffffe400;
    private int mFgColorEnd = 0xffff4800;

    private LinearGradient mShader;
    private Context mContext;
    private RectF mOval,mOval1,mOval2;
    private Paint mPaint;
    private Paint mBigPaint;   //最外层圆弧Paint
    //屏幕宽高
    int width;
    int height;

    public ColorfulRingProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ColorfulRingProgressView,
                0,0);

        try {
            mBgColor = a.getColor(R.styleable.ColorfulRingProgressView_bgColor, 0xffe1e1e1);
            mFgColorEnd = a.getColor(R.styleable.ColorfulRingProgressView_fgColorEnd, 0xffff4800);

            mFgColorStart = a.getColor(R.styleable.ColorfulRingProgressView_fgColorStart, 0xffffe400);
            mPercent = a.getFloat(R.styleable.ColorfulRingProgressView_percent, 75);
            mStartAngle = a.getFloat(R.styleable.ColorfulRingProgressView_startAngle, 0)+270;
            mStrokeWidth = a.getDimensionPixelSize(R.styleable.ColorfulRingProgressView_strokeWidth, dp2px(21));
            System.out.println("**** m"+mStrokeWidth);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
//        WindowManager manager = (WindowManager) getContext().getSystemService(
//                Context.WINDOW_SERVICE
//        );
//        width = manager.getDefaultDisplay().getWidth();
//        height = manager.getDefaultDisplay().getHeight();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mBigPaint = new Paint();
        mBigPaint.setAntiAlias(true);
        mBigPaint.setColor(0xff65c0ea);
        mBigPaint.setStyle(Paint.Style.STROKE);
        mBigPaint.setStrokeWidth(4);

    }

    private int dp2px(float dp) {
        return (int) (mContext.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int xp = getPaddingLeft() + getPaddingRight();
        int yp = getPaddingBottom() + getPaddingTop();
        mPaint.setShader(null);
        mPaint.setColor(mBgColor);
        canvas.drawRect(mOval,mPaint);
        canvas.drawLine(0,((getHeight() - yp) - mStrokeWidth-dp2px(24))/2,getPaddingLeft() + mStrokeWidth+dp2px(24),((getHeight() - yp) - mStrokeWidth-dp2px(24))/2, mBigPaint );
        canvas.drawArc(mOval, 180, 180, false, mBigPaint);
        canvas.drawArc(mOval1, 0, 360, false, mPaint);

        mPaint.setShader(mShader);
        canvas.drawArc(mOval1, mStartAngle, mPercent * 3.6f, false, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updateOval();
        //设置颜色渐变
        mShader = new LinearGradient(mOval.left, mOval.top,
                mOval.left, mOval.bottom, mFgColorStart, mFgColorEnd, Shader.TileMode.MIRROR);
    }

    public float getPercent() {
        return mPercent;
    }

    public void setPercent(float mPercent) {
        this.mPercent = mPercent;
        refreshTheLayout();
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        mPaint.setStrokeWidth(mStrokeWidth);
        updateOval();
        refreshTheLayout();
    }

    private void updateOval() {
        int xp = getPaddingLeft() + getPaddingRight();
        int yp = getPaddingBottom() + getPaddingTop();
        mOval = new RectF(getPaddingLeft() + mStrokeWidth+dp2px(24), getPaddingTop() + mStrokeWidth+dp2px(24),
                getPaddingLeft() + (getWidth() - xp) - mStrokeWidth-dp2px(24),
                getPaddingTop() + (getHeight() - yp) - mStrokeWidth-dp2px(24));
        mOval1 = new RectF(getPaddingLeft() + mStrokeWidth+dp2px(32), getPaddingTop() + mStrokeWidth+dp2px(32),
                getPaddingLeft() + (getWidth() - xp) - mStrokeWidth-dp2px(32),
                getPaddingTop() + (getHeight() - yp) - mStrokeWidth-dp2px(32));
        mOval2 = new RectF(getPaddingLeft() + mStrokeWidth+dp2px(16), getPaddingTop() + mStrokeWidth+dp2px(16),
                getPaddingLeft() + (getWidth() - xp) - mStrokeWidth-dp2px(16),
                getPaddingTop() + (getHeight() - yp) - mStrokeWidth-dp2px(16));
    }

    public void setStrokeWidthDp(float dp) {
        this.mStrokeWidth = dp2px(dp);
        mPaint.setStrokeWidth(mStrokeWidth);
        updateOval();
        refreshTheLayout();
    }

    public void refreshTheLayout() {
        invalidate();
        requestLayout();
    }

    public int getFgColorStart() {
        return mFgColorStart;
    }

    public void setFgColorStart(int mFgColorStart) {
        this.mFgColorStart = mFgColorStart;
        mShader = new LinearGradient(mOval.left, mOval.top,
                mOval.left, mOval.bottom, mFgColorStart, mFgColorEnd, Shader.TileMode.MIRROR);
        refreshTheLayout();
    }

    public int getFgColorEnd() {
        return mFgColorEnd;
    }

    public void setFgColorEnd(int mFgColorEnd) {
        this.mFgColorEnd = mFgColorEnd;
        mShader = new LinearGradient(mOval.left, mOval.top,
                mOval.left, mOval.bottom, mFgColorStart, mFgColorEnd, Shader.TileMode.MIRROR);
        refreshTheLayout();
    }


    public float getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(float mStartAngle) {
        this.mStartAngle = mStartAngle + 270;
        refreshTheLayout();
    }
}