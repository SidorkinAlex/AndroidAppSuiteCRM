package com.example.suitecrmapp.ui.settings;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suitecrmapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SettingsMainFragment extends Fragment {
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
        initViewElements(view);
    }

    private void initViewElements(View view) {
        initButton(view);
        initEditText(view);
    }

    private void initEditText(View view) {
        url = view.findViewById(R.id.url_root);
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
        pass = view.findViewById(R.id.pass_crm);
    }


    private void initButton(View view) {
        Button saveButton = view.findViewById(R.id.settings_save);
        Button testButton = view.findViewById(R.id.settings_test);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(),
                        "Пора покормить кота!", Toast.LENGTH_SHORT);
                toast.show();

            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(),
                        "Пора покормить кота!", Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_main, container, false);
    }
}