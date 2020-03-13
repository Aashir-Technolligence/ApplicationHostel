package com.coder.applicationhostel;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPackageDetails extends Fragment {
    TextView packageType , seats , fare  , parking ,  electricity , mess , laundary , ac , cctv , internet ;
    Button btnEdit , btnDelete;
    ImageView packageImage;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final String hostelId = getArguments().getString("HostelId");
        final String packageId = getArguments().getString("PackageId");
        View v = inflater.inflate(R.layout.fragment_fragment_package_details, container, false);
        getActivity().setTitle("Package detail");

        packageType = v.findViewById(R.id.txtType);
        seats = v.findViewById(R.id.txtSeats);
        fare = v.findViewById(R.id.txtFare);
        parking = v.findViewById(R.id.txtParking);
        electricity = v.findViewById(R.id.txtElectricity);
        mess = v.findViewById(R.id.txtMess);
        laundary = v.findViewById(R.id.txtLaundary);
        ac = v.findViewById(R.id.txtAC);
        cctv = v.findViewById(R.id.txtCctv);
        internet = v.findViewById(R.id.txtInternet);
        packageImage = v.findViewById(R.id.packageImg);
        btnDelete= v.findViewById(R.id.btnDeletePackage);
        btnEdit = v.findViewById(R.id.btnUpdatePackage);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Packages");
        reference.child(packageId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(getActivity()!=null) {
                    AddPacakgeAttr pacakgeAttr = dataSnapshot.getValue(AddPacakgeAttr.class);
                    if (pacakgeAttr != null) {
                        packageType.setText(pacakgeAttr.getPackageType());
                        seats.setText(pacakgeAttr.getNoOfSeats());
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
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavActivity activity = (NavActivity) v.getContext();
                Fragment fr = new UpdatePackage();
                FragmentManager fm = activity.getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("PackageId", packageId);
                args.putString("HostelId" , hostelId);
                fr.setArguments(args);
                ft.replace(R.id.main_frame, fr);
                ft.commit();


            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(packageId).setValue(null);


                Home_Fragment home_fragment = new Home_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_frame , home_fragment ,home_fragment.getTag()).commit();

//                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage("Are you sure want to delete ?");
//                builder.setCancelable(true);
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//
//                    }
//                });

            }
        });
        return v;
    }

}
