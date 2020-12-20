package com.example.atmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserid;
    private EditText edPasscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserid = findViewById(R.id.userid);
        edPasscode = findViewById(R.id.passcode);
    }

    public void login(View view){
        String userid = edUserid.getText().toString();
        String passcode = edPasscode.getText().toString();
        if ("jack".equals(userid)&&"1234".equals(passcode)){
            finish();
        }

    }

    public void quit(View view){

    }
}