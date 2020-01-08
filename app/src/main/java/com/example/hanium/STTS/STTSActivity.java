package com.example.hanium.STTS;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hanium.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class STTSActivity extends AppCompatActivity {
    /**
     * Made By LSC
     */
    private final String TAG = "[MyLog]";
    Button ttspeech, repeat,stt;
    TextView txtRead;
    TextView txtSpeech;
    private TextToSpeech tts;
    private boolean isAvailableToTTS = false;
    private ArrayList<Sentence> sentences;
    private int nowIndex = 0;
    private Intent STTservice;
    private MyBroadcastReceiver myBroadCastReceiver;
    /**
     * FiNISH
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stts_interface);

        TedPermission.with(this)
                .setPermissions(Manifest.permission.RECORD_AUDIO)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Log.d(TAG, "onPermissionGranted: ");
                        initView();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        for (int i = 0; i < deniedPermissions.size(); i++) {
                            Log.d(TAG, "onPermissionDenied: " + deniedPermissions.get(i));
                        }
                        Toast.makeText(STTSActivity.this, "권한을 허락해주셔야 합니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .check();
    }
    public void initView(){
        stt = findViewById(R.id.stt);
        stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myBroadCastReceiver = new MyBroadcastReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("get_stt_result");
                registerReceiver(myBroadCastReceiver, intentFilter);
                STTservice = new Intent(getApplicationContext(), STTservice.class);
//                nStart =  System.currentTimeMillis();
                startService(STTservice);
            }
        });
        txtRead = (TextView) findViewById(R.id.txtRead);
        txtSpeech=(TextView) findViewById(R.id.sttext);
        /**
         * Made By LSC
         */
        sentences = new ArrayList<>();
        try {
            String result = new GetSentenceTask(this, "http://165.229.250.25:8080/haneum/index.php").execute().get();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    sentences.add(new Sentence(jsonArray.getJSONObject(i).getString("eng"), jsonArray.getJSONObject(i).getString("kor")));
                    //Log.d(TAG, "onPostExecute: " + jsonArray.getJSONObject(i).getString("sen"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * FiNISH
         */

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int isAvailable) {
                if (isAvailable == TextToSpeech.SUCCESS) {
                    int language = tts.setLanguage(Locale.ENGLISH);
                    if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(STTSActivity.this, "지원되지 않는 언어입니다.", Toast.LENGTH_SHORT).show();
                        isAvailableToTTS = false;
                    } else {
                        isAvailableToTTS = true;
                    }
                }
            }
        });


        ttspeech = (Button) findViewById(R.id.ttspeech);

        ttspeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speech();
            }
        });
        repeat = findViewById(R.id.repeat);
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nowIndex != 0) {
                    nowIndex--;
                }
                speech();
            }
        });


    }

    public void speech() {
        if (isAvailableToTTS) {
            String text = sentences.get(nowIndex).getEng();
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            txtRead.setText(sentences.get(nowIndex).getEng());
            nowIndex++;
        }

    }

    @Override
    protected void onDestroy() {
        if(STTservice != null) {
            stopService(STTservice);
        }
        super.onDestroy();
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("get_stt_result")) {
                ArrayList<String> results = intent.getStringArrayListExtra("result");
                txtSpeech.setText(results.toString());
                unregisterReceiver(myBroadCastReceiver);
                if(STTservice != null) {
                    stopService(STTservice);
                }
            }
        }
    }


}
