package com.example.diacaree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button btn_show;
    EditText name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_show = findViewById(R.id.loginBtn);
        name = findViewById(R.id.userid);
        pass = findViewById(R.id.passid);


        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String password = pass.getText().toString();
                if (userName.equals("admin") && password.equals("password")) {
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    System.out.println("Incorrect Credentials");
                }
            }
        });
    }
}