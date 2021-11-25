package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Employeemain extends AppCompatActivity {

    TextView report,message,profile,tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeemain);

        report = findViewById(R.id.report);
        message = findViewById(R.id.message);
        profile = findViewById(R.id.profile);
        tasks = findViewById(R.id.tasks);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Employeemain.this, ViewEmpProfile.class));
            }
        });

        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Employeemain.this, AssignedTasks.class));
            }
        });
    }
}