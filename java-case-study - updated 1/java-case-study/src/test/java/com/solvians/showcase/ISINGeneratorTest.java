package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ISINGeneratorTest {

    @Test
    public void calculateCheckDigit_matchesReadmeExample() {
        assertEquals(6, ISINGenerator.calculateCheckDigit("DE123456789"));
    }

    @Test
    public void generate_hasCorrectFormat() {
        String isin = ISINGenerator.generate();

        assertEquals(12, isin.length());
        assertTrue(isin.substring(0, 2).matches("[A-Z]{2}"));
        assertTrue(isin.substring(2, 11).matches("[A-Z0-9]{9}"));
        assertTrue(isin.substring(11).matches("[0-9]"));
    }

    @Test
    public void generate_hasValidCheckDigit() {
        String isin = ISINGenerator.generate();

        String body = isin.substring(0, 11);
        int checkDigit = Character.getNumericValue(isin.charAt(11));
        assertEquals(ISINGenerator.calculateCheckDigit(body), checkDigit);
    }
}
