package com.example.a59070083.healthy.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a59070083.healthy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            LoginFragment loginFragment = new LoginFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, loginFragment)
                    .commit();
        }
    }
}
