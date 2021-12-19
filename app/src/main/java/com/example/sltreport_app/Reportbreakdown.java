package com.example.sltreport_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Reportbreakdown extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    EditText town,vilage,description;
    Button addtomylist,gps,camera,addtosuplist;
    Report reportob;
    FirebaseDatabase rootNode;
    DatabaseReference db;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportbreakdown);

        town= findViewById(R.id.town);
        vilage=findViewById(R.id.vilage);
        description=findViewById(R.id.editTextTextMultiLine);
        gps = findViewById(R.id.addlocation);
        camera = findViewById(R.id.addimage);
        addtosuplist = findViewById(R.id.btn_addBreakdownWithAssign);
        addtomylist=findViewById(R.id.addbreakdown);
        reportob = new Report();


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });

    }

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else{
            openCamera();
        }
    }

    private void openCamera() {
        Toast.makeText(this,"Camera Open requested",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void add(View view){
        rootNode=FirebaseDatabase.getInstance();
        db= rootNode.getReference("Report");

        try{if(TextUtils.isEmpty(town.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Enter Town Name",Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(vilage.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Enter vilage name",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(description.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Input description",Toast.LENGTH_LONG).show();

        } else {
            reportob.setTown(town.getText().toString().trim());
            reportob.setVilage(vilage.getText().toString().trim());
            reportob.setDescription(description.getText().toString().trim());


            String Name = town.getText().toString().trim();


            db.child(Name).setValue(reportob);

            Toast.makeText(getApplicationContext(),"Report Added",Toast.LENGTH_LONG).show();


            startActivity(new Intent(Reportbreakdown.this, Employeemain.class));



            ClearControls();

        }}
        catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(), "Number Format Exception", Toast.LENGTH_LONG).show();
        }


   }


    public void ClearControls() {
        town.setText("");
        vilage.setText("");
        description.setText("");
    }

}