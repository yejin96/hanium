package com.example.hanium.STTS;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetSentenceTask extends AsyncTask<Void, Void, String> {

    private final String TAG = "[MyLog]";

    private Context context;
    private String url;

    public GetSentenceTask(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: ");
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL url = new URL(GetSentenceTask.this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestMethod("GET");         // 통신방식
            connection.setDoInput(true);                // 읽기모드 지정
            connection.setUseCaches(false);             // 캐싱데이터를 받을지 안받을지
            connection.setConnectTimeout(15000);        // 통신 타임아웃

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }

        } catch (ConnectException e) {
            Log.e(TAG, "ConnectException");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: " + s);
        super.onPostExecute(s);
    }
}
