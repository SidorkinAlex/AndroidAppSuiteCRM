package app.suiteCRM.rest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ModuleMenu {
    public String id;
    public String name;
    public String apiName;

    public ModuleMenu(String id, String name, String apiName) {
        this.id = id;
        this.name = name;
        this.apiName = apiName;
    }
    public ModuleMenu(JSONObject json){
        try {
            id = json.getString("id");
            name = json.getString("name");
            apiName = json.getString("apiName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
