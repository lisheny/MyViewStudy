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
public class RectView extends View {

    int width;
    int height;

    private  void init(){
        WindowManager maneget = (WindowManager)getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        width = maneget.getDefaultDisplay().getWidth();
        height = maneget.getDefaultDisplay().getHeight();
    }

    public RectView(Context context) {
        super(context);
        init();
    }

    public RectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        canvas.drawRect(200,200,width-200,width-200,paint);
    }
}
