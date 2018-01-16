package com.fm.scheduling.zonedatetime;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZoneDateTimeTest{

    @Test
    public void testDateTime(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt.toLocalTime());

        ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
        System.out.println(ldtZoned.toString());

        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));

        System.out.println(utcZoned.toLocalTime());
    }

}
