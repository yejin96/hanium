package com.example.hanium;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {
    CalendarView cal =(CalendarView)findViewById(R.id.Calendar);
    TextView sc1=(TextView)findViewById(R.id.script1);
    TextView sc2=(TextView)findViewById(R.id.script2);
    TextView dia=(TextView)findViewById(R.id.dialog);
    Button myBtn;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"홈화면");
        setContentView(R.layout.home_interface);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                sc1.setText(year + "/" + (month + 1) + "/" + date);
                sc2.setText(year + "/" + (month + 1) + "/" + date);
                dia.setText(year + "/" + (month + 1) + "/" + date); }
            });
        myBtn = (Button) findViewById(R.id.my_bar);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout mymenu=(LinearLayout)inflater.inflate(R.layout.menu_interface,null);
                mymenu.setBackgroundColor(Color.parseColor("#99000000"));
                LinearLayout.LayoutParams parm=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT);
                addContentView(mymenu,parm);
            }
        });


    }


}