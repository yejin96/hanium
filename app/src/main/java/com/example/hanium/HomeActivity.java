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
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanium.Login.LoginActivity;
import com.example.hanium.STTS.STTSActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeActivity extends AppCompatActivity  {
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private static final String TAG = "HomeActivity";
    Button myBtn,mback;
    TextView Mname;
    TextView Mlogout;
    TextView s1,s2,d;
    CalendarView cal;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_interface);

        myBtn = (Button) findViewById(R.id.my_bar);
        s1=(TextView)findViewById(R.id.script1);
        s2=(TextView)findViewById(R.id.script2);
        d=(TextView)findViewById(R.id.dialog);

        user=(User)getIntent().getParcelableExtra("user");
        Log.d(TAG,user.getId());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy년MM월dd일");
        final String today=sdf.format(new Date());
        db.collection("sentence").document(today)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                s1.setText(document.get("smain1kor").toString());
                                s2.setText(document.get("smain2kor").toString());
                                d.setText(document.get("dmainkor").toString());

                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                s1.setText("No Script #1");
                                s2.setText("No Script #2");
                                d.setText("No Dialog");
                                Toast.makeText(HomeActivity.this, today+"\n공부할 내용이 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        cal=(CalendarView)findViewById(R.id.Calendar);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                NumberFormat nf=NumberFormat.getIntegerInstance();
                nf.setMinimumIntegerDigits(2);
                final String date=year+"년"+nf.format(month+1)+"월"+nf.format(dayOfMonth)+"일";
                Log.d(TAG,date);
                db.collection("sentence").document(date)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        s1.setText(document.get("smain1kor").toString());
                                        s2.setText(document.get("smain2kor").toString());
                                        d.setText(document.get("dmainkor").toString());
                                        s1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent STTS_intent = new Intent(HomeActivity.this, STTSActivity.class);
                                                STTS_intent.putExtra("date",date);
                                                startActivity(STTS_intent);
                                            }
                                        });
                                    } else {
                                        s1.setText("No Script #1");
                                        s2.setText("No Script #2");
                                        d.setText("No Dialog");
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                        Toast.makeText(HomeActivity.this, date+"\n공부할 내용이 없습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });


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

