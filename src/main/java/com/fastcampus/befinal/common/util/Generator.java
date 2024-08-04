package com.fastcampus.befinal.common.util;

import org.apache.commons.lang3.RandomStringUtils;

public class Generator {
    public static String generate(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
