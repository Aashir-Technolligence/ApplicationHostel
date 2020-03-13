package com.coder.applicationhostel;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePackage extends Fragment {
    EditText fare , noofseats ;
    Button update;
    String packagetype;
    Spinner typeSpinner;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final String hostelId = getArguments().getString("HostelId");
        final String packageId = getArguments().getString("PackageId");
        View v = inflater.inflate(R.layout.fragment_update_package, container, false);
        getActivity().setTitle("Update package");
        typeSpinner = v.findViewById(R.id.spinnerType);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                packagetype = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        noofseats = v.findViewById(R.id.txtSeats);
        fare = v.findViewById(R.id.txtFare);
        update = v.findViewById(R.id.btnUpdate);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Packages");
        reference.child(packageId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(getActivity()!=null) {
                    AddPacakgeAttr pacakgeAttr = dataSnapshot.getValue(AddPacakgeAttr.class);
                    if (pacakgeAttr != null) {
                        noofseats.setText(pacakgeAttr.getNoOfSeats());
                        fare.setText(pacakgeAttr.getFare());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!noofseats.getText().toString().isEmpty() && !fare.getText().toString().isEmpty()) {
                    reference.child(packageId).child("packageType").setValue(packagetype.trim());
                    reference.child(packageId).child("noOfSeats").setValue(noofseats.getText().toString().trim());
                    reference.child(packageId).child("fare").setValue(fare.getText().toString().trim());


                    NavActivity activity = (NavActivity) v.getContext();
                    Fragment fr = new FragmentPackageDetails();
                    FragmentManager fm = activity.getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("PackageId", packageId);
                    fr.setArguments(args);
                    ft.replace(R.id.main_frame, fr);
                    ft.commit();
                }
                else
                {
                    Toast.makeText(getActivity(), "Please Enter all Information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

}
