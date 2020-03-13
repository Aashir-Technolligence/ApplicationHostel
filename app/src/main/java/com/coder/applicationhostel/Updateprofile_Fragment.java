package com.coder.applicationhostel;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Updateprofile_Fragment extends Fragment {
    private RadioGroup radioType;
    private RadioButton radioTypeButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    private Button btnUpdate;

    private EditText name , password   , contact , email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_updateprofile_, container, false);
        getActivity().setTitle("Update profile");
        radioType = (RadioGroup) v.findViewById(R.id.radioType);
        int selectedId = radioType.getCheckedRadioButtonId();
        radioTypeButton = (RadioButton) v.findViewById(selectedId);
        btnUpdate = (Button) v.findViewById(R.id.btnUpdateProfile);

        name = (EditText) v.findViewById(R.id.edtHostelName);
        password = (EditText) v.findViewById(R.id.edtHPassword);
        contact = (EditText) v.findViewById(R.id.edtContact);
        email = (EditText) v.findViewById(R.id.edtEmail);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Hostel_Admin");
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String userId=user.getUid();
        reference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Hostel_Admin_Attr admin =  dataSnapshot.getValue(Hostel_Admin_Attr.class);

                name.setText(admin.getHostel_Name());
                password.setText(admin.getPassword());
                email.setText(admin.getEmail());
                contact.setText(admin.getContact());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !contact.getText().toString().isEmpty() && !email.getText().toString().isEmpty()) {
                    reference.child(userId).child("hostel_Name").setValue(name.getText().toString().trim());
                    reference.child(userId).child("password").setValue(password.getText().toString().trim());
                    reference.child(userId).child("email").setValue(email.getText().toString().trim());
                    reference.child(userId).child("contact").setValue(contact.getText().toString().trim());


                    Profile_Fragment profile_fragment = new Profile_Fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_frame, profile_fragment, profile_fragment.getTag()).commit();
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
