package com.fm.scheduling.util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class UtilMessagesTest {

    private UtilMessages utilMessages;

    @Ignore
    @Test
    public void testMessageSpanish_success(){
        utilMessages = UtilMessages.getInstance("es");
        String message = utilMessages.getMessageByKey("abc.test");
        Assert.assertNotNull(message);
        System.out.println(message);
        Assert.assertTrue(message.contains("español"));
    }

    @Test
    public void testMessageEnglish_success(){
        utilMessages = UtilMessages.getInstance("en");
        String message = utilMessages.getMessageByKey("abc.test");
        Assert.assertNotNull(message);
        Assert.assertTrue(message.contains("english"));
    }
}
