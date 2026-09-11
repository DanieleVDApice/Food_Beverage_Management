package com.example.FoodBeverageManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(
            "Mozzarella",
            10.0f,
            2.5f,
            "BATCH001",
            LocalDate.of(2026, 1, 1),
            LocalDate.of(2026, 2, 1),
            false
        );
    }

    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================

    @Nested
    @DisplayName("Constructor")
    class ConstructorTest {

        @Test
        @DisplayName("Correctly stores the name")
        void storesTheName() {
            assertEquals("Mozzarella", product.getName());
        }

        @Test
        @DisplayName("Correctly stores the quantity")
        void storesTheQuantity() {
            assertEquals(10.0f, product.getQuantity());
        }

        @Test
        @DisplayName("Correctly stores the price")
        void storesThePrice() {
            assertEquals(2.5f, product.getPrice());
        }

        @Test
        @DisplayName("Correctly stores the batch number")
        void storesTheBatchNumber() {
            assertEquals("BATCH001", product.getBatchNumber());
        }

        @Test
        @DisplayName("Correctly stores the dates")
        void storesTheDates() {
            assertEquals(LocalDate.of(2026, 1, 1), product.getBoughtDate());
            assertEquals(LocalDate.of(2026, 2, 1), product.getExpiryDate());
        }

        @Test
        @DisplayName("Correctly stores the expired state")
        void storesTheExpiredState() {
            assertFalse(product.isExpired());
        }

        @Test
        @DisplayName("Accepts a null batchNumber")
        void acceptsNullBatchNumber() {
            Product p = new Product("Milk", 5f, 1.2f, null,
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 3, 1), false);
            assertNull(p.getBatchNumber());
        }
    }

    // =========================================================================
    // GETTERS / SETTERS
    // =========================================================================

    @Nested
    @DisplayName("Getters and Setters")
    class SetterTest {

        @Test
        void setNameChangesTheName() {
            product.setName("Parmesan");
            assertEquals("Parmesan", product.getName());
        }

        @Test
        void setQuantityChangesTheQuantity() {
            product.setQuantity(20.0f);
            assertEquals(20.0f, product.getQuantity());
        }

        @Test
        void setPriceChangesThePrice() {
            product.setPrice(5.0f);
            assertEquals(5.0f, product.getPrice());
        }

        @Test
        void setBatchNumberChangesTheBatch() {
            product.setBatchNumber("BATCH002");
            assertEquals("BATCH002", product.getBatchNumber());
        }

        @Test
        void setBoughtDateChangesTheDate() {
            LocalDate newDate = LocalDate.of(2026, 5, 10);
            product.setBoughtDate(newDate);
            assertEquals(newDate, product.getBoughtDate());
        }

        @Test
        void setExpiryDateChangesTheDate() {
            LocalDate newDate = LocalDate.of(2026, 6, 15);
            product.setExpiryDate(newDate);
            assertEquals(newDate, product.getExpiryDate());
        }

        @Test
        void setExpiredChangesTheState() {
            product.setExpired(true);
            assertTrue(product.isExpired());
        }

        @Test
        @DisplayName("Quantity can be set to zero")
        void quantityCanBeZero() {
            product.setQuantity(0f);
            assertEquals(0f, product.getQuantity());
        }
    }

    // =========================================================================
    // parseName
    // =========================================================================

    @Nested
    @DisplayName("parseName")
    class ParseNameTest {

        @Test
        void acceptsAValidName() {
            assertEquals("Tomato", Product.parseName("Tomato"));
        }

        @Test
        void trimsLeadingAndTrailingWhitespace() {
            assertEquals("Tomato", Product.parseName("  Tomato  "));
        }

        @Test
        void throwsExceptionForEmptyString() {
            assertThrows(IllegalArgumentException.class, () -> Product.parseName(""));
        }

        @Test
        void throwsExceptionForBlankString() {
            assertThrows(IllegalArgumentException.class, () -> Product.parseName("   "));
        }

        @Test
        void throwsExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> Product.parseName(null));
        }
    }

    // =========================================================================
    // parseQuantity
    // =========================================================================

    @Nested
    @DisplayName("parseQuantity")
    class ParseQuantityTest {

        @ParameterizedTest
        @CsvSource({
            "10, 10.0",
            "0, 0.0",
            "3.5, 3.5",
            "100.25, 100.25"
        })
        void correctlyConvertsValidValues(String input, float expected) {
            assertEquals(expected, Product.parseQuantity(input));
        }

        @Test
        void throwsExceptionForNegativeValue() {
            assertThrows(IllegalArgumentException.class, () -> Product.parseQuantity("-5"));
        }

        @Test
        void throwsExceptionForNonNumericText() {
            assertThrows(NumberFormatException.class, () -> Product.parseQuantity("abc"));
        }
    }

    // =========================================================================
    // parsePrice
    // =========================================================================

    @Nested
    @DisplayName("parsePrice")
    class ParsePriceTest {

        @ParameterizedTest
        @ValueSource(strings = {"0", "1.5", "99.99", "1000"})
        void acceptsValidValues(String input) {
            assertDoesNotThrow(() -> Product.parsePrice(input));
        }

        @Test
        void throwsExceptionForNegativeValue() {
            assertThrows(IllegalArgumentException.class, () -> Product.parsePrice("-1.0"));
        }

        @Test
        void throwsExceptionForNonNumericText() {
            assertThrows(NumberFormatException.class, () -> Product.parsePrice("free"));
        }
    }

    // =========================================================================
    // parseBatchNumber
    // =========================================================================

    @Nested
    @DisplayName("parseBatchNumber")
    class ParseBatchNumberTest {

        @Test
        void convertsToUpperCase() {
            assertEquals("ABC123", Product.parseBatchNumber("abc123"));
        }

        @Test
        void returnsNullWhenInputIsNull() {
            assertNull(Product.parseBatchNumber(null));
        }

        @Test
        void handlesAlreadyUpperCaseString() {
            assertEquals("XYZ", Product.parseBatchNumber("XYZ"));
        }
    }

    // =========================================================================
    // parseDate
    // =========================================================================

    @Nested
    @DisplayName("parseDate")
    class ParseDateTest {

        @Test
        void correctlyConvertsAValidDate() {
            assertEquals(LocalDate.of(2026, 3, 15), Product.parseDate("15/03/2026"));
        }

        @Test
        void throwsExceptionForWrongFormat() {
            assertThrows(DateTimeParseException.class, () -> Product.parseDate("2026-03-15"));
        }

        @Test
        @DisplayName("Rejects February 30th (month without that day)")
        void throwsExceptionForNonExistentDate() {
            assertThrows(DateTimeParseException.class, () -> Product.parseDate("30/02/2026"));
        }

        @Test
        @DisplayName("Rejects April 31st (30-day month)")
        void throwsExceptionForAnotherNonExistentDate() {
            assertThrows(DateTimeParseException.class, () -> Product.parseDate("31/04/2026"));
        }

        @Test
        void throwsExceptionForEmptyString() {
            assertThrows(DateTimeParseException.class, () -> Product.parseDate(""));
        }
    }

    // =========================================================================
    // parseExpired
    // =========================================================================

    @Nested
    @DisplayName("parseExpired")
    class ParseExpiredTest {

        @Test
        void correctlyConvertsTrue() {
            assertTrue(Product.parseExpired("true"));
        }

        @Test
        void correctlyConvertsFalse() {
            assertFalse(Product.parseExpired("false"));
        }

        @Test
        @DisplayName("Unrecognized text is interpreted as false")
        void unrecognizedTextIsFalse() {
            assertFalse(Product.parseExpired("maybe"));
        }
    }

    // =========================================================================
    // isExpiredOn
    // =========================================================================

    @Nested
    @DisplayName("isExpiredOn")
    class IsExpiredOnTest {

        @Test
        @DisplayName("Not expired when the expiry date is in the future")
        void notExpiredWhenDateIsInTheFuture() {
            LocalDate expiry = LocalDate.of(2026, 12, 31);
            LocalDate today = LocalDate.of(2026, 1, 1);
            assertFalse(Product.isExpiredOn(expiry, today));
        }

        @Test
        @DisplayName("Expired when the expiry date is in the past")
        void expiredWhenDateIsInThePast() {
            LocalDate expiry = LocalDate.of(2026, 1, 1);
            LocalDate today = LocalDate.of(2026, 6, 1);
            assertTrue(Product.isExpiredOn(expiry, today));
        }

        @Test
        @DisplayName("Expired when the expiry date is today (edge case)")
        void expiredWhenExpiryDateIsToday() {
            LocalDate expiry = LocalDate.of(2026, 6, 1);
            LocalDate today = LocalDate.of(2026, 6, 1);
            assertTrue(Product.isExpiredOn(expiry, today));
        }

        @Test
        void throwsExceptionWhenExpiryDateIsNull() {
            assertThrows(IllegalArgumentException.class,
                () -> Product.isExpiredOn(null, LocalDate.now()));
        }

        @Test
        void throwsExceptionWhenReferenceDateIsNull() {
            assertThrows(IllegalArgumentException.class,
                () -> Product.isExpiredOn(LocalDate.now(), null));
        }
    }

    // =========================================================================
    // toString
    // =========================================================================

    @Nested
    @DisplayName("toString")
    class ToStringTest {

        @Test
        void containsTheProductName() {
            assertTrue(product.toString().contains("Mozzarella"));
        }

        @Test
        void containsTheExpiredState() {
            assertTrue(product.toString().contains("Expired: false"));
        }

        @Test
        void containsTheExpiryDate() {
            assertTrue(product.toString().contains("2026-02-01"));
        }
    }
}