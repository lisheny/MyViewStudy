package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by LENOVO on 2016/9/18.
 */
public class CircleView extends View {

    int width;
    int height;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context) {
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
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);;
        canvas.drawCircle(width/2,height/2,width/4,paint);
    }
}
