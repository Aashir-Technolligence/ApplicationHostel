package com.coder.applicationhostel;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_Fragment extends Fragment {
    TextView name , contact , email  , type , password;
    Button btnEdit;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        //
        // for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_, container, false);
        getActivity().setTitle("My profile");
        name = v.findViewById(R.id.txtHostelName);
        contact = v.findViewById(R.id.txtContact);
        email = v.findViewById(R.id.txtEmail);
        type = v.findViewById(R.id.txtType);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Hostel_Admin");
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        final String userId=user.getUid();
        reference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Hostel_Admin_Attr admin =  dataSnapshot.getValue(Hostel_Admin_Attr.class);

                name.setText(admin.getHostel_Name());
                email.setText(admin.getEmail());
                contact.setText(admin.getContact());
                type.setText(admin.getHostel_Type());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnEdit = v.findViewById(R.id.btnUpdate);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updateprofile_Fragment updateprofile_fragment = new Updateprofile_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame , updateprofile_fragment ,updateprofile_fragment.getTag()).commit();

            }
        });
        return v;
    }

}
