package com.fm.scheduling.domain;

public class Address extends BaseRecord {

    private int addressId;

    private String address;

    private String address2;

    private int cityId;

    private City city;

    private String postalCode;

    private String phone;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public City getCity() {
        return city;
    }

    public String getCityName(){
        return city != null? city.getCity(): null;
    }

    public String getCountryName(){
        return city != null? city.getCountryName(): null;
    }

    public void setCity(City city) {
        this.city = city;
        this.cityId = city.getCityId();
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
