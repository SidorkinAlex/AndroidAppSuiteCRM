package com.example.suitecrmapp.rest;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestSuite {
    String url;
    String method;
    final String TYPE_SEND = "JSON";
    final String APP_NAME = "AndroidPhoneApp";
    String restData;

    public RestSuite(String url, String method, String restData) {
        this.url = url;
        this.method = method;
        this.restData = restData;
    }

    public String sendRequest(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("method",method)
                .addFormDataPart("input_type",TYPE_SEND)
                .addFormDataPart("response_type",TYPE_SEND)
                .addFormDataPart("rest_data",restData)
                .build();
        Request request = new Request.Builder()
                .url(url + "service/v4_1/rest.php")
                .method("POST", body)
                .build();
        String result="";
        try {
            Response response = client.newCall(request).execute();
            result = response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
            result ="";
        }

        return result;
    }

}
