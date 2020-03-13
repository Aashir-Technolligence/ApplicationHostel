package com.coder.applicationhostel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<AddPacakgeAttr> pacakgeAttrs;
    ViewHolder adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home_, container, false);
        getActivity().setTitle("My packages");

            recyclerView = v.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,VERTICAL, true));
            pacakgeAttrs = new ArrayList<AddPacakgeAttr>();
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            final String hostelId=user.getUid();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Packages");

            databaseReference.orderByChild("hostelId").equalTo(hostelId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    pacakgeAttrs.clear();
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        AddPacakgeAttr p = dataSnapshot1.getValue(AddPacakgeAttr.class);
                        pacakgeAttrs.add(p);
                    }

                    adapter = new ViewHolder(getContext() , pacakgeAttrs );
                    recyclerView.setAdapter(adapter);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getContext() , "Oppss.. Something went wrong" , Toast.LENGTH_LONG).show();
                }
            });
        return v;
    }

}
