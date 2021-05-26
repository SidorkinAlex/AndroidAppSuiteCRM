package app.suiteCRM.ui.settings

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Looper
import android.preference.PreferenceManager
import android.preference.PreferenceManager.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import app.suiteCRM.R
import app.suiteCRM.rest.ApiSuiteCRM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val APP_URL: String = "url"
    private val APP_MAIN_SETTINGS: String = "APP_MAIN"
    private val APP_USER_LOGIN: String = "login"
    private val APP_USER_PASS: String = "pass"
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var url: EditText
    private lateinit var login: EditText
    private lateinit var pass: EditText
    private lateinit var saveButton: Button
    private lateinit var testButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment;
        return inflater.inflate(R.layout.fragment_settings_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)!!
        initField(view);
        setValueFromPreference();
        initButton(view)
    }

    private fun initButton(view: View) {
        saveButton = view.findViewById(R.id.settings_save_button)
        saveButton.setOnClickListener {
            savePreferense()
        }
        testButton = view.findViewById(R.id.settings_test_button)
        testButton.setOnClickListener {

            testSettings(view.context)
        }
    }

    private fun testSettings(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("login",login.text.toString())
            Log.d("pass",pass.text.toString())
            Log.d("url",url.text.toString())
            val suiteApi:ApiSuiteCRM = ApiSuiteCRM(login.text.toString(),
                pass.text.toString(),
            url.text.toString(),sharedPreferences)
            val result = suiteApi.authorization()
            CoroutineScope(Dispatchers.Main).launch {
            val myToast1 = Toast.makeText(context, result, Toast.LENGTH_SHORT)
            myToast1.show()
            Looper.loop()
            }
        }


    }

    @SuppressLint("CommitPrefEdits")
    private fun savePreferense() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(APP_URL, url.text.toString())
        editor.putString(APP_USER_LOGIN, login.text.toString())
        editor.putString(APP_USER_PASS, pass.text.toString())
        editor.apply()
        val text: String =
            url.text.toString() + " " + pass.text.toString() + " " + login.text.toString();
        val myToast = Toast.makeText(this.context, text, Toast.LENGTH_SHORT)
        myToast.show()
    }

    private fun setValueFromPreference() {
        val settingsUrl = sharedPreferences.getString(APP_URL, "")
        if (!settingsUrl.equals("")) {
            url.setText(settingsUrl)
        }
        val settingsLogin = sharedPreferences.getString(APP_USER_LOGIN, "")
        if (!settingsLogin.equals("")) {
            login.setText(settingsLogin)
        }
        val settingsPass = sharedPreferences.getString(APP_USER_PASS, "")
        if (!settingsPass.equals("")) {
            pass.setText(settingsPass)
        }
    }

    private fun initField(view: View) {
        url = view.findViewById(R.id.url_root)
        pass = view.findViewById(R.id.pass_crm)
        login = view.findViewById(R.id.login_crm);
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}