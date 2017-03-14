package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.lisheny.lenovo.myviewstudy.R;


/**
 * 自定义折线图
 * Created by LENOVO on 2016/9/29.
 */
public class LineChartView extends View {

    private Context mContext;
    //屏幕宽高
    int width;
    int height;
    //XY原点
    public int XPoint = 72;
    public int YPoint = 260;
    public float Unit;
    //XY刻度长度
    public float XScale ;
    public float YScale = 65;
    //XY轴长度
    public int XLength = 380;
    public int YLength = 240;

    //判断显示哪个提示信息的标签
    private int tag = 1;

    //XY轴的刻度文字
    public String[] XLabel = {"7-11", "7-12", "7-13", "7-14", "7-15", "7-16", "7-17"};
    public String[] YLabel = {"30", "60", "90", "120", "150", "180", "210"};
    //高压
    public String[] Data1 = new String[0];
    //低压
    public String[] Data2 = new String[0];
    //脉率
    public String[] Data3 = new String[0];

    public LineChartView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public LineChartView(Context context, AttributeSet attrs) {
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
        YPoint = height / 4;
        XPoint = 2 * width / 25;
        XLength = width - 5 * width / 25;
        YLength = height / 2;

//        XScale = 3 * width / 25;
        YScale = width / 16;
        Unit = (float) (YPoint / 270);
    }

