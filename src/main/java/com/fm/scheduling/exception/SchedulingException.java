package com.fm.scheduling.exception;

import java.util.ArrayList;
import java.util.List;

public class SchedulingException extends Exception {

    public enum SchedulingExceptionTypeEnum {
        CUSTOMER_APPOINTMENT_NOT_INFORMED,
        TITLE_APPOINTMENT_NOT_INFORMED,
        DESCRIPTION_APPOINTMENT_NOT_INFORMED,
        LOCATION_APPOINTMENT_NOT_INFORMED,
        CONTACT_APPOINTMENT_NOT_INFORMED,
        URL_APPOINTMENT_NOT_INFORMED,
        START_APPOINTMENT_NOT_INFORMED,
        END_APPOINTMENT_NOT_INFORMED,
        START_END_DATE_APPOINTMENT_INCORRECT,
        APPOINTMENT_TOO_LONG,

        CUSTOMER_NAME_NOT_INFORMED,
        ADDRESS_NOT_INFORMED,
        CITY_NOT_INFORMED,
        COUNTRY_NOT_INFORMED,
        DB_CONNECTION_PROBLEM,
        NO_ROW_SELECTED,
        CUSTOMER_ON_APPOINMENT
    }

    private List<SchedulingExceptionTypeEnum> schedulingExceptionTypeEnumList;

    public SchedulingException(){
        super();
        this.schedulingExceptionTypeEnumList = new ArrayList<SchedulingExceptionTypeEnum>();
    }

    public SchedulingException(SchedulingExceptionTypeEnum schedulingExceptionTypeEnum){
        super();
        this.schedulingExceptionTypeEnumList = new ArrayList<SchedulingExceptionTypeEnum>();
        this.schedulingExceptionTypeEnumList.add(schedulingExceptionTypeEnum);
    }

    public void addSchedulingExceptionType(SchedulingExceptionTypeEnum schedulingExceptionTypeEnum){
        this.schedulingExceptionTypeEnumList.add(schedulingExceptionTypeEnum);
    }

    public List<SchedulingExceptionTypeEnum> getSchedulingExceptionTypeEnumList() {
        return schedulingExceptionTypeEnumList;
    }
}
