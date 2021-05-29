package app.suiteCRM.rest.dataClasses;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import app.suiteCRM.settings.PreferenceConstant;

public class GetEntryListDataBuilder {
    public JSONObject json;
    public String moduleName;
    public String session;
    public String where;
    public String orderBy;
    public Integer offset;
    public byte deleted;

    public GetEntryListDataBuilder(SharedPreferences sharedPreferences, String moduleName) {
        session = sharedPreferences.getString(PreferenceConstant.SESSION,"");
        this.moduleName = moduleName;
    }
    public GetEntryListDataBuilder(SharedPreferences sharedPreferences, String moduleName, String where,String orderBy) {
        session = sharedPreferences.getString(PreferenceConstant.SESSION,"");
        this.moduleName = moduleName;
        this.orderBy = orderBy;
        this.where = where;
    }
    public GetEntryListDataBuilder(SharedPreferences sharedPreferences, String moduleName, String where,String orderBy,Integer offset){
        session = sharedPreferences.getString(PreferenceConstant.SESSION,"");
        this.moduleName = moduleName;
        this.orderBy = orderBy;
        this.where = where;
        this.offset = offset;
    }
    public GetEntryListDataBuilder(SharedPreferences sharedPreferences, String moduleName, String where,String orderBy,Integer offset, byte deleted) {
        session = sharedPreferences.getString(PreferenceConstant.SESSION,"");
        this.moduleName = moduleName;
        this.orderBy = orderBy;
        this.where = where;
        this.offset = offset;
        if(deleted == 1){
            this.deleted = 1;
        } else {
            this.deleted = 0;
        }
    }
    public String getJson(){
        json = new JSONObject();
        try {
            return createJsonString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String  createJsonString() throws JSONException{
        createJsonSession();
        createModuleName();
        createQuery();
        createOrderBy();
        createOffset();
        createDeleted();
        return json.toString();
    }

    private void createDeleted() throws JSONException {
        json.put("deleted",Byte.toString(deleted));
    }

    private void createOffset() throws JSONException {
        json.put("offset",offset);
    }

    private void createOrderBy() throws JSONException {
        json.put("order_by",orderBy);
    }

    private void createQuery() throws JSONException {
        json.put("query",where);
    }

    private void createModuleName() throws JSONException {
        json.put("module_name",moduleName);
    }

    private void createJsonSession() throws JSONException {
        json.put("session",session);
    }
}
