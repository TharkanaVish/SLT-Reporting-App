package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Employeemain extends AppCompatActivity {
    Button ereport,eviewreport,emsg,eprofile,etask,logout;
    EditText editTextTextPersonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeemain);

        ereport = findViewById(R.id.btn_report_emain);
        eviewreport = findViewById(R.id.btn_viewreport_emain);
        emsg = findViewById(R.id.btn_message_emain);
        eprofile = findViewById(R.id.btn_profile_emain);
        etask = findViewById(R.id.btn_assigntask_emain);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);


        // get saved phone number
        String phone = getIntent().getStringExtra("Key1");


        editTextTextPersonName.setText(phone);


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
                String phone=editTextTextPersonName.getText().toString().trim();

                Intent i = new Intent(Employeemain.this, ViewEmpProfile.class);
                i.putExtra("Key2",phone);
                startActivity(i);

            }
        });

        etask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Employeemain.this, AssignedTasks.class));
            }
        });


        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Employeemain.this,LoginPhone.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}