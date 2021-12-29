package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SupervisorMain extends AppCompatActivity {

    Button sreport,sviewreport,smsg,sprofile,stask;
    EditText editTextTextPersonName5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_main);

        sreport = findViewById(R.id.btn_report_smain);
        sviewreport = findViewById(R.id.btn_viewreport_smain);
        smsg = findViewById(R.id.btn_message_smain);
        sprofile = findViewById(R.id.btn_profile_smain);
        stask = findViewById(R.id.btn_assigntask_smain);
        editTextTextPersonName5=findViewById(R.id.editTextTextPersonName5);


        // get saved mail
        String email = getIntent().getStringExtra("Key4");


        editTextTextPersonName5.setText(email);



        sreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupervisorMain.this, Reportbreakdown.class));
            }
        });

        sviewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupervisorMain.this, ViewAddedReports.class));
            }
        });

        sprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        String emails=editTextTextPersonName5.getText().toString().trim();

                Intent t=new Intent(SupervisorMain.this, ViewEmpProfile.class);
                t.putExtra("Key5",emails);
                startActivity(t);

            }
        });

        stask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupervisorMain.this, AssignedTasks.class));
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(SupervisorMain.this,LoginGmail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}