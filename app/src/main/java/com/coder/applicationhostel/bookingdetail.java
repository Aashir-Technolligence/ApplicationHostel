package com.coder.applicationhostel;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class bookingdetail extends Fragment {

    TextView packageType , fare  , parking ,  electricity , mess , laundary , ac , cctv , internet , status , studentName , contact;
    Button btnDelete;
    ImageView packageImage;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String hostelId = getArguments().getString("StudentId");//id get
        final String packageId = getArguments().getString("PackageId");
        final String bookingId = getArguments().getString("BookingId");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bookingdetail, container, false);
        getActivity().setTitle("Booking detail");
        packageType = v.findViewById(R.id.txtType);//design view get
        fare = v.findViewById(R.id.txtFare);
        parking = v.findViewById(R.id.txtParking);
        electricity = v.findViewById(R.id.txtElectricity);
        mess = v.findViewById(R.id.txtMess);
        laundary = v.findViewById(R.id.txtLaundary);
        ac = v.findViewById(R.id.txtAC);
        cctv = v.findViewById(R.id.txtCctv);
        internet = v.findViewById(R.id.txtInternet);
        packageImage = v.findViewById(R.id.packageImg);
        btnDelete= v.findViewById(R.id.btnDltBooking);
        status = v.findViewById(R.id.txtStatus);
        studentName = v.findViewById(R.id.txtSName);
        contact = v.findViewById(R.id.txtContact);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Booking");
        reference.child(bookingId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(getActivity()!=null) {
                    Booking_Attr booking_attr = dataSnapshot.getValue(Booking_Attr.class);
                    if (booking_attr != null) {
                        studentName.setText(booking_attr.getHostelName());
                        status.setText(booking_attr.getStatus());
                        contact.setText(booking_attr.getStudentContact());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Packages");
        reference.child(packageId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(getActivity()!=null) {
                    AddPacakgeAttr pacakgeAttr = dataSnapshot.getValue(AddPacakgeAttr.class);
                    if (pacakgeAttr != null) {
                        packageType.setText(pacakgeAttr.getPackageType());//value show on txt view
                        fare.setText(pacakgeAttr.getFare());
                        parking.setText(pacakgeAttr.getParking());
                        electricity.setText(pacakgeAttr.getElectricity());
                        mess.setText(pacakgeAttr.getMess());
                        laundary.setText(pacakgeAttr.getLaundary());
                        ac.setText(pacakgeAttr.getAc());
                        cctv.setText(pacakgeAttr.getCctv());
                        internet.setText(pacakgeAttr.getInternet());
                        Picasso.get().load(pacakgeAttr.getImage_url()).into(packageImage);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure want to delete ?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.getReference("Booking").child(bookingId).setValue(null);

                        Booking_Fragment bookingFragment = new Booking_Fragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_frame , bookingFragment ,bookingFragment.getTag()).commit();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return v;
    }

}
