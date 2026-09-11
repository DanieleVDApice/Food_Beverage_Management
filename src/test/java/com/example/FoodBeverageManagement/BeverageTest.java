package com.example.FoodBeverageManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BeverageTest {

    private Beverage beverage;

    @BeforeEach
    void setUp() {
        beverage = new Beverage(
            "Orange Juice",
            1.5f,
            3.0f,
            "BATCH020",
            LocalDate.of(2026, 1, 1),
            LocalDate.of(2026, 3, 1),
            false,
            EnumContainer.BOTTLE
        );
    }

    // =========================================================================
    // CONSTRUCTOR (also verifies fields inherited from Product)
    // =========================================================================

    @Nested
    @DisplayName("Constructor")
    class ConstructorTest {

        @Test
        void storesTheContainer() {
            assertEquals(EnumContainer.BOTTLE, beverage.getContainer());
        }

        @Test
        @DisplayName("Also correctly stores the fields inherited from Product")
        void storesTheInheritedFields() {
            assertEquals("Orange Juice", beverage.getName());
            assertEquals(1.5f, beverage.getQuantity());
            assertEquals(3.0f, beverage.getPrice());
            assertEquals("BATCH020", beverage.getBatchNumber());
            assertFalse(beverage.isExpired());
        }
    }

    // =========================================================================
    // parseContainer
    // =========================================================================

    @Nested
    @DisplayName("parseContainer")
    class ParseContainerTest {

        @ParameterizedTest
        @ValueSource(strings = {"BOTTLE", "CAN", "TAP"})
        @DisplayName("Accepts all valid enum values")
        void acceptsValidValues(String value) {
            assertEquals(EnumContainer.valueOf(value), Beverage.parseContainer(value));
        }

        @Test
        @DisplayName("Is case-insensitive")
        void isCaseInsensitive() {
            assertEquals(EnumContainer.CAN, Beverage.parseContainer("can"));
            assertEquals(EnumContainer.CAN, Beverage.parseContainer("Can"));
        }

        @Test
        @DisplayName("Ignores leading and trailing whitespace")
        void ignoresLeadingAndTrailingWhitespace() {
            assertEquals(EnumContainer.TAP, Beverage.parseContainer("  tap  "));
        }

        @Test
        void throwsExceptionForInvalidValue() {
            assertThrows(IllegalArgumentException.class, () -> Beverage.parseContainer("BOX"));
        }

        @Test
        void throwsExceptionForEmptyString() {
            assertThrows(IllegalArgumentException.class, () -> Beverage.parseContainer(""));
        }

        @Test
        void throwsExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> Beverage.parseContainer(null));
        }
    }

    // =========================================================================
    // toString
    // =========================================================================

    @Nested
    @DisplayName("toString")
    class ToStringTest {

        @Test
        void containsTheContainer() {
            assertTrue(beverage.toString().contains("BOTTLE"));
        }

        @Test
        @DisplayName("Also contains information inherited from Product (name)")
        void containsInheritedInfo() {
            assertTrue(beverage.toString().contains("Orange Juice"));
        }
    }
}