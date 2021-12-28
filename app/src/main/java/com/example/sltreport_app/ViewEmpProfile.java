package com.example.sltreport_app;

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

public class ViewEmpProfile extends AppCompatActivity {
    Button editEmp,back;
    EditText et_viewEmp_empphnumber,et_viewEmp_empemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emp_profile);

        editEmp = findViewById(R.id.btn_viewEmp_editEmp);
        back = findViewById(R.id.btn_viewEmp_bck);
        et_viewEmp_empphnumber = findViewById(R.id.et_viewEmp_empphnumber);
        et_viewEmp_empemail=findViewById(R.id.et_viewEmp_empemail);


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






    }
}