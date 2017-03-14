package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.lisheny.lenovo.myviewstudy.R;

import java.util.List;

/**
 * 曲线折线图绘制
 * Created by liuwan on 2016/9/26.
 */
public class CustomCurveChart extends View {

    private Context mContext;
    //判断显示哪个提示信息的标签
    private int tag = 1;
    // 坐标单位
    private String[] xLabel;
    private String[] yLabel;
    //数据的时间
    private String[] DataTime;
    // 曲线数据
    private List<int[]> dataList;
    private List<Integer> colorList;
    private boolean showValue;
    // 默认边距
    private float margin = 25 * 4;
    // 距离左边偏移量
    private float marginX = 30;
    //距离右边偏移量
    private float marginRitht = 10;
    // 原点坐标
    private float xPoint;
    private float yPoint;
    // X,Y轴的单位长度
    private float xScale;
    private float yScale;
    // 画笔
    private Paint paintAxes;
    private Paint paintCoordinate;
    private Paint paintTable;
    private Paint paintCurve;
    private Paint paintRectF;
    private Paint paintValue;
    private Paint paintValue2;
    private Paint painDashed;

    public CustomCurveChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public CustomCurveChart(Context context, String[] xLabel, String[] yLabel, String[] Datatime,
                            List<int[]> dataList, List<Integer> colorList, boolean showValue) {
        super(context);
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.DataTime = Datatime;
        this.dataList = dataList;
        this.colorList = colorList;
        this.showValue = showValue;
        this.mContext = context;

    }

    public void setInfo(String[] xLabel, String[] yLabel, String[] Datatime,
                        List<int[]> dataList, List<Integer> colorList, boolean showValue) {
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.DataTime = Datatime;
        this.dataList = dataList;
        this.colorList = colorList;
        this.showValue = showValue;

    }


    public CustomCurveChart(Context context) {
        super(context);
        this.mContext = context;

    }

