package com.coder.applicationhostel;

/**
 * Created by Hanzalah on 3/2/2019.
 */

public class Booking_Attr {
    private String Id ;
    private String HostelName;
    private String StudentName;
    private String HostelId;
    private String Studentid;
    private String StudentContact;
    private String Status;
    private String PackageId;
    private long Time;

    public Booking_Attr() {
    }

    public Booking_Attr(String id, String hostelName, String studentName, String hostelId, String studentid, String studentContact, String status, String packageId, long time) {
        Id = id;
        HostelName = hostelName;
        StudentName = studentName;
        HostelId = hostelId;
        Studentid = studentid;
        StudentContact = studentContact;
        Status = status;
        PackageId = packageId;
        Time = time;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getHostelName() {
        return HostelName;
    }

    public void setHostelName(String hostelName) {
        HostelName = hostelName;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getHostelId() {
        return HostelId;
    }

    public void setHostelId(String hostelId) {
        HostelId = hostelId;
    }

    public String getStudentid() {
        return Studentid;
    }

    public void setStudentid(String studentid) {
        Studentid = studentid;
    }

    public String getStudentContact() {
        return StudentContact;
    }

    public void setStudentContact(String studentContact) {
        StudentContact = studentContact;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPackageId() {
        return PackageId;
    }

    public void setPackageId(String packageId) {
        PackageId = packageId;
    }
}
