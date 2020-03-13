package com.coder.applicationhostel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hanzalah on 2/13/2019.
 */

public class bookinglist extends ArrayAdapter<Booking_Attr> {

    private Activity context;
    private List<Booking_Attr> booking_attrs;
    //constructor
    public bookinglist(Activity context , List<Booking_Attr> booking_attrs){

        super(context , R.layout.booking_list , booking_attrs);
        this.context = context;
        this.booking_attrs = booking_attrs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.booking_list , null , true);

        TextView sName = (TextView) listViewItem.findViewById(R.id.txtSName);
        TextView contact = (TextView) listViewItem.findViewById(R.id.txtContact);
        TextView status = (TextView) listViewItem.findViewById(R.id.txtStatus);
        TextView time = (TextView) listViewItem.findViewById(R.id.txtTime);
        //hostel admin ka constructor
        Booking_Attr booking_attr = booking_attrs.get(position);
        sName.setText(booking_attr.getStudentName());
        status.setText(booking_attr.getStatus());
        contact.setText(booking_attr.getStudentContact());
        String timestampDifference = getTimestampDifference(booking_attrs.get(position));
        time.setText(timestampDifference);
        return listViewItem;
    }
    private String getTimestampDifference(Booking_Attr booking_attrs)
    {

        long difference = booking_attrs.getTime();
        String result="";

        result=TimeCheck.getTimeAgo(difference);
        return  result;

    }
}
