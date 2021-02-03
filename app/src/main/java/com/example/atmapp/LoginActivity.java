package com.example.atmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edUserid;
    private EditText edPasscode;
    private CheckBox cbremember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSharedPreferences("atm", MODE_PRIVATE).edit().putInt("LEVEL", 3).putString("NAME", "jack").commit();
        edUserid = findViewById(R.id.userid);
        edPasscode = findViewById(R.id.passcode);
        cbremember = findViewById(R.id.cb_userid);
        cbremember.setChecked(getSharedPreferences("atm", MODE_PRIVATE).getBoolean("REMEMBER_USERID", false));
        cbremember.setOnCheckedChangeListener((compoundButton, b) -> getSharedPreferences("atm", MODE_PRIVATE)
                .edit().putBoolean("REMEMBER_USERID", b).apply());
        int level = getSharedPreferences("atm", MODE_PRIVATE)
                .getInt("LEVEL", 0);
        Log.d(TAG, "onCreate: " + level);
        String userid = getSharedPreferences("atm", MODE_PRIVATE)
                .getString("USERID", "");
        edUserid.setText(userid);
    }

    public void login(View view) {
        final String userid = edUserid.getText().toString();
        final String passcode = edPasscode.getText().toString();
        FirebaseDatabase.getInstance().getReference("users").child(userid).child(passcode)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pw = (String) snapshot.getValue();
                        if (snapshot.hasChild(userid)) {
                            boolean remember = getSharedPreferences("atm", MODE_PRIVATE)
                                    .getBoolean("REMEMBER_USERID", false);

                            assert pw != null;
                            if (remember) {
                                if (pw.equals(passcode)) {
                                    //save userid
                                    getSharedPreferences("atm", MODE_PRIVATE)
                                            .edit().putString("USERID", userid).apply();
                                }
                                setResult(RESULT_OK);
                                finish();
                            }
                        } else {
                            setResult(RESULT_CANCELED);
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
/*        if ("jack".equals(userid)&&"1234".equals(passcode)){
            finish();
        }*/

    }

    public void quit(View view) {

    }
}