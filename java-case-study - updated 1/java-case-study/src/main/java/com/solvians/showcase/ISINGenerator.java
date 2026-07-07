package com.solvians.showcase;

import java.util.concurrent.ThreadLocalRandom;

public class ISINGenerator {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHANUMERIC = LETTERS + "0123456789";

    public static String generate() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder isin = new StringBuilder(11);
        for (int i = 0; i < 2; i++) {
            isin.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        for (int i = 0; i < 9; i++) {
            isin.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }
        String body = isin.toString();
        return body + calculateCheckDigit(body);
    }

    public static int calculateCheckDigit(String isin) {
        StringBuilder numeric = new StringBuilder();
        for (char c : isin.toCharArray()) {
            if (Character.isDigit(c)) {
                numeric.append(c);
            } else {
                numeric.append(Character.toUpperCase(c) - 'A' + 10);
            }
        }

        int sum = 0;
        boolean doubleDigit = true;
        for (int i = numeric.length() - 1; i >= 0; i--) {
            int digit = numeric.charAt(i) - '0';
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }

        int remainder = sum % 10;
        return remainder == 0 ? 0 : 10 - remainder;
    }
}
