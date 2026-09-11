package com.example.FoodBeverageManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    private Food food;

    @BeforeEach
    void setUp() {
        food = new Food(
            "Salmon",
            2.0f,
            15.0f,
            "BATCH010",
            LocalDate.of(2026, 1, 1),
            LocalDate.of(2026, 1, 10),
            false,
            EnumCategory.FISH,
            EnumFoodState.FRESH
        );
    }

    // =========================================================================
    // CONSTRUCTOR (also verifies fields inherited from Product)
    // =========================================================================

    @Nested
    @DisplayName("Constructor")
    class ConstructorTest {

        @Test
        void storesTheCategory() {
            assertEquals(EnumCategory.FISH, food.getCategory());
        }

        @Test
        void storesTheFoodState() {
            assertEquals(EnumFoodState.FRESH, food.getFoodState());
        }

        @Test
        @DisplayName("Also correctly stores the fields inherited from Product")
        void storesTheInheritedFields() {
            assertEquals("Salmon", food.getName());
            assertEquals(2.0f, food.getQuantity());
            assertEquals(15.0f, food.getPrice());
            assertEquals("BATCH010", food.getBatchNumber());
        }
    }

    // =========================================================================
    // parseCategory
    // =========================================================================

    @Nested
    @DisplayName("parseCategory")
    class ParseCategoryTest {

        @ParameterizedTest
        @ValueSource(strings = {
            "MEAT", "FISH", "DAIRY", "VEGETABLE", "FRUIT", "GRAIN", "LEGUME", "EGG", "SWEET"
        })
        @DisplayName("Accepts all valid enum values")
        void acceptsValidValues(String value) {
            assertEquals(EnumCategory.valueOf(value), Food.parseCategory(value));
        }

        @Test
        @DisplayName("Is case-insensitive")
        void isCaseInsensitive() {
            assertEquals(EnumCategory.MEAT, Food.parseCategory("meat"));
            assertEquals(EnumCategory.MEAT, Food.parseCategory("MeAt"));
        }

        @Test
        @DisplayName("Ignores leading and trailing whitespace")
        void ignoresLeadingAndTrailingWhitespace() {
            assertEquals(EnumCategory.FRUIT, Food.parseCategory("  fruit  "));
        }

        @Test
        void throwsExceptionForInvalidValue() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseCategory("PIZZA"));
        }

        @Test
        void throwsExceptionForEmptyString() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseCategory(""));
        }

        @Test
        void throwsExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseCategory(null));
        }
    }

    // =========================================================================
    // parseFoodState
    // =========================================================================

    @Nested
    @DisplayName("parseFoodState")
    class ParseFoodStateTest {

        @ParameterizedTest
        @ValueSource(strings = {"FRESH", "CANNED", "PACKAGED", "FROZEN"})
        @DisplayName("Accepts all valid enum values")
        void acceptsValidValues(String value) {
            assertEquals(EnumFoodState.valueOf(value), Food.parseFoodState(value));
        }

        @Test
        @DisplayName("Is case-insensitive")
        void isCaseInsensitive() {
            assertEquals(EnumFoodState.FROZEN, Food.parseFoodState("frozen"));
        }

        @Test
        @DisplayName("Ignores leading and trailing whitespace")
        void ignoresLeadingAndTrailingWhitespace() {
            assertEquals(EnumFoodState.CANNED, Food.parseFoodState("  canned  "));
        }

        @Test
        void throwsExceptionForInvalidValue() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseFoodState("ROTTEN"));
        }

        @Test
        void throwsExceptionForEmptyString() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseFoodState(""));
        }

        @Test
        void throwsExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseFoodState(null));
        }
    }

    // =========================================================================
    // toString
    // =========================================================================

    @Nested
    @DisplayName("toString")
    class ToStringTest {

        @Test
        void containsTheCategory() {
            assertTrue(food.toString().contains("FISH"));
        }

        @Test
        void containsTheFoodState() {
            assertTrue(food.toString().contains("FRESH"));
        }

        @Test
        @DisplayName("Also contains information inherited from Product (name)")
        void containsInheritedInfo() {
            assertTrue(food.toString().contains("Salmon"));
        }
    }
}