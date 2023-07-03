package com.oyoungy.util;


import org.junit.jupiter.api.Test;

public class IPAddressUtilsTest {

    @Test
    public void ipString2Long() {
        System.out.println(IPAddressUtils.ipString2Long("192.168.33.123"));

    }

    @Test
    public void ipLong2String() {
        System.out.println(Long.toBinaryString(3232244091L));
        System.out.println(IPAddressUtils.ipLong2String(3232244091L));
    }

}