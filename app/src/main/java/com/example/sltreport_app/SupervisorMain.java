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
                Intent reportactivity = new Intent(SupervisorMain.this, Reportbreakdown.class);
                setResult(RESULT_OK, null);
                reportactivity.putExtra("Key11",email);

                startActivity(reportactivity);
                SupervisorMain.this.finish();
            }
        });

        sviewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viee = new Intent(SupervisorMain.this, RVActivity.class);
                setResult(RESULT_OK, null);
                viee.putExtra("Key14",email);
                startActivity(viee);
                SupervisorMain.this.finish();
            }
        });



        stask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewone = new Intent(SupervisorMain.this, SVActivity.class);
                setResult(RESULT_OK, null);
                viewone.putExtra("Key13",email);
                startActivity(viewone);
                SupervisorMain.this.finish();
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

