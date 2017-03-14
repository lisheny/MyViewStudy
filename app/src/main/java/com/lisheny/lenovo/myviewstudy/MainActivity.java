package com.lisheny.lenovo.myviewstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private String[] name  = {"矩形","yuan","sanjiaoxing","sanxing","tuoyuan","quxian","otherView"};
    private ArrayAdapter<String>mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("MainActivity:","onSaveInstanceState");
    }

    private void initView(){
        mListView = (ListView)findViewById(R.id.listview);
        mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0){
                    Intent gotoRectActivity = new Intent(MainActivity.this,RectAty.class);
                    startActivity(gotoRectActivity);
                }else if (id == 1){
                    Intent gotoCorcleActivity = new Intent(MainActivity.this,CircleAty.class);
                    startActivity(gotoCorcleActivity);
                }else if (id == 2){
                    Intent gotoTrigonActivity = new Intent(MainActivity.this,TrigonAty.class);
                    startActivity(gotoTrigonActivity);
                }else if (id == 3){

                }else if (id == 4){

                }else if (id == 5){

                }else if (id == 6){
                    Intent gotoOtherView = new Intent(MainActivity.this,OtherViewAty.class);
                    startActivity(gotoOtherView);
                }
            }
        });
    }
}
