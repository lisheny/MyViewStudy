package com.lisheny.lenovo.myviewstudy.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 *
 * Created by LENOVO on 2016/9/18.
 */
public class TrigonView extends View {

    int widght;
    int height;

    private void init(){
        WindowManager manager = (WindowManager)getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        widght = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();
    }

    public TrigonView(Context context) {
        super(context);
        init();
    }

    public TrigonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);

        Path path = new Path();
        path.moveTo(widght/2,(height-200)/2);
        path.lineTo((widght-200)/2,(height+200)/2);
        path.lineTo((widght+200)/2,(height+200)/2);
        path.close();
        canvas.drawPath(path,paint);
    }
}
