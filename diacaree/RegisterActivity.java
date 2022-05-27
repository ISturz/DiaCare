package com.example.diacaree;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText newEmail, newUser, newPass, newConfirmPass;
    Button registerBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        newEmail = findViewById(R.id.userEmail);
        newUser = findViewById(R.id.userUsername);
        newPass = findViewById(R.id.userPass);
        newConfirmPass = findViewById(R.id.confirmPass);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = newEmail.getText().toString();
                String userName = newUser.getText().toString();
                String password = newPass.getText().toString();
                String confirmPassword = newConfirmPass.getText().toString();
                if (password.equals(confirmPassword)) {


                    DbHandler db = new DbHandler(RegisterActivity.this);
                    db.insertNewUser(email, userName, password);

                    Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Passwords Do Not Match", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
