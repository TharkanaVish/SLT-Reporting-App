package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class Emp_ViewReportEdit extends AppCompatActivity {

    Button emp_gps,emp_img,emp_completeImg,emp_edit,emp_back,emp_jobdone;
    EditText emp_town,emp_village,emp_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp__view_report_edit);

        emp_gps = findViewById(R.id.btnEmp_addlocation);
        emp_img = findViewById(R.id.btnEmp_addimage);
        emp_completeImg = findViewById(R.id.btnEmp_add_completed_image);
        emp_edit = findViewById(R.id.btnEmp_editbreakdown);
        emp_back = findViewById(R.id.btnEmp_viewReport_bck);
        emp_jobdone = findViewById(R.id.btnEmp_jobdone);
        emp_town = findViewById(R.id.etEmp_town);
        emp_village = findViewById(R.id.etEmp_vilage);
        emp_desc = findViewById(R.id.etEmp_description);

        emp_jobdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Emp_ViewReportEdit.this, Employeemain.class));
            }
        });

        emp_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Emp_ViewReportEdit.this, Employeemain.class));
            }
        });
    }
}