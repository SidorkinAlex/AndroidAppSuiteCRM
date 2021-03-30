package com.example.suitecrmapp.rest;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiSuiteCRM {
    RestDataLolin restDataLolin;
    boolean badData = false;
    public String url;
    public SharedPreferences sharedPreferences;

    public ApiSuiteCRM(String login, String pass, String url, SharedPreferences sharedPreferences) {
        if(login.equals("") || pass.equals("") || url.equals("")) {
            badData = true;
        }
        restDataLolin = new RestDataLolin(login, pass);
        this.url = url;
        this.sharedPreferences = sharedPreferences;

    }
    public void save_session(String session){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session", session);
    }

    public String authorization(){
        if(badData) {
            return "";
        }
        Gson gson = new Gson();
        String json = gson.toJson(restDataLolin);
        RestSuite restSuite = new RestSuite(url,"login",json);
        String result = restSuite.sendRequest();
        save_session(result);
        //JSONPasse
        String cacheUser;
        try {
            JSONObject resultJson = new JSONObject(result);
            cacheUser = resultJson.getString("id");
        } catch (JSONException e) {
            JSONObject resultJson = null;
            try {
                resultJson = new JSONObject(result);
                cacheUser = resultJson.getString("name");
            } catch (JSONException jsonException) {
                cacheUser = "error";
            }
        }
        return cacheUser;
    }
}
