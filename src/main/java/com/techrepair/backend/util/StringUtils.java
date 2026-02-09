package com.techrepair.backend.util;

public class StringUtils {
    public static String sanitize(String s) {
        return s == null ? null : s.trim();
    }

    public static boolean isValidEmail(String email) { return ValidationUtils.isValidEmail(email); }

    public static String slugify(String input) {
        if (input == null) return null;
        return input.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
    }
}
