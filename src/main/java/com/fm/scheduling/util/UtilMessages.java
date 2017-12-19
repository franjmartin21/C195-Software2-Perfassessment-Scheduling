package com.fm.scheduling.util;

import com.fm.scheduling.exception.SchedulingException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by francisco on 5/25/17.
 */
public class UtilMessages {

    private final static String PROPERTY_FILE = "message";

    private enum LocalesSupported{
        en(new Locale("en")),
        es(new Locale("es"));

        LocalesSupported(Locale locale){
            this.locale = locale;
        }

        private Locale locale;

        public Locale getLocale() {
            return locale;
        }
    }

    private final static String MESSAGE_STR_PREFIX = "exception.";

    private static UtilMessages instance;

    private ResourceBundle resourceBundle;

    private UtilMessages(String locale){
        resourceBundle = ResourceBundle.getBundle(PROPERTY_FILE, LocalesSupported.valueOf(locale).getLocale());
    }

    public static synchronized UtilMessages getInstance(String locale){
        if(instance == null) instance = new UtilMessages(locale);

        return instance;
    }

    public String getMessageByKey(String key){
        try {
            String value = resourceBundle.getString(key);
            return new String(value.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getMessageBySchedulingException(SchedulingException.SchedulingExceptionTypeEnum typeEnum){
        return getMessageByKey(MESSAGE_STR_PREFIX + String.valueOf(typeEnum));
    }

    public String getMessageBySchedulingException(SchedulingException ex){
        return getMessageByKey(MESSAGE_STR_PREFIX + String.valueOf(ex.getSchedulingExceptionTypeEnumList().get(0)));
    }

    public List<String> getMessageListBySchedulingException(SchedulingException ex){
        if(ex == null || ex.getSchedulingExceptionTypeEnumList() == null ||  ex.getSchedulingExceptionTypeEnumList().isEmpty());

        List<String> messageList = new ArrayList<String>();
        for(SchedulingException.SchedulingExceptionTypeEnum schedulingExceptionTypeEnum: ex.getSchedulingExceptionTypeEnumList()){
            messageList.add(getMessageByKey(MESSAGE_STR_PREFIX + String.valueOf(schedulingExceptionTypeEnum)));
        }
        return messageList;
    }
}
