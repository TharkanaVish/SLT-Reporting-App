package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button Employee,Supervisour,Manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Employee = findViewById(R.id.Employee);
        Supervisour = findViewById(R.id.Supervisour);
        Manager = findViewById(R.id.Manager);

        Employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, LoginPhone.class));
            }
        });

        Supervisour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, LoginGmail.class));
            }
        });
        Manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SupervisorMain.class));
            }
        });
    }
}