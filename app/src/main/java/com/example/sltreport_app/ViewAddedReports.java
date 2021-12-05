package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewAddedReports extends AppCompatActivity {

    TextView report1,report2,report3,report4,report5,report6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_added_reports);

        report1 = findViewById(R.id.tv_view_added_report_1);
        report2 = findViewById(R.id.tv_view_added_report_2);
        report3 = findViewById(R.id.tv_view_added_report_3);
        report4 = findViewById(R.id.tv_view_added_report_4);
        report5 = findViewById(R.id.tv_view_added_report_5);
        report6 = findViewById(R.id.tv_view_added_report_6);


        report1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewAddedReports.this, ViewReportsEdit.class));
            }
        });

        report2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewAddedReports.this, ViewReportsEdit.class));
            }
        });

        report3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewAddedReports.this, ViewReportsEdit.class));
            }
        });

        report4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewAddedReports.this, ViewReportsEdit.class));
            }
        });

        report5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewAddedReports.this, ViewReportsEdit.class));
            }
        });

        report6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewAddedReports.this, ViewReportsEdit.class));
            }
        });

    }
}