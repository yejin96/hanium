package com.example.hanium;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanium.Login.LoginActivity;


public class HomeActivity extends AppCompatActivity  {
    private static final String TAG = "HomeActivity";
    Button myBtn,mback;
    TextView Mname;
    TextView Mlogout;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_interface);

        myBtn = (Button) findViewById(R.id.my_bar);


        user=(User)getIntent().getParcelableExtra("user");
        Log.d(TAG,user.getId());




        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout mymenu=(LinearLayout)inflater.inflate(R.layout.menu_interface,null);
                mymenu.setBackgroundColor(Color.parseColor("#99000000"));
                LinearLayout.LayoutParams parm=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                addContentView(mymenu,parm);
                Mname=(TextView)findViewById(R.id.main_name);
                Mname.setText(user.getId());
                Mlogout = (TextView)findViewById(R.id.main_logout);
                Mlogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent Login_intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(Login_intent);
                    }
                });
                mback=(Button)findViewById(R.id.main_back);
                mback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        ((ViewManager)mymenu.getParent()).removeView(mymenu);
                    }
                });
            }
        });


    }


}