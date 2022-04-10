package com.example.diacaree;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class SettingsActivity extends AppCompatActivity {

    private View recordIcon, historyIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        recordIcon = findViewById(R.id.ic_record);
        historyIcon = findViewById(R.id.ic_history);
        iconPicker();

    }

    private void iconPicker() {
        recordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        historyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}