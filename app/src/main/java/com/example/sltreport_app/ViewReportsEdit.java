package com.example.sltreport_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ViewReportsEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    ProgressBar completedImgProgress;
    Button gps,edit,back,img,gallery;
    ImageView comimg,beoreImage;
    EditText town;
    EditText village;
    EditText desc;
    EditText CompletedimgUrl;
    EditText supervisor2;
    Spinner superlist;
    String currentPhotoPath;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_edit);
        DAOEmployee dao =new DAOEmployee();
        Report emp_edit = (Report)getIntent().getSerializableExtra("EDIT");
        StorageReference storageReference =  FirebaseStorage.getInstance().getReference().child("imageName");
//names
        gps = findViewById(R.id.btn_addlocation);
        img = findViewById(R.id.btn_add_completed_image);
        gallery = findViewById(R.id.btn_comImg_gallery);
        completedImgProgress = findViewById(R.id.ComImg_progressBar);
        CompletedimgUrl = findViewById(R.id.ComimgUrl);
        comimg = findViewById(R.id.edit_afterImage);
        edit = findViewById(R.id.btn_editbreakdown);
        back = findViewById(R.id.btn_viewReport_bck);
        town = findViewById(R.id.et_town);
        supervisor2=findViewById(R.id.supervisor2);
        village = findViewById(R.id.et_vilage);
        desc = findViewById(R.id.et_description);
        superlist = findViewById(R.id.sp_assign_super);
        beoreImage = findViewById(R.id.edit_beoreImage);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference getImage = databaseReference.child("images");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Supervisors_List,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        superlist.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewReportsEdit.this, SupervisorMain.class));
            }


        });

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(emp_edit.getImageName()).into(beoreImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewReportsEdit.this, "Error Loading Image", Toast.LENGTH_SHORT).show();

            }
        });

        town.setText(emp_edit.getTown());
        village.setText(emp_edit.getVilage());
        desc.setText(emp_edit.getDescription());
        gps.setText(emp_edit.getLocation());
        supervisor2.setText((emp_edit.getSuperViser()));
        superlist.setOnItemSelectedListener(this);
        
    edit.setOnClickListener(v->
    {
        Report emp = new Report(town.getText().toString(), village.getText().toString(),desc.getText().toString() ,supervisor2.getText().toString());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("town", town.getText().toString());
        hashMap.put("vilage", village.getText().toString());
        hashMap.put("description", desc.getText().toString());
        hashMap.put("supervisor", supervisor2.getText().toString());
        dao.update(emp_edit.getKey(), hashMap).addOnSuccessListener(suc ->
        {
            Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
            finish();
            
        }).addOnFailureListener(er ->
        {
            Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
        });

    });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selection = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(),selection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void browser1(View view) {
        Report emp_edit = (Report)getIntent().getSerializableExtra("EDIT");
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(emp_edit.getLocation()));
        startActivity(browserIntent);
    }
}