package app.suiteCRM.rest;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import app.suiteCRM.rest.dataClasses.GetEntryListDataParser;
import app.suiteCRM.rest.dataClasses.GetEntryListDataBuilder;
import app.suiteCRM.settings.ModuleList;
import app.suiteCRM.settings.PreferenceConstant;

public class ApiSuiteCRM {

    boolean badData = false;
    public String url;
    public String login;
    public String pass;
    public String session;
    public SharedPreferences sharedPreferences;
    public String error;

    public ApiSuiteCRM(String login, String pass, String url, SharedPreferences sharedPreferences) {
        if (login.equals("") || pass.equals("") || url.equals("")) {
            badData = true;
        }
        this.url = url;
        this.login = login;
        this.pass = pass;
        this.sharedPreferences = sharedPreferences;

    }

    public ApiSuiteCRM(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        initAutorisationDataFromSettings();
    }

    private void initAutorisationDataFromSettings() {
        url = sharedPreferences.getString(PreferenceConstant.URL, "");
        login = sharedPreferences.getString(PreferenceConstant.LOGIN, "");
        pass = sharedPreferences.getString(PreferenceConstant.PASSWORD, "");
        String timeSessionInPreference = sharedPreferences.getString(PreferenceConstant.SESSION_TIME, "");
        long timeSession = 0L;
        if (!timeSessionInPreference.equals("")) {
            timeSession = Long.parseLong(timeSessionInPreference, 10);
        }
        long nowTime = System.currentTimeMillis() / 1000L;
        if (timeSession + 60 < nowTime) {
            this.authorization();
        }
    }

    public void save_session(String session) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PreferenceConstant.SESSION, session);
        long ut2 = System.currentTimeMillis() / 1000L;
        editor.putString(PreferenceConstant.SESSION_TIME, String.valueOf(ut2));
        editor.apply();
    }

    public String authorization() {
        RestDataLolin restDataLolin = new RestDataLolin(login, pass);
        if (badData) {
            return "";
        }
        Gson gson = new Gson();
        String json = gson.toJson(restDataLolin);
        RestSuite restSuite = new RestSuite(url, "login", json);
        String result = restSuite.sendRequest();
        //JSONPasse
        String cacheUser;
        String resultMessage;
        try {
            JSONObject resultJson = new JSONObject(result);
            cacheUser = resultJson.getString("id");
            session = cacheUser;
            save_session(cacheUser);
            resultMessage = "sucsess";
        } catch (JSONException e) {
            JSONObject resultJson = null;
            try {
                resultJson = new JSONObject(result);
                cacheUser = resultJson.getString("name");
                resultMessage = cacheUser;
            } catch (JSONException jsonException) {
                cacheUser = "error";
                resultMessage = cacheUser;
            }
        }

        return resultMessage;
    }

    public ModuleList getModuleList() {
        GetEntryListDataBuilder moduleListJSON = new GetEntryListDataBuilder(sharedPreferences, "MBL_MODULE_PUBLIC_LIST");
        String dataParams = moduleListJSON.getJson();
        RestSuite restSuite = new RestSuite(url, "get_entry_list", dataParams);
        String result = restSuite.sendRequest();
        ModuleList moduleMenuCollections = new ModuleList();
        try {
            GetEntryListDataParser getEntryDataParser = new GetEntryListDataParser();
            ModuleList moduleList = getEntryDataParser.parseEntryList(result);
            save_module_menu_list(moduleList);
            return getEntryDataParser.parseEntryList(result);
        } catch (JSONException e) {
            JSONObject resultJson = null;
            try {
                resultJson = new JSONObject(result);
                error = resultJson.getString("name");
            } catch (JSONException jsonException) {
                error = "error parse JSON in getModuleList";
            }
        }
        return moduleMenuCollections;
    }

    private void save_module_menu_list(ModuleList moduleList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PreferenceConstant.MODULE_MENU_LIST);
        editor.putString(PreferenceConstant.MODULE_MENU_LIST, moduleList.serialise());
        editor.apply();
    }
}
