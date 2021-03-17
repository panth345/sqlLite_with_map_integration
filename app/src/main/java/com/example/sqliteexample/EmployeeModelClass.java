package com.example.sqliteexample;

public class EmployeeModelClass {

    Integer id;
    String name;
    String email;
    String address;

   Double latitude;
    Double longitude;

    public EmployeeModelClass(String name, String email, String address, Double latitude, Double longitude) {
        this.name = name;
        this.email = email;
        this.address = address;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public EmployeeModelClass(Integer id, String name, String email, String address, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;

        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) {
       this.longitude = longitude;
    }

   public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
