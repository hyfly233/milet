package com.hyfly.milet.demo.demo_common.utils;

import com.hyfly.milet.demo.demo_common.exception.UserCheckedException;

import java.util.stream.Stream;

public class CheckUtil {

    private static final String[] NAMES = {"admin", "abc", "123"};

    public static void checkName(String value) {
        Stream.of(NAMES).filter(name -> name.equalsIgnoreCase(value)).findAny().ifPresent(name -> {
            throw new UserCheckedException("name", value);
        });
    }
}
