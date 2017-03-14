package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * 饼状图
 * Created by LENOVO on 2016/9/30.
 */
public class SectorView extends View {


    //屏幕宽高
    int width;
    int height;
    //偏低
    public float flat = (float) 0.05;
    //正常
    public float normal = (float) 0.60;
    //轻度
    public float mild = (float) 0.20;
    //中度
    public float Medium = (float) 0.10;
    //重度
    public float serious = (float) 0.05;

    public  void  setInfo(float inputflat,float inputnormal,float inputmild,float inputmedium,float inputserious){
        flat = inputflat;
        normal = inputnormal;
        mild = inputmild;
        Medium = inputmedium;
        serious = inputserious;
    }

    public SectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SectorView(Context context) {
        super(context);
        init();
    }

    public void init(){
        WindowManager manager = (WindowManager)getContext().getSystemService(
                Context.WINDOW_SERVICE
        );
        width = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        Paint flatPaint = new Paint();
        flatPaint.setAntiAlias(true);
        flatPaint.setColor(Color.RED);

        Paint normalPaint = new Paint();
        normalPaint.setAntiAlias(true);
        normalPaint.setColor(Color.BLUE);

        Paint mildPaint = new Paint();
        mildPaint.setAntiAlias(true);
        mildPaint.setColor(Color.GRAY);

        Paint MediumPaint = new Paint();
        MediumPaint.setAntiAlias(true);
        MediumPaint.setColor(Color.BLACK);

        Paint seriousPaint  = new Paint();
        seriousPaint.setAntiAlias(true);
        seriousPaint.setColor(Color.CYAN);

//        canvas.drawRect(width/4,width/8,width/4 * 3,width/8 * 5,paint);
        RectF rectF = new RectF(width/4,width/8,width/4 * 3,width/8 * 5);
//        canvas.drawArc(rectF, 290,  flat*360, true, flatPaint);
        canvas.drawArc(rectF, 30,  normal*360, true, normalPaint);
        canvas.drawArc(rectF, 30+normal*360,flat*360, true, flatPaint);
        canvas.drawArc(rectF, 30+normal*360+flat*360,serious*360, true, seriousPaint);
        canvas.drawArc(rectF, 30+normal*360+flat*360+serious*360,Medium*360, true, MediumPaint);
        canvas.drawArc(rectF, 30+normal*360+flat*360+serious*360+Medium*360,mild*360, true, mildPaint);
        canvas.drawCircle(width/2,width/8*3,width/10,paint);
    }
}
