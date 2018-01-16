package com.fm.scheduling.domain;

import java.time.LocalDateTime;

public class Appointment extends BaseRecord<Appointment>{

    public final static int MAX_HOURS_LENGHT_APPOINTMENT = 3;

    public enum DescriptionEnum {
        INITIAL_CONSULTATION("Initial Consultation"),
        UPDATE_MEETING("Update meeting"),
        ENDING_CONTRACT("Ending contract");

        private String labelTxt;

        DescriptionEnum(String labelTxt){
            this.labelTxt = labelTxt;
        }

        public String getLabelTxt() {
            return labelTxt;
        }
    }

    public enum LocationEnum{
        PHOENIX("Phoenix - Arizona"),
        NEW_YORK("New York - New York"),
        LONDON("London - England");

        private String labelTxt;

        LocationEnum(String labelTxt){
            this.labelTxt = labelTxt;
        }

        public String getLabelTxt() {
            return labelTxt;
        }

        public static LocationEnum getLocationByLabel(String label){
            for(LocationEnum locationEnum: LocationEnum.values()){
                if(locationEnum.getLabelTxt().equals(label)) return locationEnum;
            }
            return null;
        }
    }


    private int appointmentId;

    private int customerId;

    private Customer customer;

    private String title;

    private DescriptionEnum description;

    private LocationEnum location;

    private String contact;

    private String url;

    private LocalDateTime start;

    private LocalDateTime end;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DescriptionEnum getDescription() {
        return description;
    }

    public void setDescription(DescriptionEnum description) {
        this.description = description;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public void setLocation(LocationEnum location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCustomerName(){
        return customer != null? customer.getCustomerName(): null;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getCustomerId();
    }
}
