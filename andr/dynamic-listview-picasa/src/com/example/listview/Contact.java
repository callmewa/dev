package com.example.listview;

public class Contact {

    public static class Bounds {
        static final double LatKm = 111.2;

        public double lat1;
        public double lat2;
        public double lon1;
        public double lon2;

        public Bounds calcBounds(double lat, double lon, double dist){
            lat1 = lat - dist/LatKm;
            lat2 = lat + dist/LatKm;
            lon1 = lon - dist/Math.abs(Math.cos(Math.toRadians(lat))*LatKm);
            lon2 = lon + dist/Math.abs(Math.cos(Math.toRadians(lat))*LatKm);
            return this;
        }
    }
     
    //private variables
    int _id;
    String name;
    String phone;
    Double lat;
    Double lon;
    String address;


    public Contact(int _id, String name, Double lat, Double lon, String address, String phone) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    // Empty constructor
    public Contact(){
         
    }
    // constructor
    public Contact(int id, String name, String phone){
        this._id = id;
        this.name = name;
        this.phone = phone;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // constructor
    public Contact(String name, String phone){
        this.name = name;

        this.phone = phone;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting name
    public String getName(){
        return this.name;
    }
     
    // setting name
    public void setName(String name){
        this.name = name;
    }
     
    // getting phone number
    public String getPhone(){
        return this.phone;
    }
     
    // setting phone number
    public void setPhone(String phone_number){
        this.phone = phone_number;
    }
}