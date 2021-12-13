package com.example.sltreport_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    EditText town,vilage,description;
    Button addtomylist;
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
        addtomylist=findViewById(R.id.addbreakdown);
        reportob = new Report();

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