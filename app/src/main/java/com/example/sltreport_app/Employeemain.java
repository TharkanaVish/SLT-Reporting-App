package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Employeemain extends AppCompatActivity {
    Button ereport,eviewreport,emsg,eprofile,etask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeemain);

        ereport = findViewById(R.id.btn_report_emain);
        eviewreport = findViewById(R.id.btn_viewreport_emain);
        emsg = findViewById(R.id.btn_message_emain);
        eprofile = findViewById(R.id.btn_profile_emain);
        etask = findViewById(R.id.btn_assigntask_emain);

        ereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Employeemain.this, Reportbreakdown.class));
            }
        });

        eviewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Employeemain.this, ViewAddedReports.class));
            }
        });

        eprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Employeemain.this, ViewEmpProfile.class));
            }
        });

        etask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Employeemain.this, AssignedTasks.class));
            }
        });
    }
}