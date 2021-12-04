package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SupervisorMain extends AppCompatActivity {

    Button sreport,sviewreport,smsg,sprofile,stask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_main);

        sreport = findViewById(R.id.btn_report_smain);
        sviewreport = findViewById(R.id.btn_viewreport_smain);
        smsg = findViewById(R.id.btn_message_smain);
        sprofile = findViewById(R.id.btn_profile_smain);
        stask = findViewById(R.id.btn_assigntask_smain);

        sreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupervisorMain.this, Reportbreakdown.class));
            }
        });

        sviewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupervisorMain.this, ViewReportsEdit.class));
            }
        });

        sprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupervisorMain.this, ViewEmpProfile.class));
            }
        });

        stask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupervisorMain.this, AssignedTasks.class));
            }
        });
    }
}