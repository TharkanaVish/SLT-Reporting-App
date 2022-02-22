package com.example.sltreport_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Reportbreakdown extends AppCompatActivity {
    private final static int PERMISSION_REQUEST = 1;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private static final String TAG = Reportbreakdown.class.getSimpleName();
    public static final int GALLERY_REQUEST_CODE = 105;
    ImageView selectedImage;
    private Button gpsButton;
    private TextView progressTitle;
    private ProgressBar progressBar,imgProgressBar;
    private TextView detailsText;

    private Button shareButton;
    private Button copyButton;
    private Button viewButton;

    private LocationManager locManager;
    private Location lastLocation;
    EditText town,vilage,description,location,imageName,imageUrl;
    Button addtomylist,camera,addtosuplist,gallery;
    Report reportob;
    FirebaseDatabase rootNode;
    DatabaseReference db;
    StorageReference storageReference;
    int i=0;
    String currentPhotoPath;

    private final LocationListener locListener = new LocationListener() {
        public void onLocationChanged(Location loc) {
            updateLocation(loc);
        }

        public void onProviderEnabled(String provider) {
            updateLocation();
        }

        public void onProviderDisabled(String provider) {
            updateLocation();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportbreakdown);
        setTitle(R.string.app_name);
        location=findViewById(R.id.location);
        town= findViewById(R.id.town);
        vilage=findViewById(R.id.vilage);
        description=findViewById(R.id.editTextTextMultiLine);
        imageName = findViewById(R.id.imageName);
        imageUrl = findViewById(R.id.imageUrl);
        camera = findViewById(R.id.addimage);
        gallery = findViewById(R.id.gallery);
        addtosuplist = findViewById(R.id.btn_addBreakdownWithAssign);
        addtomylist=findViewById(R.id.addbreakdown);
        gpsButton = findViewById(R.id.gpsButton);
        progressTitle = findViewById(R.id.progressTitle);
        progressBar = findViewById(R.id.progressBar);
        detailsText = findViewById(R.id.detailsText);
        shareButton = findViewById(R.id.shareButton);
        copyButton = findViewById(R.id.copyButton);
        viewButton = findViewById(R.id.viewButton);
        selectedImage = findViewById(R.id.imgView_camera);
        imgProgressBar = findViewById(R.id.imgPrograssBar);

        imgProgressBar.setVisibility(View.INVISIBLE);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        reportob = new Report();

        storageReference = FirebaseStorage.getInstance().getReference();


        camera.setOnClickListener(new View.OnClickListener() {
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


    }

    // Creates and displays a notification
    private void addNotification() {
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.edd)
                .setContentTitle("SIRK APP Notification")
                .setContentText("Report has been added Successfully!!!!!");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(Reportbreakdown.this, Employeemain.class);
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
                imageName.setText(f.getName());

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
        StorageReference image = storageReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imgProgressBar.setVisibility(View.INVISIBLE);
                        imageUrl.setText(uri.toString());
                        Picasso.get().load(uri).into(selectedImage);
                    }
                });
                Toast.makeText(Reportbreakdown.this,"Image Uploaded successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                imgProgressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                imgProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Reportbreakdown.this,"Upload failed",Toast.LENGTH_SHORT).show();
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



    public void add(View view){
        rootNode=FirebaseDatabase.getInstance();
        db= rootNode.getReference("Report");

        try{if(TextUtils.isEmpty(town.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Enter Town Name",Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(vilage.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Enter vilage name",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(description.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "Input description", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(location.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Copy location",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(imageName.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Add an Image",Toast.LENGTH_LONG).show();
        }
        else {
            reportob.setTown(town.getText().toString().trim());
            reportob.setVilage(vilage.getText().toString().trim());
            reportob.setDescription(description.getText().toString().trim());
            reportob.setLocation(location.getText().toString().trim());
            reportob.setImageName(imageUrl.getText().toString().trim());
            String Name = town.getText().toString().trim();
            addNotification();

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
        location.setText("");
        imageName.setText("");
    }


    @Override
    protected void onStop() {
        super.onStop();
        try {
            locManager.removeUpdates(locListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Failed to stop listening for location updates", e);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        startRequestingLocation();
        updateLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST &&
                grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRequestingLocation();
        } else {
            Toast.makeText(getApplicationContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
            finish();
        }

        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void updateLocation() {
        // Trigger a UI update without changing the location
        updateLocation(lastLocation);
    }
    private void updateLocation(Location location) {
        boolean locationEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean waitingForLocation = locationEnabled && !validLocation(location);
        boolean haveLocation = locationEnabled && !waitingForLocation;

        // Update display area
        gpsButton.setVisibility(locationEnabled ? View.GONE : View.VISIBLE);
        progressTitle.setVisibility(waitingForLocation ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(waitingForLocation ? View.VISIBLE : View.GONE);
        detailsText.setVisibility(haveLocation ? View.VISIBLE : View.GONE);

        // Update buttons
        shareButton.setEnabled(haveLocation);
        copyButton.setEnabled(haveLocation);
        viewButton.setEnabled(haveLocation);

        if (haveLocation) {
            String newline = System.getProperty("line.separator");
            detailsText.setText(String.format("%s: %s%s%s: %s (%s)%s%s: %s (%s)",
                    getString(R.string.accuracy), getAccuracy(location), newline,
                    getString(R.string.latitude), getLatitude(location), getDMSLatitude(location), newline,
                    getString(R.string.longitude), getLongitude(location), getDMSLongitude(location)));

            lastLocation = location;
        }
    }

    // ----------------------------------------------------
    // DialogInterface Listeners
    // ----------------------------------------------------
    private class onClickShareListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int i) {
            shareLocationText(formatLocation(lastLocation, getResources().getStringArray(R.array.link_options)[i]));
        }
    }

    private class onClickCopyListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int i) {
            copyLocationText(formatLocation(lastLocation, getResources().getStringArray(R.array.link_options)[i]));
        }
    }
    // ----------------------------------------------------
    // Actions
    // ----------------------------------------------------
    public void shareLocation(View view) {
        if (!validLocation(lastLocation)) {
            return;
        }

        String linkChoice = PreferenceManager.getDefaultSharedPreferences(this).getString("prefLinkType", "");

        if (linkChoice.equals(getResources().getString(R.string.always_ask))) {
            new AlertDialog.Builder(this).setTitle(R.string.choose_link)
                    .setCancelable(true)
                    .setItems(R.array.link_names, new onClickShareListener())
                    .create()
                    .show();
        } else {
            shareLocationText(formatLocation(lastLocation, linkChoice));
        }
    }

    public void copyLocation(View view) {
        if (!validLocation(lastLocation)) {
            return;
        }

        String linkChoice = PreferenceManager.getDefaultSharedPreferences(this).getString("prefLinkType", "");

        if (linkChoice.equals(getResources().getString(R.string.always_ask))) {
            new AlertDialog.Builder(this).setTitle(R.string.choose_link)
                    .setCancelable(true)
                    .setItems(R.array.link_names, new onClickCopyListener())
                    .create()
                    .show();
        } else {
            copyLocationText(formatLocation(lastLocation, linkChoice));
        }
    }

    public void viewLocation(View view) {
        if (!validLocation(lastLocation)) {
            return;
        }

        String uri = formatLocation(lastLocation, "geo:{0},{1}?q={0},{1}");

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(Intent.createChooser(intent, getString(R.string.view_location_via)));
    }

    public void openLocationSettings(View view) {
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    // ----------------------------------------------------
    // Helper functions
    // ----------------------------------------------------
    public void shareLocationText(String string) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, string);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getString(R.string.share_location_via)));
    }

    public void copyLocationText(String string) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText(getString(R.string.app_name), string);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), R.string.copied, Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Failed to get the clipboard service");
            Toast.makeText(getApplicationContext(), R.string.clipboard_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void startRequestingLocation() {
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
            return;
        }

        // GPS enabled and have permission - start requesting location updates
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")

    private boolean validLocation(Location location) {
        if (location == null) {
            return false;
        }

        // Location must be from less than 30 seconds ago to be considered valid
        if (Build.VERSION.SDK_INT < 17) {
            return System.currentTimeMillis() - location.getTime() < 30e3;
        } else {
            return SystemClock.elapsedRealtimeNanos() - location.getElapsedRealtimeNanos() < 30e9;
        }
    }
    private String getAccuracy(Location location) {
        float accuracy = location.getAccuracy();
        if (accuracy < 0.01) {
            return "?";
        } else if (accuracy > 99) {
            return "99+";
        } else {
            return String.format(Locale.US, "%2.0fm", accuracy);
        }
    }
    private String getLatitude(Location location) {
        return String.format(Locale.US, "%2.5f", location.getLatitude());
    }
    private String getDMSLongitude(Location location) {
        double val = location.getLongitude();
        return String.format(Locale.US, "%.0f° %2.0f′ %2.3f″ %s",
                Math.floor(Math.abs(val)),
                Math.floor(Math.abs(val * 60) % 60),
                (Math.abs(val) * 3600) % 60,
                val > 0 ? "E" : "W"
        );
    }
    private String getDMSLatitude(Location location) {
        double val = location.getLatitude();
        return String.format(Locale.US, "%.0f° %2.0f′ %2.3f″ %s",
                Math.floor(Math.abs(val)),
                Math.floor(Math.abs(val * 60) % 60),
                (Math.abs(val) * 3600) % 60,
                val > 0 ? "N" : "S"
        );
    }
    private String getLongitude(Location location) {
        return String.format(Locale.US, "%3.5f", location.getLongitude());
    }

    private String formatLocation(Location location, String format) {
        return MessageFormat.format(format,
                getLatitude(location), getLongitude(location));
    }

}