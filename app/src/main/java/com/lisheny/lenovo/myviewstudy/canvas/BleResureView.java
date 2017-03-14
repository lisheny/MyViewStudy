package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.lisheny.lenovo.myviewstudy.R;

/**
 * 蓝牙测量结果视图
 * Created by LENOVO on 2016/10/23.
 */
public class BleResureView extends View {
    private int width;
    private int height;
    private Context mContext;
    //画笔
    private Paint textPaint;
    //背景
    private Paint bgPaint;
    //重度
    private Paint severePaint;
    //中度
    private Paint moderatePaint;
    //轻度
    private Paint mildPaint;
    //正常
    private Paint normalpaint;

    private int sys;
    private int dia;
    private int pr;
    private boolean arrhythmailogo;

    public BleResureView(Context context) {
        super(context);
        this.mContext = context;
    }

    public BleResureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public BleResureView(Context context, int sys, int dia, int pr, boolean arrhythmiaLogo) {
        super(context);
        this.mContext = context;
        this.sys = sys;
        this.dia = dia;
        this.pr = pr;
        this.arrhythmailogo = arrhythmiaLogo;

    }

    public void setInfo(int sys, int dia, int pr, boolean arrhythmiaLogo) {
        this.sys = sys;
        this.dia = dia;
        this.pr = pr;
        this.arrhythmailogo = arrhythmiaLogo;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        drawBleRect(canvas);
        drawTrigon(canvas, bgPaint);
        //绘制圆角矩形
        RectF rectF = new RectF(9 * width / 24, width / 25 - 10, width - width / 25, 13 * width / 25 - 10);
        canvas.drawRoundRect(rectF, 25, 25, bgPaint);
        drawText(canvas, textPaint);
        drawBitmaps(canvas, bgPaint);
    }

    public void init() {
        WindowManager manager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE
        );
        width = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setDither(true);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(width/30);

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.BLUE);

        severePaint = new Paint();
        severePaint.setStyle(Paint.Style.FILL);
        severePaint.setAntiAlias(true);
        severePaint.setColor(Color.RED);

        moderatePaint = new Paint();
        moderatePaint.setStyle(Paint.Style.FILL);
        moderatePaint.setAntiAlias(true);
        moderatePaint.setColor(Color.BLACK);

        mildPaint = new Paint();
        mildPaint.setStyle(Paint.Style.FILL);
        mildPaint.setAntiAlias(true);
        mildPaint.setColor(Color.YELLOW);

        normalpaint = new Paint();
        normalpaint.setStyle(Paint.Style.FILL);
        normalpaint.setAntiAlias(true);
        normalpaint.setColor(Color.GREEN);
    }

    /**
     * 绘文字
     */
    private void drawText(Canvas canvas, Paint paint) {
        canvas.drawText("重度高血压", width / 25, 2 * width / 25, paint);
        canvas.drawText("中度高血压", width / 25, 4 * width / 25, paint);
        canvas.drawText("轻度高血压", width / 25, 6 * width / 25, paint);
        canvas.drawText("正常偏高值", width / 25, 8 * width / 25, paint);
        canvas.drawText("正常血压值", width / 25, 10 * width / 25, paint);
        canvas.drawText("理想血压值", width / 25, 12 * width / 25, paint);

        canvas.drawText("高压： " + String.valueOf(sys) + "mmHg", 13 * width / 24, 3 * width / 25, paint);
        canvas.drawText("低压： " + String.valueOf(dia) + "mmHg", 13 * width / 24, 5 * width / 25, paint);
        canvas.drawText("脉压： " + String.valueOf(sys - dia) + "mmHg", 13 * width / 24, 7 * width / 25, paint);
        canvas.drawText("脉率： " + String.valueOf(pr) + "bpm", 13 * width / 24, 10 * width / 25, paint);
        canvas.drawText("心律不齐", 13 * width / 24, 11 * width / 25 + 10, paint);
    }

    /**
     * 画血压指示条
     */
    private void drawBleRect(Canvas canvas) {
        canvas.drawRect(width / 4, width / 25 - 10, width / 4 + 20, 3 * width / 25 - 10, severePaint);
        canvas.drawRect(width / 4, 3 * width / 25 - 8, width / 4 + 20, 5 * width / 25 - 10, moderatePaint);
        canvas.drawRect(width / 4, 5 * width / 25 - 8, width / 4 + 20, 7 * width / 25 - 10, mildPaint);
        canvas.drawRect(width / 4, 7 * width / 25 - 8, width / 4 + 20, 9 * width / 25 - 10, normalpaint);
        canvas.drawRect(width / 4, 9 * width / 25 - 8, width / 4 + 20, 11 * width / 25 - 10, normalpaint);
        canvas.drawRect(width / 4, 11 * width / 25 - 8, width / 4 + 20, 13 * width / 25 - 10, normalpaint);
    }

    /**
     * 画三角形
     */
    private void drawTrigon(Canvas canvas, Paint paint) {
        Path path = new Path();
        if (sys >= 180) {
            path.moveTo(width / 4 + 20, 2 * width / 25);
            path.lineTo(9 * width / 24, width / 25 + 10);
            path.lineTo(9 * width / 24, 3 * width / 25 - 10);
        } else if (sys >= 160 && sys <= 179) {
            path.moveTo(width / 4 + 20, 4 * width / 25 - 10);
            path.lineTo(9 * width / 24, 3 * width / 25);
            path.lineTo(9 * width / 24, 5 * width / 25 - 20);
        } else if (sys >= 140 && sys <= 159) {
            path.moveTo(width / 4 + 20, 6 * width / 25 - 10);
            path.lineTo(9 * width / 24, 5 * width / 25);
            path.lineTo(9 * width / 24, 7 * width / 25 - 20);
        } else if (sys >= 130 && sys <= 139) {
            path.moveTo(width / 4 + 20, 8 * width / 25 - 10);
            path.lineTo(9 * width / 24, 7 * width / 25);
            path.lineTo(9 * width / 24, 9 * width / 25 - 20);
        } else if (sys >= 120 && sys <= 129) {
            path.moveTo(width / 4 + 20, 10 * width / 25 - 10);
            path.lineTo(9 * width / 24, 9 * width / 25);
            path.lineTo(9 * width / 24, 11 * width / 25 - 20);
        } else if (sys <= 119) {
            path.moveTo(width / 4 + 20, 12 * width / 25 - 20);
            path.lineTo(9 * width / 24, 11 * width / 25 - 10);
            path.lineTo(9 * width / 24, 13 * width / 25 - 30);
        }
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     * 绘bitmap
     */
    private void drawBitmaps(Canvas canvas, Paint paint) {
        Bitmap bloodBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blood);
        Bitmap ecgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ecg);
        Bitmap heartBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.heart);

        canvas.drawBitmap(bloodBitmap, 11 * width / 23, 2 * width / 25, paint);
        canvas.drawBitmap(heartBitmap, 11 * width / 23, 9 * width / 24, paint);

        Log.i("arrhythmailogo", String.valueOf(arrhythmailogo));
        if (true) {
            canvas.drawBitmap(ecgBitmap, 17 * width / 24 - dp2px(10), 10 * width / 25 + dp2px(6), paint);
        }
    }

    private int dp2px(float dp) {
        return (int) (mContext.getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
