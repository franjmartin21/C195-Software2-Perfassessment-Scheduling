package com.fm.scheduling.service;

public class SchedulingService {

    private static SchedulingService instance;

    public static synchronized SchedulingService getInstance(){
        if(instance == null) instance = new SchedulingService();

        return instance;
    }
}
