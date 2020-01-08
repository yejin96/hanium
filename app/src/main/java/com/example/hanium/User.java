package com.example.hanium;

import com.example.hanium.Login.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class User implements Serializable {
    String id;
    String name;
    String cellphone;
    String mail;


    public User(String id) {
        this.id = id;

        try {
            JSONObject getuser = new JSONObject(new DBHelper().execute("getUser", "&id=" + id).get());

            String name = getuser.getString("name");
            String cellphone = getuser.getString("cellphone");
            String mail = getuser.getString("mail");

            this.name = name;
            this.cellphone = cellphone;
            this.mail = mail;

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
