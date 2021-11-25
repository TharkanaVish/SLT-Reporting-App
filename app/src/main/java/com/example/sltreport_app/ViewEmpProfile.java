package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewEmpProfile extends AppCompatActivity {
    Button editEmp,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emp_profile);

        editEmp = findViewById(R.id.btn_viewEmp_editEmp);
        back = findViewById(R.id.btn_viewEmp_bck);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewEmpProfile.this, Employeemain.class));
            }
        });
    }
}