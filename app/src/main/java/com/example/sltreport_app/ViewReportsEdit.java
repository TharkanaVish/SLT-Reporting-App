package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewReportsEdit extends AppCompatActivity {

    Button gps,img,edit,back;
    EditText town,village,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_edit);

        gps = findViewById(R.id.btn_addlocation);
        img = findViewById(R.id.btn_addimage);
        edit = findViewById(R.id.btn_editbreakdown);
        back = findViewById(R.id.btn_viewReport_bck);
        town = findViewById(R.id.et_town);
        village = findViewById(R.id.et_vilage);
        desc = findViewById(R.id.et_description);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewReportsEdit.this, SupervisorMain.class));
            }
        });
    }
}