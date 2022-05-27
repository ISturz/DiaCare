package com.example.diacaree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegist;
    EditText name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.loginBtn);
        btnRegist = findViewById(R.id.registBtn);
        name = findViewById(R.id.userid);
        pass = findViewById(R.id.passid);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String password = pass.getText().toString();
                DbHandler db = new DbHandler(LoginActivity.this);
                String id = db.getUserID(userName);
                String pass = db.getPassword(id);
                if (password.equals(pass)) {
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Incorrect Details", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}