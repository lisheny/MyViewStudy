package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 心电图1
 * Created by LENOVO on 2016/10/19.
 */
public class CardiographView extends View {
    //画笔
    protected Paint mPaint;
    //折现的颜色
    protected int mLineColor = Color.parseColor("#76f112");
    //自身的大小
    protected int mWidth,mHeight;
    //心电图折现
    protected Path mPath ;

    //y的值
    private int yPoin = 0;
    public CardiographView(Context context) {
        this(context,null);
    }

    public CardiographView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CardiographView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPath = new Path();
    }

    //取得脉搏波数值
    public void setInfo(String str) {
        yPoin = Integer.valueOf(str);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        invalidate();
        drawPath(canvas);
        scrollBy(3,0);
        postInvalidateDelayed(10);
    }

    private void drawPath(Canvas canvas) {
        // 重置path
        mPath.reset();

        //用path模拟一个心电图样式
        mPath.moveTo(0,mHeight/2);
        int tmp =0;
        for(int i = 0;i<80;i++) {
            mPath.lineTo(tmp+10, 50);
            mPath.lineTo(tmp+35, mHeight / 4 + 25);
            mPath.lineTo(tmp+40, mHeight / 4);

            mPath.lineTo(tmp+100, mHeight / 4);
            tmp = tmp+100;
        }
        //设置画笔style
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(5);
        canvas.drawPath(mPath,mPaint);

    }
}