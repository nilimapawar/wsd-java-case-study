package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateUpdateTest {

    private static final Pattern LINE_PATTERN = Pattern.compile(
            "\\d+,[A-Z]{2}[A-Z0-9]{9}\\d,\\d{3}\\.\\d{2},\\d+,\\d{3}\\.\\d{2},\\d+");

    @Test
    public void toString_matchesExpectedCsvFormat() {
        CertificateUpdate update = new CertificateUpdate();

        assertTrue(LINE_PATTERN.matcher(update.toString()).matches(), update.toString());
    }

    @Test
    public void generatedFields_areWithinSpecRanges() {
        CertificateUpdate update = new CertificateUpdate();

        assertTrue(update.getBidPrice() >= 100.00 && update.getBidPrice() <= 200.00);
        assertTrue(update.getAskPrice() >= 100.00 && update.getAskPrice() <= 200.00);
        assertTrue(update.getBidSize() >= 1_000 && update.getBidSize() <= 5_000);
        assertTrue(update.getAskSize() >= 1_000 && update.getAskSize() <= 10_000);
        assertTrue(ISINGenerator.calculateCheckDigit(update.getIsin().substring(0, 11))
                == Character.getNumericValue(update.getIsin().charAt(11)));
    }
}
