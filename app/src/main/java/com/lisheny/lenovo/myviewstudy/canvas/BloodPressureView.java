package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.lisheny.lenovo.myviewstudy.R;

/**
 * 血压测量过程自定义view
 * Created by LENOVO on 2016/11/8.
 */
public class BloodPressureView extends View {

    //可否点击，默认可点击 true
    private boolean isSetClickable = true;

    //手机屏幕宽高
    private int width;
    private int height;

    private Context mContext;
    private LinearGradient mShader;
    private RectF mOval, mOval1;
    private Paint mBigPaint;                               //最外层圆弧Paint
    private Paint mtextPaint, mtextPaint1, mtextPaint2;
    private Paint mPaint;                                  //内圆弧
    private Paint mCirclePaint;                            //中心圆
    //渐变始末颜色
    private int mFgColorStart = 0xff65c0ea;
    private int mFgColorEnd = 0xffffffff;
    private float mStartAngle = 0;                         //圆弧开始角度
    private float mPercent = 45;                           //渐变圆弧占圆的百分比
    private String battrey = "";                           //电量显示
    private String showText = "0";                         //xxxx

    public void setInfo(String battrey, String showText) {
        this.battrey = battrey;
        this.showText = showText;
        invalidate();
    }

    /*
    设置点击是否失效
     */
    public void settingSetClickable(Boolean isSetClickable) {
        this.isSetClickable = isSetClickable;
    }

    public BloodPressureView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public BloodPressureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public void init() {
        WindowManager manager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE
        );
        width = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();

        mBigPaint = new Paint();
        mBigPaint.setAntiAlias(true);
        mBigPaint.setColor(0xff65c0ea);
        mBigPaint.setStyle(Paint.Style.STROKE);
        mBigPaint.setStrokeWidth(4);

        mtextPaint = new Paint();
        mtextPaint.setStyle(Paint.Style.STROKE);
        mtextPaint.setDither(true);
        mtextPaint.setAntiAlias(true);
        mtextPaint.setColor(Color.GRAY);
        mtextPaint.setTextSize(width / 30);

        mtextPaint1 = new Paint();
        mtextPaint1.setStyle(Paint.Style.STROKE);
        mtextPaint1.setDither(true);
        mtextPaint1.setTextAlign(Paint.Align.CENTER);
        mtextPaint1.setAntiAlias(true);
        mtextPaint1.setColor(0xffffffff);
        mtextPaint1.setTextSize(width / 10);

        mtextPaint2 = new Paint();
        mtextPaint2.setStyle(Paint.Style.STROKE);
        mtextPaint2.setDither(true);
        mtextPaint2.setAntiAlias(true);
        mtextPaint2.setColor(0xffffffff);
        mtextPaint2.setTextSize(width / 40);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(12);
        mPaint.setColor(0xffe8e8e8);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(0xff65c0ea);

        mOval = new RectF(width / 3, width / 25, 2 * width / 3, width / 25 + width / 3);
        mOval1 = new RectF(width / 3 + dp2px(10), width / 25 + dp2px(10), 2 * width / 3 - dp2px(10), width / 25 + width / 3 - dp2px(10));
    }

    private int dp2px(float dp) {
        return (int) (mContext.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //参照矩形
//        canvas.drawRect(width/3,width/25,2*width/3,width/25+width/3,mtextPaint);
        //最外层圆弧
        canvas.drawArc(mOval, 180, 180, false, mBigPaint);
        //背景圆弧
        mPaint.setShader(null);
        canvas.drawArc(mOval1, 0, 360, false, mPaint);
        //绘制渐变狐
        mPaint.setShader(mShader);
        canvas.drawArc(mOval1, mStartAngle, mPercent * 3.6f, false, mPaint);
        //绘制中心圆
        canvas.drawCircle(width / 2, width / 6 + width / 25, dp2px(42), mCirclePaint);
        //绘制直线
        canvas.drawLine(0, width / 6 + width / 25, width / 3, width / 6 + width / 25, mBigPaint);
        canvas.drawLine(2 * width / 3, width / 6 + width / 25, width, width / 6 + width / 25, mBigPaint);

        //bitmap绘制
        Bitmap bleBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_ble_ic);
        Bitmap battreyBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_battery_ic);
        Bitmap pressureBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_pressure_ic);
        canvas.drawBitmap(bleBitmap, width / 7 - dp2px(12), width / 6 + width / 25 - dp2px(23), mPaint);
        canvas.drawBitmap(battreyBitmap, 5 * width / 6 - dp2px(36), width / 6 + width / 25 - dp2px(21), mPaint);
        canvas.drawBitmap(pressureBitmap, 5 * width / 6 - dp2px(28), width / 3 - 3 * width / 30, mPaint);

        //绘制文字
        try {
            canvas.drawText("已连接", width / 7, width / 6 + width / 25 - dp2px(10), mtextPaint);
            canvas.drawText("battrey " + battrey, 5 * width / 6 - dp2px(24), width / 6 + width / 25 - dp2px(10), mtextPaint);
            canvas.drawText(showText, width / 2, width / 6 + width / 25 + dp2px(12), mtextPaint1);
            canvas.drawText("停止", 5 * width / 6 - dp2px(16), width / 6 + width / 3 - 6 * width / 28+dp2px(6), mtextPaint2);
//            canvas.drawText(getContext().getString(R.string.view_messuer), 5 * width / 6 - dp2px(16), width / 6 + width / 3 - 5 * width / 28-dp2px(1), mtextPaint2);
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置颜色渐变
        mShader = new LinearGradient(mOval.left, mOval.top,
                mOval.left, mOval.bottom, mFgColorStart, mFgColorEnd, Shader.TileMode.MIRROR);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取手指位置
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (isSetClickable && x > (5 * width / 6 - dp2px(25)) &&
                        x < (5 * width / 6 + dp2px(12)) &&
                        y < (width / 6 + width / 3 - 5 * width / 28 + dp2px(8)) &&
                        y > (width / 6 + width / 3 - 8 * width / 28) + dp2px(8)) {
                    if (listener != null) {
                        listener.onStop();
                    }
                }
                break;
        }
        return true;
    }

    public void refreshTheLayout() {
        invalidate();
        requestLayout();
    }

    public float getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(float mStartAngle) {
        this.mStartAngle = mStartAngle + 270;
        refreshTheLayout();
    }

    //定义一个接口对象listerner
    private OnStopPressureListener listener;

    //获得接口对象的方法。
    public void setOnStopPressureListener(OnStopPressureListener listener) {
        this.listener = listener;
    }

    //定义一个接口
    public interface OnStopPressureListener {
        public void onStop();
    }
}