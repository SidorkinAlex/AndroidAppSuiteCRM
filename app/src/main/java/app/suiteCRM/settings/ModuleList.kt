package app.suiteCRM.settings

import android.util.Log
import app.suiteCRM.rest.ModuleMenu
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

public class ModuleList : Serializable {
    public var moduleSuiteCRMCollection: ArrayList<ModuleMenu>
    public val JSON_PARSE_KEY = "moduleSuiteCRMCollection"

    constructor(moduleSuiteCRMCollection: ArrayList<ModuleMenu>) {
        this.moduleSuiteCRMCollection = moduleSuiteCRMCollection
    }

    constructor() {
        this.moduleSuiteCRMCollection = ArrayList<ModuleMenu>()
    }

    constructor(json: String) {
        moduleSuiteCRMCollection = ArrayList<ModuleMenu>()

    }

    public fun add(moduleMenu: ModuleMenu) {
        moduleSuiteCRMCollection.add(moduleMenu)
    }

    public fun getArrayList(): ArrayList<ModuleMenu> {
        return moduleSuiteCRMCollection
    }

    public fun serialise(): String {
        val gson: Gson = Gson()
        return gson.toJson(this)
    }

    public fun unserialise(json: String) {
        try {
            val jsonObject: JSONObject = JSONObject(json)
            val jsonModuleSuiteCRMCollection = jsonObject.optJSONArray(JSON_PARSE_KEY)
            if (jsonModuleSuiteCRMCollection != null) {
                for (i in 0 until jsonModuleSuiteCRMCollection.length()) {
                    val jsonModule = jsonModuleSuiteCRMCollection.getJSONObject(i)
                    moduleSuiteCRMCollection.add(ModuleMenu(jsonModule))

                    // Your code here
                }
            }
        } catch (e: JSONException) {
            Log.d("error","error parse JSON in unserialise ModuleList")
        }
    }

}