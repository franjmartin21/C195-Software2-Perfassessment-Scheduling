package com.fm.scheduling.util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Locale;

public class UtilMessagesTest {

    private UtilMessages utilMessages;

    @Test
    public void testDefaultLocation(){
        System.out.println(Locale.getDefault());
    }

    @Ignore
    @Test
    public void testMessageSpanish_success(){
        Locale.setDefault(new Locale("es", "ES"));
        utilMessages = UtilMessages.getInstance();
        String message = utilMessages.getMessageByKey("abc.test");
        Assert.assertNotNull(message);
        System.out.println(message);
        Assert.assertTrue(message.contains("español"));
    }

    @Test
    public void testMessageEnglish_success(){
        utilMessages = UtilMessages.getInstance();
        String message = utilMessages.getMessageByKey("abc.test");
        Assert.assertNotNull(message);
        Assert.assertTrue(message.contains("english"));
    }
}