    public void setInfo(String[] XLabels, String[] YLabels, String[] pulseRateData,
                        String[] highBloodData, String[] lowBloodData) {
        XLabel = XLabels;
        YLabel = YLabels;
        Data1 = pulseRateData;
        Data2 = highBloodData;
        Data3 = lowBloodData;
        try {
            XScale = (float) (0.95 * XLength / Data1.length);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Data1.length:", "Data1.length 等于0");
        }
        Log.i("XScale", XScale + ": " + Data1.length);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        invalidate();
        Paint textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(width / 45);
        textPaint.setStrokeWidth(3);

        Paint mainPaint = new Paint();
        mainPaint.setStyle(Paint.Style.FILL);
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(Color.GRAY);
        mainPaint.setTextSize(width / 32);
        mainPaint.setStrokeWidth(3);

        Paint highPaint = new Paint();
        highPaint.setStyle(Paint.Style.FILL);
        highPaint.setAntiAlias(true);
        highPaint.setColor(0xFFED7100);
        highPaint.setStrokeWidth(3);

        Paint lowPaint = new Paint();
        lowPaint.setStyle(Paint.Style.FILL);
        lowPaint.setAntiAlias(true);
        lowPaint.setColor(0xFF009B55);
        lowPaint.setStrokeWidth(3);

        // 绘制颜色条说明
        canvas.drawLine(7 * width / 16, width / 25, width / 2, width / 25, highPaint);
        canvas.drawText(getContext().getString(R.string.view_high_blood), width / 2 + 5, width / 20, mainPaint);

        canvas.drawLine(9 * width / 16 + 5, width / 25, 10 * width / 16, width / 25, lowPaint);
        canvas.drawText(getContext().getString(R.string.view_low_blood), 10 * width / 16 + 5, width / 20, mainPaint);

        canvas.drawLine(11 * width / 16 + 5, width / 25, 12 * width / 16, width / 25, mainPaint);
        canvas.drawText(getContext().getString(R.string.view_pr_blood), 12 * width / 16 + 5, width / 20, mainPaint);
        //Y轴
        //画轴线
        canvas.drawLine(XPoint + 45, 18 + (YPoint - YLength), XPoint + 45, YPoint, mainPaint);
        for (int i = 1; i * YScale < YLength; i++) {
            //画刻度
            canvas.drawLine(XPoint + 45, YPoint - i * YScale, XPoint + 35, YPoint - i * YScale, mainPaint);
            try {
                //绘制文字
                //数据填充
                SharedPreferences preferences2 = mContext.getSharedPreferences("Appmessege", Context.MODE_PRIVATE);
                //单位判断：mmgh/kpa
                if (preferences2.getString("unit", "mmgh").equals("mmgh")) {
                    canvas.drawText(YLabel[i], XPoint - 32, YPoint - i * YScale + 5, mainPaint);
                } else {
                    canvas.drawText((String.valueOf(Integer.parseInt(YLabel[i]) / 7.5) + "000").substring(0, 4),
                            XPoint - 32, YPoint - i * YScale + 5, mainPaint);
                }
            } catch (Exception e) {
            }
        }
        //X轴
        //画轴线
        canvas.drawLine(XPoint + 45, YPoint, XPoint + XLength, YPoint, mainPaint);
        for (int i = 0; i * XScale < XLength; i++) {
            //绘制刻度
//            canvas.drawLine(XPoint + 45 + i * XScale, YPoint, XPoint + 45 + i * XScale, YPoint - 10, mainPaint);
            try {
                //绘制刻度文字
//                canvas.drawText(XLabel[i], XPoint + i * XScale + 10, YPoint + 40, mainPaint);

                if (i > 0 && YCoord(Data1[i - 1]) != -999 && YCoord(Data1[i]) != -999 && !Data1[i].equals("0")) {  //保证有效数据

                    canvas.drawLine(XPoint + 45 + (i - 1) * XScale,
                            YCoord(Data1[i - 1]),
                            XPoint + 45 + i * XScale,
                            YCoord(Data1[i]),
                            mainPaint);

                    canvas.drawLine(XPoint + 45 + (i - 1) * XScale,
                            YCoord(Data2[i - 1]),
                            XPoint + 45 + i * XScale,
                            YCoord(Data2[i]),
                            highPaint);

                    canvas.drawLine(XPoint + 45 + (i - 1) * XScale,
                            YCoord(Data3[i - 1]),
                            XPoint + 45 + i * XScale,
                            YCoord(Data3[i]),
                            lowPaint);
                }
            } catch (Exception e) {
//                Log.d("Exception", "没有数据");
            }
        }


        if (tag < Data1.length && tag > 0) {
            invalidate();
            //绘制提示信息
            try {
                if (Data1[tag].equals("0")) tag = Data1.length - 1;
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                canvas.drawRect(tag * XScale + 2 * width / 25 + dp2px(20), YCoord(Data2[tag]) - width / 15, 3 * width / 25 + tag * XScale + 2 * width / 25 + dp2px(20), YCoord(Data2[tag]) - width / 40, highPaint);
                Path path = new Path();
                path.moveTo(XPoint + 45 + tag * XScale, YCoord(Data2[tag]));
                path.lineTo(tag * XScale + 2 * width / 25 + dp2px(25), YCoord(Data2[tag]) - width / 40);
                path.lineTo(tag * XScale + 2 * width / 25 + dp2px(35), YCoord(Data2[tag]) - width / 40);
                path.close();
                canvas.drawPath(path, highPaint);

                canvas.drawRect(tag * XScale + 2 * width / 25 + dp2px(22), YCoord(Data3[tag]) - width / 15, 3 * width / 25 + tag * XScale + 2 * width / 25 + dp2px(16), YCoord(Data3[tag]) - width / 40, lowPaint);
                Path path2 = new Path();
                path2.moveTo(XPoint + 45 + tag * XScale, YCoord(Data3[tag]));
                path2.lineTo(tag * XScale + 2 * width / 25 + dp2px(25), YCoord(Data3[tag]) - width / 40);
                path2.lineTo(tag * XScale + 2 * width / 25 + dp2px(35), YCoord(Data3[tag]) - width / 40);
                path2.close();
                canvas.drawPath(path2, lowPaint);

                canvas.drawRect(tag * XScale + 2 * width / 25 - 2 * width / 25, YCoord(Data1[tag]) - width / 40, tag * XScale + 2 * width / 25 + dp2px(8), YCoord(Data1[tag]) + width / 50, mainPaint);
                canvas.drawText(Integer.parseInt(Data1[tag]) + "bpm", tag * XScale + 2 * width / 25 - 2 * width / 30, YCoord(Data1[tag]) + width / 50 - dp2px(7), textPaint);
                Path path3 = new Path();
                path3.moveTo(tag * XScale + 2 * width / 25 + dp2px(8), YCoord(Data1[tag]) + width / 40 - dp2px(7));
                path3.lineTo(tag * XScale + 2 * width / 25 + dp2px(8), YCoord(Data1[tag]) - width / 40 + dp2px(7));
                path3.lineTo(XPoint + 45 + tag * XScale, YCoord(Data1[tag]));
                path3.close();
                canvas.drawPath(path3, mainPaint);

                //数据填充
                SharedPreferences preferences2 = mContext.getSharedPreferences("Appmessege", Context.MODE_PRIVATE);
                //单位判断：mmgh/kpa
                if (preferences2.getString("unit", "mmgh").equals("mmgh")) {
                    canvas.drawText(Integer.parseInt(Data2[tag]) + "mmHg",
                            tag * XScale + 2 * width / 25 + dp2px(22), YCoord(Data2[tag]) - width / 30, textPaint);
                    canvas.drawText(Integer.parseInt(Data3[tag]) + "mmHg",
                            tag * XScale + 2 * width / 25 + dp2px(22), YCoord(Data3[tag]) - width / 30, textPaint);
                } else {
                    canvas.drawText((String.valueOf(Integer.parseInt(Data2[tag]) / 7.5) + "000").substring(0, 4) + "kPa",
                            tag * XScale + 2 * width / 25 + dp2px(22), YCoord(Data2[tag]) - width / 30, textPaint);
                    canvas.drawText((String.valueOf(Integer.parseInt(Data3[tag]) / 7.5) + "000").substring(0, 4) + "kPa",
                            tag * XScale + 2 * width / 25 + dp2px(22), YCoord(Data3[tag]) - width / 30, textPaint);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取手指位置
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (x < (XPoint + XLength) && x > (XPoint + XScale)) {
                    tag = (int) ((x - XPoint) / XScale);
                    if (tag < Data1.length) {
//                        invalidate();
                    } else {
                        tag = Data1.length - 1;
                    }
                    Log.i("myPoin:", String.valueOf(tag));
                    Log.i("myPoin:Data1.length", String.valueOf(Data1.length));
                    break;
                }
        }
        return true;
    }

    //计算绘制时的Y坐标，无数据时返回-999
    private int YCoord(String y0) {
        int y;
        try {
            y = (int) (Integer.parseInt(y0));
            //超过210当210处理
            if (y > 210) {
                y = 210;
            }
            //小于30当30处理
            if (y < 30) {
                y = 30;
            }
        } catch (Exception e) {
            return -999;    //出错则返回-999
        }

        //return YPoint - y * YScale / Integer.parseInt(YLabel[1]);
        if (y > 180) {
            return (int) (YPoint - dp2px(y * Unit - 70));
        } else if (y > 150 && y <= 180) {
            return (int) (YPoint - dp2px(y * Unit - 60));
        } else if (y < 95 && y > 50) {
            return (int) (YPoint - dp2px(y * Unit - 40));
        } else if (y <= 50) {
            return (int) (YPoint - dp2px(y * Unit - 30));
        } else {
            return (int) (YPoint - dp2px(y * Unit - 50));
        }


//        return (int) (YPoint - y * Unit *1.8f);
    }

    private int dp2px(float dp) {
        return (int) (mContext.getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
