package com.fm.scheduling.exception;

import java.util.ArrayList;
import java.util.List;

public class SchedulingException extends Exception {

    public enum SchedulingExceptionTypeEnum {
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
