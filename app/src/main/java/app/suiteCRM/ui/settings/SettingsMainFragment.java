package app.suiteCRM.ui.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import app.suiteCRM.rest.ApiSuiteCRM;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import app.suiteCRM.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static android.content.Context.MODE_PRIVATE;


public class SettingsMainFragment extends Fragment {
    private static final String APP_URL = "url" ;
    private static final String APP_MAIN_SETTINGS = "APP_MAIN" ;
    private static final String APP_USER_LOGIN = "login" ;
    private static final String APP_USER_PASS = "pass" ;
    private SharedPreferences sharedPreferences;
    EditText url;
    EditText login;
    EditText pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity ma = getActivity();
        sharedPreferences = ma.getSharedPreferences(
                APP_MAIN_SETTINGS, MODE_PRIVATE);
        initViewElements(view);
    }

    private void initViewElements(View view) {
        initButton(view);
        initEditText(view);
    }

    private void initEditText(View view) {
        url = view.findViewById(R.id.url_root);
        if(sharedPreferences.contains(APP_URL)){
            String url_value = sharedPreferences.getString(APP_URL,"");
            if (!url_value.equals("")){
                url.setText(url_value);
            }
        }
        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Reg(url.getText().toString())){
                    url.getBackground().mutate().setColorFilter(getResources().getColor(R.color.success), PorterDuff.Mode.SRC_ATOP);
                } else {
                    url.getBackground().mutate().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
                }
            }
            public boolean Reg(String testString){
                Pattern p = Pattern.compile("^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w\\.-]*)*\\/?$");
                Matcher m = p.matcher(testString);
                return m.matches();
            }
        });
        login = view.findViewById(R.id.login_crm);
        if(sharedPreferences.contains(APP_USER_LOGIN)){
            String login_value = sharedPreferences.getString(APP_USER_LOGIN,"");
            if (!login_value.equals("")){
                login.setText(login_value);
            }
        }
        pass = view.findViewById(R.id.pass_crm);
        if(sharedPreferences.contains(APP_USER_PASS)){
            String pass_value = sharedPreferences.getString(APP_USER_PASS,"");
            if (!pass_value.equals("")){
                pass.setText(pass_value);
            }
        }
    }


    private void initButton(View view) {
        Button saveButton = view.findViewById(R.id.settings_save);
        Button testButton = view.findViewById(R.id.settings_test);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(APP_URL, url.getText().toString());
                editor.putString(APP_USER_LOGIN,login.getText().toString());
                editor.putString(APP_USER_PASS, pass.getText().toString());
                editor.apply();
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_test = login.getText().toString();
                String pass_test = pass.getText().toString();
                String url_test = url.getText().toString();

            }
        });
    }

    public String testAuth(String login_test, String pass_test, String url_test, SharedPreferences sP){
        ApiSuiteCRM api = new ApiSuiteCRM(login_test,pass_test,url_test, sP);
        String result = api.authorization();
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_main, container, false);
    }
}