package com.example.hanium.Login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hanium.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Button okBtn;
    Button cancelBtn;

    EditText idEt, pwEt, nameEt, phnumEt, emailEt;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    Map<String,String> user=new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_interface);

        idEt = (EditText) findViewById(R.id.signup_id_Et);
        pwEt = (EditText) findViewById(R.id.signup_pw_Et);
        nameEt = (EditText) findViewById(R.id.signup_name_Et);
        phnumEt = (EditText) findViewById(R.id.signup_phnum_Et);
        emailEt = (EditText) findViewById(R.id.signup_email_Et);

        okBtn = (Button) findViewById(R.id.signup_ok_Btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.put("PW",pwEt.getText().toString());
                user.put("NAME",nameEt.getText().toString());
                user.put("Phone",phnumEt.getText().toString());
                user.put("Email",emailEt.getText().toString());
                db.collection("User").document(idEt.getText().toString())
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                new AlertDialog.Builder(SignUpActivity.this)
                                        .setTitle("알림")
                                        .setMessage("회원가입 성공")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                finish();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        cancelBtn = (Button) findViewById(R.id.signUp_cancel_Btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
