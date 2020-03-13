package com.coder.applicationhostel;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPackage_Fragment extends Fragment {
    EditText  fare , noofseats;
    Button addPackage;
    Spinner typeSpinner , parkingSpinner , electricitySpinner , messSpinner , laundarySpinner , acSpinner , cctvSpinner , internetSpinner;
    private ImageButton image;
    String packagetype , parking , electricity , mess , laundary , ac , cctv , internet;
    private Uri filePath;
    private StorageReference StorageRef;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressBar progressBar;

    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_package_, container, false);
        getActivity().setTitle("Add new package");
        image = v.findViewById(R.id.imgPackage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }

        });
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Packages");
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String hostelId=user.getUid();
        StorageRef = FirebaseStorage.getInstance().getReference();
        typeSpinner = v.findViewById(R.id.spinnerType);
        progressBar = (ProgressBar) v.findViewById(R.id.loginProgress);
        progressBar.setVisibility(View.GONE);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                packagetype = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fare = v.findViewById(R.id.edtFare);
        noofseats = v.findViewById(R.id.edtNoofseats);

        parkingSpinner = v.findViewById(R.id.spinnerParking);
        parkingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parking = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        electricitySpinner = v.findViewById(R.id.spinnerElectricity);
        electricitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                electricity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        messSpinner = v.findViewById(R.id.spinnerMess);
        messSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mess = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        laundarySpinner = v.findViewById(R.id.spinnerLaundary);
        laundarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                laundary = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        acSpinner = v.findViewById(R.id.spinnerAc);
        acSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ac = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cctvSpinner = v.findViewById(R.id.spinnerCctv);
        cctvSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cctv = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        internetSpinner = v.findViewById(R.id.spinnerInternet);
        internetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                internet = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addPackage = (Button) v.findViewById(R.id.btnAddPackage);

        addPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fare.getText().toString().isEmpty() && !noofseats.getText().toString().isEmpty() && !filePath.getPath().isEmpty() )
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext() , "Inserting Please wait" , Toast.LENGTH_LONG).show();
                        final String push = FirebaseDatabase.getInstance().getReference().child("Packages").push().getKey();
                        StorageReference fileReference  = StorageRef.child("images/"+ push);
                        fileReference.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                    if(filePath!=null) {
                                        AddPacakgeAttr addPacakgeAttr = new AddPacakgeAttr();
                                        addPacakgeAttr.setId(push);
                                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                        while (!urlTask.isSuccessful());
                                        Uri downloadUrl = urlTask.getResult();
                                        addPacakgeAttr.setImage_url(downloadUrl.toString());
                                        addPacakgeAttr.setPackageType(packagetype);
                                        addPacakgeAttr.setFare(fare.getText().toString());
                                        addPacakgeAttr.setNoOfSeats(noofseats.getText().toString());
                                        addPacakgeAttr.setParking(parking);
                                        addPacakgeAttr.setElectricity(electricity);
                                        addPacakgeAttr.setMess(mess);
                                        addPacakgeAttr.setLaundary(laundary);
                                        addPacakgeAttr.setAc(ac);
                                        addPacakgeAttr.setCctv(cctv);
                                        addPacakgeAttr.setInternet(internet);
                                        addPacakgeAttr.setTime(System.currentTimeMillis());
                                        addPacakgeAttr.setHostelId(hostelId);

                                        reference.child(push)
                                                .setValue(addPacakgeAttr);
                                        Toast.makeText(getContext(), "Inserted", Toast.LENGTH_LONG).show();


                                        AddPackage_Fragment addPackage_fragment = new AddPackage_Fragment();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.main_frame, addPackage_fragment, addPackage_fragment.getTag()).commit();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                else
                {
                    Toast.makeText(getActivity(), "Please enter all Information", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                image.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
