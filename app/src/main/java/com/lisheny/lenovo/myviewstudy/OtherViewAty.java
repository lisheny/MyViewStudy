package com.lisheny.lenovo.myviewstudy;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lisheny.lenovo.myviewstudy.canvas.BarChartView;
import com.lisheny.lenovo.myviewstudy.canvas.BleResureView;
import com.lisheny.lenovo.myviewstudy.canvas.BloodPressureView;
import com.lisheny.lenovo.myviewstudy.canvas.CustomCurveChart;
import com.lisheny.lenovo.myviewstudy.canvas.PieChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * Created by LENOVO on 2016/9/30.
 */
public class OtherViewAty extends Activity {

    //XY轴的刻度文字
    public String[] XLabel = {"7-11", "7-12", "7-13", "7-14", "7-15", "7-16", "7-17"};
    public String[] YLabel = {"30", "60", "90", "120", "150", "180", "210"};
    //高压
    public String[] Data1 = {"120", "150", "125", "135", "123", "103", "152","144","123", "133", "154", "110", "123", "103", "152","144","154", "110", "123", "103", "152","144", };
    //低压
    public String[] Data2 = {"210", "215", "240", "195", "115", "162", "180","172","153", "146", "180", "143", "159", "162", "180","172","180", "143", "159", "162", "180","172"};
    //脉率
    public String[] Data3 = {"30", "60", "35", "45", "85", "68", "88","70","62", "59", "83", "76", "90", "68", "88","70", "83", "76", "90", "68", "88","70"};

//    //高压
//    public List<String> Data1;
//    //低压
//    public List<String> Data2;
//    //脉率
//    public List<String> Data3;

    private BloodPressureView bloodPressureView;

    private int i = 1;
    private TextView test;
    private Timer mTimer;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                bloodPressureView.setStartAngle(i);
                bloodPressureView.setInfo("50%", String.valueOf((int) i/100));
            }
        }

        ;
    };

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            i = i + 1;
            handler.sendEmptyMessage(1);
        }
    };

    private LinearLayout customCurveChart ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_view);

        customCurveChart = (LinearLayout) findViewById(R.id.customCurveChart);
        initCurveChart();

        bloodPressureView = (BloodPressureView) findViewById(R.id.bloodpresureview);
        bloodPressureView.setStartAngle(10);
        bloodPressureView.setOnStopPressureListener(new BloodPressureView.OnStopPressureListener() {
            @Override
            public void onStop() {
                Toast.makeText(OtherViewAty.this,"点击了停止测量",Toast.LENGTH_SHORT).show();
            }
        });
        mTimer = new Timer();
        mTimer.schedule(mTimerTask,10,10);

        test = (TextView) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow();
            }
        });

        PieChartView pieChartView = (PieChartView) findViewById(R.id.pie_chart);
        List<PieChartView.PieceDataHolder> pieceDataHolders = new ArrayList<>();
        pieceDataHolders.add(new PieChartView.PieceDataHolder(100, 0xFF77CCAA, "今天，１"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(1000, 0xFF11AA33, "明天，２"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(1200, Color.GRAY, "后天，３"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(5000, Color.YELLOW, "呵呵，４"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(10000, Color.RED, "小京，５"));
        pieceDataHolders.add(new PieChartView.PieceDataHolder(13000, Color.BLUE, "花花，６"));
        pieChartView.setData(pieceDataHolders);

        BleResureView bleResureView = (BleResureView) findViewById(R.id.my_bleresureview);
        bleResureView.setInfo(180, 92, 62, false);

        BarChartView barChartView = (BarChartView)findViewById(R.id.barcharview);
        barChartView.setInfo(45,25,55,78,103,12);
    }

    /**
     * 初始化曲线图数据
     */
    private void initCurveChart() {
        String[] xLabel = {"2017-03-01", "2017-03-02", "2017-03-03", "2017-03-04", "2017-03-05", "2017-03-06", "2017-03-07", "2017-03-08", "2017-03-09", "2017-03-10", "2017-03-11", "2017-03-12","2017-03-01", "2017-03-02", "2017-03-03", "2017-03-04", "2017-03-05", "2017-03-06", "2017-03-07", "2017-03-08", "2017-03-01", "2017-03-10"};
        String[] DataTime = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12","1", "2", "3", "4", "5", "6", "7", "8", " ", "10"};
        String[] yLabel = {"30", "60", "90", "120", "150", "180", "210","240","270"};
        int[] data1 =  {120, 150, 125, 135, 123, 103, 152,144,123, 133, 154, 110, 123, 103, 152,144,154, 110, 123, 103, 152,144, };
        int[] data2 = {220, 215, 240, 195, 115, 162, 180,172,153, 146, 180, 143, 159, 162, 180,172,180, 143, 159, 162, 180,172};
        int[] data3 = {30, 60, 35, 45, 85, 68, 88,70,62, 59, 83, 76, 90, 68, 88,70, 83, 76, 90, 68, 88,70};
        List<int[]> data = new ArrayList<>();
        List<Integer> color = new ArrayList<>();
        data.add(data1);
        color.add(R.color.color14);
        data.add(data2);
        color.add(R.color.color13);
        data.add(data3);
        color.add(R.color.color25);
        customCurveChart.addView(new CustomCurveChart(this, xLabel, yLabel,DataTime, data, color, true));
    }

    private void popWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);             //获取一个填充器
        View view = inflater.inflate(R.layout.popupwindow_test, null);   //填充我们自定义的布局

        Display display = getWindowManager().getDefaultDisplay();           //得到当前屏幕的显示器对象
        Point size = new Point();                                           //创建一个Point点对象用来接收屏幕尺寸信息
        display.getSize(size);                                               //Point点对象接收当前设备屏幕尺寸信息
        int width = size.x;                                                  //从Point点对象中获取屏幕的宽度(单位像素)
        int height = size.y;                                                 //从Point点对象中获取屏幕的高度(单位像素)

        //创建一个PopupWindow对象，第二个参数是设置宽度的
        //用刚刚获取到的屏幕宽度乘以1/3，取该屏幕的2/3宽度
        //从而在任何设备中都可以适配，高度则包裹内容即可
        //最后一个参数是设置得到焦点
        final PopupWindow popWindow = new PopupWindow(view, (int) (1.2 * width / 3),
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());              //设置PopupWindow的背景为一个空的Drawable对象，如果不设置这个，那么PopupWindow弹出后就无法退出了
        popWindow.setOutsideTouchable(true);                                //设置是否点击PopupWindow外退出PopupWindow

        WindowManager.LayoutParams params = getWindow().getAttributes(); //创建当前界面的一个参数对象
        params.alpha = 0.8f;                                                //设置参数的透明度为0.8，透明度取值为0~1，1为完全不透明，0为完全透明，因为android中默认的屏幕颜色都是纯黑色的，所以如果设置为1，那么背景将都是黑色，设置为0，背景显示我们的当前界面
        getWindow().setAttributes(params);                                   //把该参数对象设置进当前界面中

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {//设置PopupWindow退出监听器
            @Override
            public void onDismiss() {                                        //如果PopupWindow消失了，即退出了，那么触发该事件，然后把当前界面的透明度设置为不透明
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1.0f;                                           //设置为不透明，即恢复原来的界面
                getWindow().setAttributes(params);
            }
        });

        popWindow.showAsDropDown(test);                                //设置弹窗再 test控件 的左下方

        TextView textView = (TextView) view.findViewById(R.id.button1);           //popupwindow内的控件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("666", "点击了按钮1");
                test.setText("666");
                popWindow.dismiss();
            }
        });
    }
}
