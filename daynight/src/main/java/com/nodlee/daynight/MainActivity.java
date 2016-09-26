package com.nodlee.daynight;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                // recreate();
                Toast.makeText(MainActivity.this, "日间模式", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_night).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                // recreate();
                Toast.makeText(MainActivity.this, "夜间模式", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TwoActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (uiMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                Toast.makeText(MainActivity.this, "夜间模式", Toast.LENGTH_SHORT).show();
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                Toast.makeText(MainActivity.this, "日间模式", Toast.LENGTH_SHORT).show();
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                Toast.makeText(MainActivity.this, "未知模式", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
