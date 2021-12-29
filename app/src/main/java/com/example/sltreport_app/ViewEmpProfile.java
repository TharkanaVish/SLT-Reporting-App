package com.example.sltreport_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewEmpProfile extends AppCompatActivity {
    Button editEmp,back;
    EditText et_viewEmp_empphnumber,et_viewEmp_empemail,et_viewEmp_empno,et_viewEmp_empname,et_viewEmp_empusname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emp_profile);

        editEmp = findViewById(R.id.btn_viewEmp_editEmp);
        back = findViewById(R.id.btn_viewEmp_bck);
        et_viewEmp_empphnumber = findViewById(R.id.et_viewEmp_empphnumber);
        et_viewEmp_empemail=findViewById(R.id.et_viewEmp_empemail);
        et_viewEmp_empno =findViewById(R.id.et_viewEmp_empno);
        et_viewEmp_empname=findViewById(R.id.et_viewEmp_empname);
        et_viewEmp_empusname=findViewById(R.id.et_viewEmp_empusname);
        // get saved phone number
        String phone = getIntent().getStringExtra("Key2");
        String emails=getIntent().getStringExtra("Key5");

        et_viewEmp_empphnumber.setText(phone);
        et_viewEmp_empemail.setText(emails);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewEmpProfile.this, Employeemain.class));
            }
        });

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("employee");
        databaseReference.child("-MpqEm6WRZmDPlTTM6aU").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emp= String.valueOf(snapshot.child("empno").getValue());
                String phone= String.valueOf(snapshot.child("number").getValue());
                String name= String.valueOf(snapshot.child("name").getValue());
                String uname= String.valueOf(snapshot.child("username").getValue());
                et_viewEmp_empno.setText(emp);
                et_viewEmp_empusname.setText(uname);
                et_viewEmp_empname.setText(name);
                et_viewEmp_empphnumber.setText(phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}