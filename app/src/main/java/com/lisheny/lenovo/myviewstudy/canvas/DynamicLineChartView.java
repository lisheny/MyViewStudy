package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 自定义view，用来展示测量过程的测量动画
 * Created by LENOVO on 2016/11/1.
 */
public class DynamicLineChartView extends View {
    //屏幕宽高
    int width;
    int height;

    private int XPoint = 0;
    private int YPoint = 260;
    private int XScale = 8; // 刻度长度
    private int YScale = 40;
    private int XLength = 380;
    private int YLength = 240;

    private int MaxDataSize =12;

    private List<Integer> data = new ArrayList<Integer>();

    private void init() {

        WindowManager manager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE
        );
        width = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();

        //可调节波浪高度
        YPoint = 80 * width /100;
        XPoint = 0;
        XLength = width;
        YLength = 2 * width /5;

        //可调节波浪宽度
        XScale = 3*width/25;

        //可调节波浪高度和变化区间高度
        YScale = 7*width/100;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x1234) {
                DynamicLineChartView.this.invalidate();
            }
        };
    };

    public DynamicLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (data.size() >= MaxDataSize) {
                        data.remove(0);
                    }
                    data.add(new Random().nextInt(4) + 8);
//                    Log.i("Random", String.valueOf(new Random().nextInt(4) + 1));
                    handler.sendEmptyMessage(0x1234);
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(0xffaddbed);
        paint.setDither(true);
        PathEffect pathEffect = new CornerPathEffect(50);
        paint.setPathEffect(pathEffect);

        // 画X轴
//        canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint);

        paint.setStyle(Paint.Style.FILL);
        if (data.size() > 1) {
            Path path = new Path();
            path.moveTo(XPoint, YPoint);
            for (int i = 0; i < data.size(); i++) {
                path.lineTo(XPoint + i * XScale, YPoint - data.get(i) * YScale);
//                Log.i("data.size():", String.valueOf( YPoint - data.get(i) * YScale));
            }
            path.lineTo(XPoint + (data.size() - 1) * XScale, YPoint);
            canvas.drawPath(path, paint);
        }
    }
}
