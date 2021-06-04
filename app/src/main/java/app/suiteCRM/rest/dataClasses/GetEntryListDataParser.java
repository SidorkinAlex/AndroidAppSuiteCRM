package app.suiteCRM.rest.dataClasses;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.suiteCRM.rest.ModuleMenu;
import app.suiteCRM.settings.ModuleList;

public class GetEntryListDataParser {
    public ModuleList parseEntryList(String queryResult) throws JSONException {
        JSONObject jsonObject = new JSONObject(queryResult);
        JSONArray entryList = jsonObject.getJSONArray("entry_list");
        ModuleList moduleMenuCollections = new ModuleList();
        for (int i=0; i < entryList.length();i++) {
            JSONObject oneModuleDataJSON = entryList.getJSONObject(i);
            String id = getProperty(oneModuleDataJSON,"id");
            String name = getProperty(oneModuleDataJSON,"name");
            String apiName = getProperty(oneModuleDataJSON,"name");
            ModuleMenu moduleMenu = new ModuleMenu(id,name,apiName);
            moduleMenuCollections.add(moduleMenu);
        }
        return moduleMenuCollections;
    }
    public String getProperty(JSONObject moduleData,String key) throws JSONException {
        return moduleData.getJSONObject("name_value_list").getJSONObject(key).getString("value");
    }
}