    /**
     * 初始化数据值和画笔
     */
    public void init() {
        // 默认边距
        margin = dp2px(32);
        // 距离左边偏移量
        marginX = dp2px(8);
        //距离右边偏移量
        marginRitht = dp2px(10);
        xPoint = margin + marginX;
        yPoint = this.getHeight() - margin;
        try {
            xScale = (float) ((this.getWidth() - 2 * xPoint - marginRitht) / (xLabel.length - 1));
//            xScale = (this.getWidth() - 2 * margin - marginX - marginRitht) / (xLabel.length - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        yScale = (this.getHeight() - 2 * margin) / (yLabel.length - 1);

        paintAxes = new Paint();
        paintAxes.setStyle(Paint.Style.STROKE);
        paintAxes.setAntiAlias(true);
        paintAxes.setDither(true);
        paintAxes.setColor(ContextCompat.getColor(getContext(), R.color.black));
        paintAxes.setStrokeWidth(3);

        paintCoordinate = new Paint();
        paintCoordinate.setStyle(Paint.Style.STROKE);
        paintCoordinate.setDither(true);
        paintCoordinate.setAntiAlias(true);
        paintCoordinate.setColor(ContextCompat.getColor(getContext(), R.color.black));
        paintCoordinate.setTextSize(dp2px(10));
        paintCoordinate.setTextAlign(Paint.Align.CENTER);

        paintTable = new Paint();
        paintTable.setStyle(Paint.Style.STROKE);
        paintTable.setAntiAlias(true);
        paintTable.setDither(true);
        paintTable.setColor(ContextCompat.getColor(getContext(), R.color.color4));
        paintTable.setStrokeWidth(2);

        paintCurve = new Paint();
        paintCurve.setStyle(Paint.Style.STROKE);
        paintCurve.setDither(true);
        paintCurve.setAntiAlias(true);
        paintCurve.setStrokeWidth((float) 1.5);
//        曲线
//        PathEffect pathEffect = new CornerPathEffect(50);
//        paintCurve.setPathEffect(pathEffect);

        paintRectF = new Paint();
        paintRectF.setStyle(Paint.Style.FILL);
        paintRectF.setDither(true);
        paintRectF.setAntiAlias(true);
        paintRectF.setStrokeWidth(3);

        paintValue = new Paint();
        paintValue.setStyle(Paint.Style.STROKE);
        paintValue.setAntiAlias(true);
        paintValue.setDither(true);
        paintValue.setColor(ContextCompat.getColor(getContext(), R.color.color1));
        paintValue.setTextAlign(Paint.Align.CENTER);
        paintValue.setTextSize(dp2px(8));

        paintValue2 = new Paint();
        paintValue2.setStyle(Paint.Style.STROKE);
        paintValue2.setAntiAlias(true);
        paintValue2.setDither(true);
        paintValue2.setColor(ContextCompat.getColor(getContext(), R.color.black));
        paintValue2.setTextAlign(Paint.Align.CENTER);
        paintValue2.setTextSize(dp2px(10));

        painDashed = new Paint();
        painDashed.setStyle(Paint.Style.STROKE);
        painDashed.setColor(ContextCompat.getColor(getContext(), R.color.black));
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        painDashed.setPathEffect(effects);
        painDashed.setStrokeWidth(3);

    }

    private int dp2px(float dp) {
        return (int) (mContext.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        invalidate();
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.color1));
        init();
//        drawTable(canvas, paintTable);
        drawAxesLine(canvas, paintAxes);
        drawCoordinate(canvas, paintCoordinate);
        for (int i = 0; i < dataList.size(); i++) {
            drawCurve(canvas, paintCurve, dataList.get(i), colorList.get(i));
        }
        for (int i = 0; i < dataList.size(); i++) {
            if (showValue) {
                if (i % 2 == 0) {
                    drawValue2(canvas, paintRectF, dataList.get(i), colorList.get(i));
                } else {
                    drawValue(canvas, paintRectF, dataList.get(i), colorList.get(i));
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取手指位置
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //让父控件分发事件给子view
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("myPoin:", String.valueOf(tag));
                if (x > xPoint && x < (xPoint + xScale * dataList.get(0).length)) {
                    tag = (int) ((x - xPoint) / xScale);
                    if (tag < dataList.get(0).length) {
//                        invalidate();
                    } else {
                        tag = dataList.get(0).length - 1;
//                        invalidate();
                    }
                    Log.i("myPoin:", String.valueOf(tag));
                    Log.i("myPoin:Data1.length", String.valueOf(dataList.get(0).length));
                } else {
                    //让父控件拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return true;
    }


    /**
     * 绘制坐标轴
     */
    private void drawAxesLine(Canvas canvas, Paint paint) {
        // X
        canvas.drawLine(xPoint, yPoint, this.getWidth() - margin / 6, yPoint, paint);
        //绘制箭头
//        canvas.drawLine(this.getWidth() - margin / 6, yPoint, this.getWidth() - margin / 2, yPoint - margin / 3, paint);
//        canvas.drawLine(this.getWidth() - margin / 6, yPoint, this.getWidth() - margin / 2, yPoint + margin / 3, paint);

        // Y
        canvas.drawLine(xPoint, yPoint, xPoint, margin / 6, paint);
        //绘制箭头
//        canvas.drawLine(xPoint, margin / 6, xPoint - margin / 3, margin / 2, paint);
//        canvas.drawLine(xPoint, margin / 6, xPoint + margin / 3, margin / 2, paint);
    }

    /**
     * 绘制表格
     */
    private void drawTable(Canvas canvas, Paint paint) {
        Path path = new Path();
        // 横向线
        for (int i = 1; (yPoint - i * yScale) >= margin; i++) {
            float startX = xPoint;
            float startY = yPoint - i * yScale;
            float stopX = xPoint + (xLabel.length - 1) * xScale;
            path.moveTo(startX, startY);
            path.lineTo(stopX, startY);
            canvas.drawPath(path, paint);
        }

        // 纵向线
        for (int i = 1; i * xScale <= (this.getWidth() - margin); i++) {
            float startX = xPoint + i * xScale;
            float startY = yPoint;
            float stopY = yPoint - (yLabel.length - 1) * yScale;
            path.moveTo(startX, startY);
            path.lineTo(startX, stopY);
            canvas.drawPath(path, paint);
        }
    }

    /**
     * 绘制刻度
     */
    private void drawCoordinate(Canvas canvas, Paint paint) {
//        try {
//            // X轴坐标
//            for (int i = 0; i <= (xLabel.length - 1); i++) {
//                paint.setTextAlign(Paint.Align.CENTER);
//                float startX = xPoint + i * xScale;
//                canvas.drawText(xLabel[i], startX, this.getHeight() - margin / 2, paint);
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }

        // Y轴坐标
        for (int i = 1; i <= (yLabel.length - 1); i++) {
            paint.setTextAlign(Paint.Align.LEFT);
            float startY = yPoint - i * yScale;
            int offsetX;
            switch (yLabel[i].length()) {
                case 1:
                    offsetX = 28;
                    break;

                case 2:
                    offsetX = 20;
                    break;

                case 3:
                    offsetX = 12;
                    break;

                case 4:
                    offsetX = 5;
                    break;

                default:
                    offsetX = 0;
                    break;
            }
            float offsetY;
            if (i == 0) {
                offsetY = 0;
            } else {
                offsetY = margin / 5;
            }
            // x默认是字符串的左边在屏幕的位置，y默认是字符串是字符串的baseline在屏幕上的位置
            SharedPreferences preferences2 = mContext.getSharedPreferences("Appmessege", Context.MODE_PRIVATE);
            //单位判断：mmgh/kpa
            if (preferences2.getString("unit", "mmgh").equals("mmgh")) {
                canvas.drawText(yLabel[i], margin / 4 + offsetX, startY + offsetY, paint);
            } else {
                canvas.drawText(String.valueOf((int) (Integer.parseInt(yLabel[i]) / 7.5)),
                        margin / 4 + offsetX, startY + offsetY, paint);
            }
        }

    }

    /**
     * 绘制曲线
     */
    private void drawCurve(Canvas canvas, Paint paint, int[] data, int color) {
        paint.setColor(ContextCompat.getColor(getContext(), color));
        Path path = new Path();
        for (int i = 0; i <= (xLabel.length - 1); i++) {
            if (i == 0) {
                path.moveTo(xPoint, toY(data[0]));
            } else {
                path.lineTo(xPoint + i * xScale, toY(data[i]));
            }

            if (i == xLabel.length - 1) {
                path.lineTo(xPoint + i * xScale, toY(data[i]));
            }
        }
        canvas.drawPath(path, paint);

    }

    /**
     * 绘制数值
     * 向右
     */
    private void drawValue2(Canvas canvas, Paint paint, int data[], int color) {
        invalidate();
        paint.setColor(ContextCompat.getColor(getContext(), color));
//        for (int i = 1; i <= (xLabel.length - 1); i++) {
        if (tag < 0) {
            tag = 0;
        }
        RectF rect;
        try {
            if (toY(data[tag - 1]) < toY(data[tag])) {
                rect = new RectF((xPoint + tag * xScale + dp2px(23)) - dp2px(20), (toY(data[tag]) - dp2px(25)) - dp2px(11),
                        (xPoint + tag * xScale + dp2px(23)) + dp2px(20), (toY(data[tag]) - dp2px(25)) + dp2px(1));
                Path path = new Path();
                path.moveTo(xPoint + tag * xScale, toY(data[tag]));
                path.lineTo((xPoint + tag * xScale + dp2px(23)) - dp2px(18), (toY(data[tag]) - dp2px(25)) + dp2px(1));
                path.lineTo((xPoint + tag * xScale + dp2px(23)) - dp2px(10), (toY(data[tag]) - dp2px(25)) + dp2px(1));
                path.close();
                canvas.drawPath(path, paint);
                canvas.drawRoundRect(rect, 0, 0, paint);

                canvas.drawText(data[tag] + "mmHg",
                        (xPoint + tag * xScale + dp2px(23)),
                        (toY(data[tag]) - dp2px(25) - dp2px(2)), paintValue);

            } else if (toY(data[tag - 1]) > toY(data[tag])) {
                rect = new RectF((xPoint + tag * xScale + dp2px(23)) - dp2px(20), (toY(data[tag]) - dp2px(25)) - dp2px(1),
                        (xPoint + tag * xScale + dp2px(23)) + dp2px(20), (toY(data[tag]) - dp2px(25)) + dp2px(15));
                Path path = new Path();
                path.moveTo(xPoint + tag * xScale, toY(data[tag]));
                path.lineTo((xPoint + tag * xScale + dp2px(23)) - dp2px(18), (toY(data[tag]) - dp2px(25)) + dp2px(15));
                path.lineTo((xPoint + tag * xScale + dp2px(23)) - dp2px(10), (toY(data[tag]) - dp2px(25)) + dp2px(15));
                path.close();
                canvas.drawPath(path, paint);
                canvas.drawRoundRect(rect, 0, 0, paint);

                canvas.drawText(data[tag] + "mmHg",
                        (xPoint + tag * xScale + dp2px(23)),
                        (toY(data[tag]) - dp2px(25)) + dp2px(10), paintValue);


            } else {
                rect = new RectF((xPoint + tag * xScale + dp2px(23)) - dp2px(20), (toY(data[tag]) - dp2px(25)) - dp2px(6),
                        (xPoint + tag * xScale + dp2px(23)) + dp2px(20), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                Path path = new Path();
                path.moveTo(xPoint + tag * xScale, toY(data[tag]));
                path.lineTo((xPoint + tag * xScale + dp2px(23)) - dp2px(18), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                path.lineTo((xPoint + tag * xScale + dp2px(23)) - dp2px(10), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                path.close();
                canvas.drawPath(path, paint);
                canvas.drawRoundRect(rect, 0, 0, paint);

                canvas.drawText(data[tag] + "mmHg",
                        (xPoint + tag * xScale + dp2px(23)),
                        (toY(data[tag]) - dp2px(25)) + dp2px(2), paintValue);

            }
        } catch (Exception e) {

            try {
                rect = new RectF((xPoint + tag * xScale + dp2px(23)) - dp2px(20), (toY(data[tag]) - dp2px(25)) - dp2px(6),
                        (xPoint + tag * xScale + dp2px(23)) + dp2px(20), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                Path path = new Path();
                path.moveTo(xPoint + tag * xScale, toY(data[tag]));
                path.lineTo((xPoint + tag * xScale + dp2px(23)) - dp2px(18), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                path.lineTo((xPoint + tag * xScale + dp2px(23)) - dp2px(10), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                path.close();
                canvas.drawPath(path, paint);
                canvas.drawRoundRect(rect, 0, 0, paint);

                canvas.drawText(data[tag] + "mmHg",
                        (xPoint + tag * xScale + dp2px(23)),
                        (toY(data[tag]) - dp2px(25)) + dp2px(2), paintValue);

            } catch (Exception ignored) {

            }
        }
        try {
            //画虚线、时间展示
            Path path = new Path();
            path.moveTo(xPoint + tag * xScale, yPoint);
            path.lineTo(xPoint + tag * xScale, yPoint - yScale * (yLabel.length - 1));
            canvas.drawPath(path, painDashed);
            canvas.drawText(DataTime[tag], xPoint + tag * xScale, yPoint + dp2px(12), paintValue2);
            canvas.drawText(xLabel[tag], xPoint + tag * xScale, yPoint + dp2px(22), paintValue2);

            //画小圆点
            canvas.drawCircle(xPoint + tag * xScale, toY(data[tag]), dp2px(3), paint);
            canvas.drawCircle(xPoint + tag * xScale, toY(data[tag]), dp2px(2), paintValue);
        } catch (Exception e) {
//            Logger.e(e);
        }

//        canvas.drawLine(xPoint + tag * xScale,yPoint,xPoint + tag * xScale,yPoint-yScale* (yLabel.length - 1),painDashed);
//        }
    }

    /**
     * 绘制数值
     * 向左
     */
    private void drawValue(Canvas canvas, Paint paint, int data[], int color) {
        invalidate();

        paint.setColor(ContextCompat.getColor(getContext(), color));
//        for (int i = 1; i <= (xLabel.length - 1); i++) {
        if (tag < 0) {
            tag = 0;
        }
        RectF rect;
        try {
            if (toY(data[tag - 1]) < toY(data[tag])) {
                rect = new RectF((xPoint + tag * xScale - dp2px(23)) - dp2px(20), (toY(data[tag]) - dp2px(25)) - dp2px(11),
                        (xPoint + tag * xScale - dp2px(23)) + dp2px(20), (toY(data[tag]) - dp2px(25)) + dp2px(1));
                Path path = new Path();
                path.moveTo(xPoint + tag * xScale, toY(data[tag]));
                path.lineTo((xPoint + tag * xScale - dp2px(23)) + dp2px(18), (toY(data[tag]) - dp2px(25)) + dp2px(1));
                path.lineTo((xPoint + tag * xScale - dp2px(23)) + dp2px(10), (toY(data[tag]) - dp2px(25)) + dp2px(1));
                path.close();
                canvas.drawPath(path, paint);
                canvas.drawRoundRect(rect, 0, 0, paint);
                canvas.drawText(data[tag] + "bpm", (xPoint + tag * xScale - dp2px(23)), (toY(data[tag]) - dp2px(25) - dp2px(2)), paintValue);
            } else if (toY(data[tag - 1]) > toY(data[tag])) {
                rect = new RectF((xPoint + tag * xScale - dp2px(23)) - dp2px(20), (toY(data[tag]) - dp2px(25)) - dp2px(1),
                        (xPoint + tag * xScale - dp2px(23)) + dp2px(20), (toY(data[tag]) - dp2px(25)) + dp2px(15));
                Path path = new Path();
                path.moveTo(xPoint + tag * xScale, toY(data[tag]));
                path.lineTo((xPoint + tag * xScale - dp2px(23)) + dp2px(18), (toY(data[tag]) - dp2px(25)) + dp2px(15));
                path.lineTo((xPoint + tag * xScale - dp2px(23)) + dp2px(10), (toY(data[tag]) - dp2px(25)) + dp2px(15));
                path.close();
                canvas.drawPath(path, paint);
                canvas.drawRoundRect(rect, 0, 0, paint);
                canvas.drawText(data[tag] + "bpm", (xPoint + tag * xScale - dp2px(23)), (toY(data[tag]) - dp2px(25)) + dp2px(10), paintValue);
            } else {
                rect = new RectF((xPoint + tag * xScale - dp2px(23)) - dp2px(20), (toY(data[tag]) - dp2px(25)) - dp2px(6),
                        (xPoint + tag * xScale - dp2px(23)) + dp2px(20), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                Path path = new Path();
                path.moveTo(xPoint + tag * xScale, toY(data[tag]));
                path.lineTo((xPoint + tag * xScale - dp2px(23)) + dp2px(18), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                path.lineTo((xPoint + tag * xScale - dp2px(23)) + dp2px(10), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                path.close();
                canvas.drawPath(path, paint);
                canvas.drawRoundRect(rect, 0, 0, paint);
                canvas.drawText(data[tag] + "bpm", (xPoint + tag * xScale - dp2px(23)), (toY(data[tag]) - dp2px(25)) + dp2px(2), paintValue);
            }
        } catch (Exception e) {
            try {
                rect = new RectF((xPoint + tag * xScale - dp2px(23)) - dp2px(20), (toY(data[tag]) - dp2px(25)) - dp2px(6),
                        (xPoint + tag * xScale - dp2px(23)) + dp2px(20), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                Path path = new Path();
                path.moveTo(xPoint + tag * xScale, toY(data[tag]));
                path.lineTo((xPoint + tag * xScale - dp2px(23)) + dp2px(18), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                path.lineTo((xPoint + tag * xScale - dp2px(23)) + dp2px(10), (toY(data[tag]) - dp2px(25)) + dp2px(6));
                path.close();
                canvas.drawPath(path, paint);
                canvas.drawRoundRect(rect, 0, 0, paint);
                canvas.drawText(data[tag] + "bpm", (xPoint + tag * xScale - dp2px(23)), (toY(data[tag]) - dp2px(25)) + dp2px(2), paintValue);
            } catch (Exception ignored) {

            }
        }
        try {
            //画虚线、时间展示
            Path path = new Path();
            path.moveTo(xPoint + tag * xScale, yPoint);
            path.lineTo(xPoint + tag * xScale, yPoint - yScale * (yLabel.length - 1));
            canvas.drawPath(path, painDashed);
            canvas.drawText(DataTime[tag], xPoint + tag * xScale, yPoint + dp2px(12), paintValue2);
            canvas.drawText(xLabel[tag], xPoint + tag * xScale, yPoint + dp2px(22), paintValue2);

            //画小圆点
            canvas.drawCircle(xPoint + tag * xScale, toY(data[tag]), dp2px(3), paint);
            canvas.drawCircle(xPoint + tag * xScale, toY(data[tag]), dp2px(2), paintValue);
        } catch (Exception ignored) {

        }

//        canvas.drawLine(xPoint + tag * xScale,yPoint,xPoint + tag * xScale,yPoint-yScale* (yLabel.length - 1),painDashed);
//        }
    }

    /**
     * 数据按比例转坐标
     */
    private float toY(int num) {
        float y = 0;
        //小于30当30处理
        if (num < 33) {
            num = 33;
        }
        try {

            if (yLabel.length == 7) {
                float a = (float) num / 29.0f;
                y = yPoint - a * yScale;
            } else if (yLabel.length == 8) {
                if (num < 40) {
                    num = 40;
                }
                float a = (float) num / 29.0f;
                y = yPoint - a * yScale;
            } else if (yLabel.length == 9) {
                if (num < 42) {
                    num = 42;
                }
                float a = (float) num / 28.0f;
                y = yPoint - a * yScale;
            } else {
                float a = (float) num / 32.0f;
                y = yPoint - a * yScale;
            }
        } catch (Exception e) {
            return 0;
        }
        return y + dp2px(25);
    }

}
