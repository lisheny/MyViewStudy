package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * 条形图
 * Created by LENOVO on 2016/10/8.
 */
public class BarChartView extends View {

    private int width;
    private int height;
    private Context mContext;
    //画
    private Paint paint;
    //重度
    private Paint paint1;
    //中度
    private Paint paint2;
    //轻度
    private Paint paint3;
    //正常
    private Paint paint4;
    //偏低
    private Paint paint5;
    //偏高
    private Paint paint6;

    private int mSevere = 2;
    private int mModerate = 3;
    private int mMild = 9;
    private int mUptilted = 3;
    private int mNormal = 13;
    private int mLow = 4;
    private int sev, mod, mil, upt, nor, low, max = 0;


    public void setInfo(int severe, int moderate, int mild, int mUptilted, int normal, int loww) {

        this.mSevere = severe;
        this.mModerate = moderate;
        this.mMild = mild;
        this.mUptilted = mUptilted;
        this.mNormal = normal;
        this.mLow = loww;


        max = 0;
        if (max < mSevere) max = severe;
        if (max < mMild) max = mild;
        if (max < mModerate) max = moderate;
        if (max < mUptilted) max = mUptilted;
        if (max < mNormal) max = normal;
        if (max < mLow) max = low;
        invalidate();

        if (max < 3 && max > 0) {
            sev = 40 * mSevere;
            mod = 40 * mModerate;
            mil = 40 * mMild;
            upt = 40 * mUptilted;
            nor = 40 * mNormal;
            low = 40 * mLow;
        } else if (max >= 3 && max < 5) {
            sev = 12 * mSevere;
            mod = 12 * mModerate;
            mil = 12 * mMild;
            upt = 12 * mUptilted;
            nor = 12 * mNormal;
            low = 12 * mLow;
        } else if (max >= 5 && max < 10) {
            sev = 8 * mSevere;
            mod = 8 * mModerate;
            mil = 8 * mMild;
            upt = 8 * mUptilted;
            nor = 8 * mNormal;
            low = 8 * mLow;
        } else if (max >= 10 && max < 20) {
            sev = 5 * mSevere;
            mod = 5 * mModerate;
            mil = 5 * mMild;
            upt = 5 * mUptilted;
            nor = 5 * mNormal;
            low = 5 * mLow;

        } else if (max >= 20 && max < 35) {
            sev = (int) (2.5 * mSevere);
            mod = (int) (2.5 * mModerate);
            mil = (int) (2.5 * mMild);
            upt = (int) (2.5 * mUptilted);
            nor = (int) (2.5 * mNormal);
            low = (int) (2.5 * mLow);

        } else if (max >= 35 && max < 50) {
            sev = 2 * mSevere;
            mod = 2 * mModerate;
            mil = 2 * mMild;
            upt = 2 * mUptilted;
            nor = 2 * mNormal;
            low = 2 * mLow;

        } else if (max >= 50 && max < 70) {
            if (mSevere < 3) {
                sev = 2 * mSevere;
            } else {
                sev = (int) (1.3 * mSevere);
            }

            if (mModerate < 3) {
                mod = 2 * mModerate;
            } else {
                mod = (int) (1.3 * mModerate);
            }

            if (mUptilted < 3) {
                upt = 2 * mUptilted;
            } else {
                upt = (int) (1.3 * mUptilted);
            }

            if (mNormal < 3) {
                nor = 2 * mNormal;
            } else {
                nor = (int) (1.3 * mNormal);
            }

            if (mLow < 3) {
                low = 2 * mLow;
            } else {
                low = (int) (1.3 * mLow);
            }
        } else if (max >= 70 && max < 100) {
            sev = (int) (0.93 * mSevere);
            mod = (int) (0.93 * mModerate);
            mil = (int) (0.93 * mMild);
            upt = (int) (0.93 * mUptilted);
            nor = (int) (0.93 * mNormal);
            low = (int) (0.93 * mLow);

        } else if (max >= 100) {
            sev = (int) (mSevere);
            mod = (int) (mModerate);
            mil = (int) (mMild);
            upt = (int) (mUptilted);
            nor = (int) (mNormal);
            low = (int) (mLow);

        } else {
            sev = (int) (1.1 * mSevere);
            mod = (int) (1.1 * mModerate);
            mil = (int) (1.1 * mMild);
            upt = (int) (1.1 * mUptilted);
            nor = (int) (1.1 * mNormal);
            low = (int) (1.1 * mLow);
        }
    }

    public BarChartView(Context context) {
        super(context);
        init();
        this.mContext = context;
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        this.mContext = context;
    }

