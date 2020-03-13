package com.coder.applicationhostel;

/**
 * Created by Hanzalah on 2/12/2019.
 */

public class Hostel_Admin_Attr {
    private String id;
    private String Hostel_Name;
    private String Password;
    private String Contact;
    private String Email;
    private String Hostel_Type;
    private Double LocLongitude;
    private Double LocLatitude;

    public Hostel_Admin_Attr() {
    }

    public Hostel_Admin_Attr(String id, String hostel_Name, String password, String contact, String email, String hostel_Type, Double locLongitude, Double locLatitude) {
        this.id = id;
        Hostel_Name = hostel_Name;
        Password = password;
        Contact = contact;
        Email = email;
        Hostel_Type = hostel_Type;
        LocLongitude = locLongitude;
        LocLatitude = locLatitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostel_Name() {
        return Hostel_Name;
    }

    public void setHostel_Name(String hostel_Name) {
        Hostel_Name = hostel_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHostel_Type() {
        return Hostel_Type;
    }

    public void setHostel_Type(String hostel_Type) {
        Hostel_Type = hostel_Type;
    }

    public Double getLocLongitude() {
        return LocLongitude;
    }

    public void setLocLongitude(Double locLongitude) {
        LocLongitude = locLongitude;
    }

    public Double getLocLatitude() {
        return LocLatitude;
    }

    public void setLocLatitude(Double locLatitude) {
        LocLatitude = locLatitude;
    }
}
