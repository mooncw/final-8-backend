package com.fastcampus.befinal.common.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class Generator {
    public static String generate(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generateOnlyNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String generateUniqueValue() {
        return UUID.randomUUID().toString();
    }
}
