package com.example.suitecrmapp.rest;

import com.google.gson.Gson;

import org.json.JSONObject;

public class ApiSuiteCRM {
    RestDataLolin restDataLolin;
    boolean badData = false;
    public String url;

    public ApiSuiteCRM(String login, String pass, String url) {
        if(login.equals("") || pass.equals("") || url.equals("")) {
            badData = true;
        }
        restDataLolin = new RestDataLolin(login, pass);
        this.url = url;

    }

    public String authorization(){
        if(badData) {
            return "";
        }
        Gson gson = new Gson();
        String json = gson.toJson(restDataLolin);
        RestSuite restSuite = new RestSuite(url,"login",json);
        String result = restSuite.sendRequest();
        return result;
    }
}
