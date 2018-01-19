package com.fm.scheduling.util;

import com.fm.scheduling.domain.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UtilLog {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private static UtilLog instance;

    private final static String PROPERTY_FILE = "config";

    private File file;

    private UtilLog(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTY_FILE);
        file = new File(resourceBundle.getString("log.path"));
    }

    public void append(User user) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.write(dateTimeFormatter.format(LocalDateTime.now()) + " - Logged in user:"  + user.getUserName());
        bw.newLine();
        bw.close();
    }

    public static synchronized UtilLog getInstance(){
        if(instance == null) instance = new UtilLog();

        return instance;
    }
}
