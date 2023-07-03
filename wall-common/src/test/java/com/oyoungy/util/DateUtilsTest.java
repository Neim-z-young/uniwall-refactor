package com.oyoungy.util;


import org.junit.jupiter.api.Test;

import java.util.Date;


public class DateUtilsTest {

    @Test
    public void getEpochFromDate() {
        Integer epoch = DateUtils.getEpochFromDate(new Date());
        System.out.println(epoch);
        System.out.println(DateUtils.getDateFromEpoch(epoch));
    }
}