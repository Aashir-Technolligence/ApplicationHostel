package com.coder.applicationhostel;

/**
 * Created by Hanzalah on 2/20/2019.
 */

public class AddPacakgeAttr {
    private String id;
    private String Image_url;
    private String PackageType;
    private String Fare;
    private String NoOfSeats;
    private String Parking;
    private String Electricity;
    private String Mess;
    private String Laundary;
    private String Ac;
    private String Cctv;
    private String Internet;
    private String HostelId;
    private long Time;

    public AddPacakgeAttr() {
    }

    public AddPacakgeAttr(String id, String image_url, String packageType, String fare, String noOfSeats, String parking, String electricity, String mess, String laundary, String ac, String cctv, String internet, String hostelId, long time) {
        this.id = id;
        Image_url = image_url;
        PackageType = packageType;
        Fare = fare;
        NoOfSeats = noOfSeats;
        Parking = parking;
        Electricity = electricity;
        Mess = mess;
        Laundary = laundary;
        Ac = ac;
        Cctv = cctv;
        Internet = internet;
        HostelId = hostelId;
        Time = time;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return Image_url;
    }

    public void setImage_url(String image_url) {
        Image_url = image_url;
    }

    public String getPackageType() {
        return PackageType;
    }

    public void setPackageType(String packageType) {
        PackageType = packageType;
    }

    public String getFare() {
        return Fare;
    }

    public void setFare(String fare) {
        Fare = fare;
    }

    public String getNoOfSeats() {
        return NoOfSeats;
    }

    public void setNoOfSeats(String noOfSeats) {
        NoOfSeats = noOfSeats;
    }

    public String getParking() {
        return Parking;
    }

    public void setParking(String parking) {
        Parking = parking;
    }

    public String getElectricity() {
        return Electricity;
    }

    public void setElectricity(String electricity) {
        Electricity = electricity;
    }

    public String getMess() {
        return Mess;
    }

    public void setMess(String mess) {
        Mess = mess;
    }

    public String getLaundary() {
        return Laundary;
    }

    public void setLaundary(String laundary) {
        Laundary = laundary;
    }

    public String getAc() {
        return Ac;
    }

    public void setAc(String ac) {
        Ac = ac;
    }

    public String getCctv() {
        return Cctv;
    }

    public void setCctv(String cctv) {
        Cctv = cctv;
    }

    public String getInternet() {
        return Internet;
    }

    public void setInternet(String internet) {
        Internet = internet;
    }

    public String getHostelId() {
        return HostelId;
    }

    public void setHostelId(String hostelId) {
        HostelId = hostelId;
    }
}
