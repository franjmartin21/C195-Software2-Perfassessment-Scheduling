package com.fm.scheduling.domain;


public class Customer extends BaseRecord<Customer>{

    private int customerId;

    private String customerName;

    private int addressId;

    private Address address;

    private boolean active;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public String getAddressName(){
        return address != null ? address.getAddress() + " " + address.getAddress2(): null;
    }

    public String getCityName(){
        return address != null? address.getCityName(): null;
    }

    public String getCountryName(){
        return address != null? address.getCountryName(): null;
    }

    public void setAddress(Address address) {
        this.address = address;
        this.addressId = address.getAddressId();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
