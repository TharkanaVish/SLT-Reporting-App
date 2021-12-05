package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewReportsEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button gps,img,edit,back;
    EditText town,village,desc;
    Spinner superlist;

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
        superlist = findViewById(R.id.sp_assign_super);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Supervisors_List,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        superlist.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewReportsEdit.this, SupervisorMain.class));
            }
        });

        superlist.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selection = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(),selection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}