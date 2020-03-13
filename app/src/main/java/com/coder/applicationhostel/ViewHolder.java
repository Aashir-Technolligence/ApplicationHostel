package com.coder.applicationhostel;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanzalah on 2/22/2019.
 */
public class ViewHolder extends RecyclerView.Adapter<ViewHolder.Holder>{
    Context context;
    ArrayList<AddPacakgeAttr> pacakgeAttrs;

    public ViewHolder(Context c , ArrayList<AddPacakgeAttr> p /*, ArrayList<Hostel_Admin_Attr> h*/){
        context = c;
        pacakgeAttrs = p;
       // hostel_admin_attrs = h;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.post , parent , false));
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        Picasso.get().load(pacakgeAttrs.get(position).getImage_url()).into(holder.postimage);
        holder.postType.setText(pacakgeAttrs.get(position).getPackageType());
        holder.postSeats.setText(pacakgeAttrs.get(position).getNoOfSeats());
        holder.postFare.setText(pacakgeAttrs.get(position).getFare());
        String timestampDifference = getTimestampDifference(pacakgeAttrs.get(position));
        holder.postTime.setText(timestampDifference);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packageId = null;
                String hostelId = null;
                hostelId = pacakgeAttrs.get(position).getHostelId();
                packageId = pacakgeAttrs.get(position).getId();
                //Toast.makeText(v.getContext() , hostelId , Toast.LENGTH_LONG).show();

                NavActivity activity = (NavActivity) v.getContext();
                Fragment fr = new FragmentPackageDetails();
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
    }

    @Override
    public int getItemCount() {
        return pacakgeAttrs.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView postimage;
        TextView postType;
        TextView postSeats;
        TextView postFare;
        TextView postTime;
    public Holder(View itemView) {
        super(itemView);
        postimage = itemView.findViewById(R.id.postPic);
        postType = itemView.findViewById(R.id.postType);
        postSeats = itemView.findViewById(R.id.postSeats);
        postFare = itemView.findViewById(R.id.postFare);
        postTime = itemView.findViewById(R.id.postTime);
        }

    }
    private String getTimestampDifference(AddPacakgeAttr addPacakgeAttr)
    {

        long difference = addPacakgeAttr.getTime();
        String result="";

        result=TimeCheck.getTimeAgo(difference);
        return  result;

    }
}