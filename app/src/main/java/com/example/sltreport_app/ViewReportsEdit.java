package com.example.sltreport_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
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
    StorageReference storageReference =  FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_edit);
        DAOEmployee dao =new DAOEmployee();
        Report emp_edit = (Report)getIntent().getSerializableExtra("EDIT");
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

        completedImgProgress.setVisibility(View.INVISIBLE);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Supervisors_List,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        superlist.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewReportsEdit.this, SupervisorMain.class));
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
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
        CompletedimgUrl.setText((emp_edit.getCompletedimgUrl()));
        superlist.setOnItemSelectedListener(this);
        supervisor2.setText((emp_edit.getSuperViser()));

        edit.setOnClickListener(v->
    {
        Report emp = new Report(town.getText().toString(), village.getText().toString(),desc.getText().toString() ,supervisor2.getText().toString());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("town", town.getText().toString());
        hashMap.put("vilage", village.getText().toString());
        hashMap.put("description", desc.getText().toString());
        hashMap.put("supervisor", supervisor2.getText().toString());
        hashMap.put("CompletedimgUrl", CompletedimgUrl.getText().toString());

        dao.update(emp_edit.getKey(), hashMap).addOnSuccessListener(suc ->
        {
            Toast.makeText(this, "Record is Completed", Toast.LENGTH_SHORT).show();
            addNotification();
            finish();
            
        }).addOnFailureListener(er ->
        {
            Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
        });

    });

    }

    // Creates and displays a notification
    private void addNotification() {
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.edd)
                .setContentTitle("SIRK APP Notification")
                .setContentText("Report completed Successfully!!!!!");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(ViewReportsEdit.this, Employeemain.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                Log.d("tag","Absolute URI of the image is " + Uri.fromFile(f));
                //imageName.setText(f.getName());

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                uploadImageToFirebase(f.getName(),contentUri);
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);

                uploadImageToFirebase(imageFileName,contentUri);
            }
        }
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(contentUri));
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        StorageReference image = storageReference.child("completed_images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        completedImgProgress.setVisibility(View.INVISIBLE);
                         CompletedimgUrl.setText(uri.toString());
                        Picasso.get().load(uri).into(comimg);
                    }
                });
                Toast.makeText(ViewReportsEdit.this,"Image Uploaded successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                completedImgProgress.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                completedImgProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(ViewReportsEdit.this,"Upload failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else{
            dispatchTakePictureIntent();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
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