package com.coder.applicationhostel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class RegisterActivity extends AppCompatActivity {
    private RadioGroup radioType;
    private RadioButton radioTypeButton;
    private Button btnRegister;
    private EditText name, password, contact, email;
    FirebaseAuth auth;
    Hostel_Admin_Attr admin_attr;
    String Hostel_Name, Password, Email, Contact, Hostel_Type;
    double LocLongitude = 0.0;
    double LocLatitude = 0.0;
    ProgressBar progressBar;
    private FusedLocationProviderClient mFusedLocationClient;

    static int time;
    private static final long MIN_TIME_BW_UPDATES = 1000 * time;

    // Declaring a Location Manager
    protected LocationManager mlocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("Register");

        radioType = (RadioGroup) findViewById(R.id.radioType);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        name = (EditText) findViewById(R.id.edtHostelName);
        password = (EditText) findViewById(R.id.edtHPassword);
        contact = (EditText) findViewById(R.id.edtContact);
        email = (EditText) findViewById(R.id.edtEmail);
        progressBar = (ProgressBar) findViewById(R.id.registerProgress);

        progressBar.setVisibility(View.GONE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION} , 1);
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} ,2);
            recreate();
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            LocLongitude = location.getLongitude();
                            LocLatitude = location.getLatitude();
                        }
                    }
                });

        auth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioType.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioTypeButton = (RadioButton) findViewById(selectedId);

                //Toast.makeText(RegisterActivity.this,
                //      radioTypeButton.getText(), Toast.LENGTH_SHORT).show();

                Hostel_Name = name.getText().toString().trim();
                Password = password.getText().toString().trim();
                Contact = contact.getText().toString().trim();
                Email = email.getText().toString().trim();
                Hostel_Type = radioTypeButton.getText().toString().trim();

                if (!Hostel_Name.isEmpty() && !Password.isEmpty() && !Contact.isEmpty() && !Email.isEmpty() && !Hostel_Type.isEmpty())
                {
                    if(Email.contains("@")||Email.contains(".com")) {
                        progressBar.setVisibility(View.VISIBLE);
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, Password)
                                .addOnCompleteListener(

                                        new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            admin_attr = new Hostel_Admin_Attr();
                                            admin_attr.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            admin_attr.setHostel_Name(Hostel_Name);
                                            admin_attr.setPassword(Password);

                                            admin_attr.setContact(Contact);
                                            admin_attr.setEmail(Email);
                                            admin_attr.setHostel_Type(Hostel_Type);
                                            admin_attr.setLocLatitude(LocLatitude);
                                            admin_attr.setLocLongitude(LocLongitude);
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("Hostel_Admin")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(admin_attr);
                                            Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterActivity.this, NavActivity.class);
                                            startActivity(intent);
                                            finish();
                                            SharedPreferences.Editor editor = getSharedPreferences("Log", MODE_PRIVATE).edit();
                                            editor.putBoolean("isLoggedIn", true);
                                            editor.commit();

                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Please Enter all Information", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
