package com.lvmama.module_login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.steve.NameGenerate;

@NameGenerate(name = "LoginActivity")
 public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);
    }
}