    public void init() {
        WindowManager manager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE
        );
        width = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(width / 32);
        paint.setAntiAlias(true);
        //重度
        paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(0xFFE41420);
        paint1.setAntiAlias(true);
        //中度
        paint2 = new Paint();
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(0xFFE31320);
        paint2.setAntiAlias(true);
        //轻度
        paint3 = new Paint();
        paint3.setStyle(Paint.Style.FILL);
        paint3.setColor(0xFFE31320);
        paint3.setAntiAlias(true);
        //正常
        paint4 = new Paint();
        paint4.setStyle(Paint.Style.FILL);
        paint4.setColor(0xFF009B55);
        paint4.setAntiAlias(true);
        //偏低
        paint5 = new Paint();
        paint5.setStyle(Paint.Style.FILL);
        paint5.setColor(0xFF81b92b);
        paint5.setAntiAlias(true);
        //偏高
        paint6 = new Paint();
        paint6.setStyle(Paint.Style.FILL);
        paint6.setColor(0xFFFBE20C);
        paint6.setAntiAlias(true);
    }

    /**
     * 重度
     */
    private void drawSevere(Canvas canvas, Paint paint) {
        canvas.drawRect(width / 25, 2 * width / 25, 2 * width / 23, 4 * width / 25 - 2, paint);

        if (mSevere >= 95) {
            sev = 95;
        }
        canvas.drawRect(2 * width / 23 + 10, 2 * width / 25 + 10, sev * width / 120 + 2 * width / 23, 4 * width / 25 - 10, paint);
    }

    /**
     * 中度
     */
    private void drawModerate(Canvas canvas, Paint paint) {

        if (mModerate >= 95) {
            mod = 95;
        }
        canvas.drawRect(width / 25, 4 * width / 25, 2 * width / 23, 6 * width / 25 - 2, paint);
        canvas.drawRect(2 * width / 23 + 10, 4 * width / 25 + 10, mod * width / 120 + 2 * width / 23, 6 * width / 25 - 10, paint);
    }

    /**
     * 轻度
     */
    private void drawMild(Canvas canvas, Paint paint) {

        if (mMild >= 95) {
            mil = 95;
        }
        canvas.drawRect(width / 25, 6 * width / 25, 2 * width / 23, 8 * width / 25 - 2, paint);
        canvas.drawRect(2 * width / 23 + 10, 6 * width / 25 + 10, mil * width / 120 + 2 * width / 23, 8 * width / 25 - 10, paint);
    }

    /**
     * 偏高
     */
    private void drawUptilted(Canvas canvas, Paint paint) {

        if (mUptilted >= 95) {
            upt = 95;
        }
        canvas.drawRect(width / 25, 8 * width / 25, 2 * width / 23, 10 * width / 25 - 2, paint);
        canvas.drawRect(2 * width / 23 + 10, 8 * width / 25 + 10, upt * width / 120 + 2 * width / 23, 10 * width / 25 - 10, paint);
    }

    /**
     * 正常
     */
    private void drawNormal(Canvas canvas, Paint paint) {

        if (mNormal >= 95) {
            nor = 95;
        }
        canvas.drawRect(width / 25, 10 * width / 25, 2 * width / 23, 12 * width / 25 - 2, paint);
        canvas.drawRect(2 * width / 23 + 10, 10 * width / 25 + 10, nor * width / 120 + 2 * width / 23, 12 * width / 25 - 10, paint);
    }

    /**
     * 偏低
     *
     * @param canvas
     */
    private void drawLow(Canvas canvas, Paint paint) {

        if (mLow >= 95) {
            low = 95;
        }

        canvas.drawRect(width / 25, 12 * width / 25, 2 * width / 23, 14 * width / 25 - 2, paint);
        canvas.drawRect(2 * width / 23 + 10, 12 * width / 25 + 10, low * width / 120 + 2 * width / 23, 14 * width / 25 - 10, paint);
    }

    /**
     * 绘文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas, Paint paint) {
        canvas.drawRect(2 * width / 23 + 9, 2 * width / 25, 2 * width / 23 + 10, 14 * width / 25, paint);

        canvas.drawText("重", width / 21, 3 * width / 26, paint);
        canvas.drawText("度", width / 21, 4 * width / 26, paint);
        canvas.drawText("中", width / 21, 5 * width / 26, paint);
        canvas.drawText("度", width / 21, 6 * width / 26, paint);
        canvas.drawText("轻", width / 21, 7 * width / 26, paint);
        canvas.drawText("度", width / 21, 8 * width / 26, paint);
        canvas.drawText("偏", width / 21, 9 * width / 26, paint);
        canvas.drawText("高", width / 21, 10 * width / 26, paint);
        canvas.drawText("正", width / 21, 11 * width / 26 + 5, paint);
        canvas.drawText("常", width / 21, 12 * width / 26 + 5, paint);
        canvas.drawText("偏", width / 21, 13 * width / 26 + 5, paint);
        canvas.drawText("低", width / 21, 14 * width / 26 + 5, paint);

        canvas.drawText(String.valueOf(mSevere), sev * width / 120 + 2 * width / 20, 4 * width / 25 - 25, paint);
        canvas.drawText(String.valueOf(mModerate), mod * width / 120 + 2 * width / 20, 6 * width / 25 - 25, paint);
        canvas.drawText(String.valueOf(mMild), mil * width / 120 + 2 * width / 20, 8 * width / 25 - 30, paint);
        canvas.drawText(String.valueOf(mUptilted), upt * width / 120 + 2 * width / 20, 10 * width / 25 - 25, paint);
        canvas.drawText(String.valueOf(mNormal), nor * width / 120 + 2 * width / 20, 12 * width / 25 - 25, paint);
        canvas.drawText(String.valueOf(mLow), low * width / 120 + 2 * width / 20, 14 * width / 25 - 25, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        invalidate();
        super.onDraw(canvas);
        drawSevere(canvas, paint1);
        drawModerate(canvas, paint2);
        drawMild(canvas, paint3);
        drawUptilted(canvas, paint6);
        drawNormal(canvas, paint4);
        drawLow(canvas, paint5);
        drawText(canvas, paint);
    }
}
