package com.example.FoodBeverageManagement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    // Small helpers to keep test bodies focused on behavior, not object wiring.

    private Food newFood(float quantity, LocalDate expiryDate) {
        return new Food("Chicken Breast", quantity, 8.0f, "BATCHF01",
            LocalDate.now(), expiryDate, false, EnumCategory.MEAT, EnumFoodState.FRESH);
    }

    private Beverage newBeverage(float quantity, LocalDate expiryDate) {
        return new Beverage("Sparkling Water", quantity, 1.0f, "BATCHB01",
            LocalDate.now(), expiryDate, false, EnumContainer.BOTTLE);
    }

    // =========================================================================
    // addProduct
    // =========================================================================

    @Nested
    @DisplayName("addProduct")
    class AddProductTest {

        @Test
        void throwsExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> inventory.addProduct(null));
        }

        @Test
        @DisplayName("Adding a Food makes it findable in the inventory")
        void addingFoodMakesItFindable() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);
            assertTrue(inventory.findProduct(food));
        }

        @Test
        @DisplayName("Adding a Beverage makes it findable in the inventory")
        void addingBeverageMakesItFindable() {
            Beverage beverage = newBeverage(3f, LocalDate.now().plusDays(10));
            inventory.addProduct(beverage);
            assertTrue(inventory.findProduct(beverage));
        }

        @Test
        @DisplayName("A plain Product (neither Food nor Beverage) is rejected")
        void plainProductIsRejected() {
            Product plainProduct = new Product("Generic Item", 1f, 1f, "B1",
                LocalDate.now(), LocalDate.now().plusDays(5), false);
            assertThrows(IllegalArgumentException.class, () -> inventory.addProduct(plainProduct));
        }
    }

    // =========================================================================
    // findProduct
    // =========================================================================

    @Nested
    @DisplayName("findProduct")
    class FindProductTest {

        @Test
        void returnsFalseWhenProductWasNeverAdded() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            assertFalse(inventory.findProduct(food));
        }

        @Test
        @DisplayName("Returns false for a different object even with identical field values")
        void returnsFalseForADifferentEqualLookingObject() {

            LocalDate expiry = LocalDate.now().plusDays(10);
            Food original = newFood(5f, expiry);
            Food lookAlike = newFood(5f, expiry);

            inventory.addProduct(original);

            assertFalse(inventory.findProduct(lookAlike));
        }
    }

    // =========================================================================
    // removeProduct
    // =========================================================================

    @Nested
    @DisplayName("removeProduct")
    class RemoveProductTest {

        @Test
        void throwsExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> inventory.removeProduct(null));
        }

        @Test
        void throwsExceptionWhenProductNotInInventory() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            assertThrows(IllegalArgumentException.class, () -> inventory.removeProduct(food));
        }

        @Test
        @DisplayName("Removes a previously added Food")
        void removesAPreviouslyAddedFood() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);

            inventory.removeProduct(food);

            assertFalse(inventory.findProduct(food));
        }

        @Test
        @DisplayName("Removes a previously added Beverage")
        void removesAPreviouslyAddedBeverage() {
            Beverage beverage = newBeverage(3f, LocalDate.now().plusDays(10));
            inventory.addProduct(beverage);

            inventory.removeProduct(beverage);

            assertFalse(inventory.findProduct(beverage));
        }
    }

    // =========================================================================
    // increaseProduct
    // =========================================================================

    @Nested
    @DisplayName("increaseProduct")
    class IncreaseProductTest {

        @Test
        void throwsExceptionForNullProduct() {
            assertThrows(IllegalArgumentException.class, () -> inventory.increaseProduct(5, null));
        }

        @Test
        void throwsExceptionForZeroQuantity() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);
            assertThrows(IllegalArgumentException.class, () -> inventory.increaseProduct(0, food));
        }

        @Test
        void throwsExceptionForNegativeQuantity() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);
            assertThrows(IllegalArgumentException.class, () -> inventory.increaseProduct(-3, food));
        }

        @Test
        void throwsExceptionWhenProductNotInInventory() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            assertThrows(IllegalArgumentException.class, () -> inventory.increaseProduct(2, food));
        }

        @Test
        @DisplayName("Increases a Food's quantity by exactly the given amount")
        void increasesFoodQuantityByTheGivenAmount() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);

            inventory.increaseProduct(3, food);

            assertEquals(8f, food.getQuantity());
        }

        @Test
        @DisplayName("Increases a Beverage's quantity by exactly the given amount")
        void increasesBeverageQuantityByTheGivenAmount() {
            Beverage beverage = newBeverage(3f, LocalDate.now().plusDays(10));
            inventory.addProduct(beverage);

            inventory.increaseProduct(4, beverage);

            assertEquals(7f, beverage.getQuantity());
        }
    }

    // =========================================================================
    // reduceProduct
    // =========================================================================

    @Nested
    @DisplayName("reduceProduct")
    class ReduceProductTest {

        @Test
        void throwsExceptionForNullProduct() {
            assertThrows(IllegalArgumentException.class, () -> inventory.reduceProduct(1, null));
        }

        @Test
        void throwsExceptionWhenProductNotInInventory() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            assertThrows(IllegalArgumentException.class, () -> inventory.reduceProduct(1, food));
        }

        @Test
        void throwsExceptionForZeroQuantity() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);
            assertThrows(IllegalArgumentException.class, () -> inventory.reduceProduct(0, food));
        }

        @Test
        void throwsExceptionForNegativeQuantity() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);
            assertThrows(IllegalArgumentException.class, () -> inventory.reduceProduct(-1, food));
        }

        @Test
        @DisplayName("Throws ErrorQuantityException when reducing more than available")
        void throwsErrorQuantityExceptionWhenReducingMoreThanAvailable() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);

            assertThrows(Inventory.ErrorQuantityException.class,
                () -> inventory.reduceProduct(10, food));
        }

        @Test
        @DisplayName("Reduces the quantity when reducing a partial amount")
        void reducesQuantityForAPartialAmount() throws Inventory.ErrorQuantityException {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);

            inventory.reduceProduct(2, food);

            assertEquals(3f, food.getQuantity());
            assertTrue(inventory.findProduct(food));
        }

        @Test
        @DisplayName("Removes the product entirely when reducing the exact remaining quantity")
        void removesProductWhenReducingTheExactRemainingQuantity() throws Inventory.ErrorQuantityException {
            Food food = newFood(5f, LocalDate.now().plusDays(10));
            inventory.addProduct(food);

            inventory.reduceProduct(5, food);

            assertFalse(inventory.findProduct(food));
        }
    }

    // =========================================================================
    // checkProduct
    // =========================================================================

    @Nested
    @DisplayName("checkProduct")
    class CheckProductTest {

        @Test
        void throwsExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> inventory.checkProduct(null));
        }

        @Test
        @DisplayName("Marks as expired and returns true when expiry date is in the past")
        void marksAsExpiredWhenDateIsInThePast() {
            Food food = newFood(5f, LocalDate.now().minusDays(3));

            boolean result = inventory.checkProduct(food);

            assertTrue(result);
            assertTrue(food.isExpired());
        }

        @Test
        @DisplayName("Marks as expired and returns true when expiry date is today")
        void marksAsExpiredWhenDateIsToday() {
            Food food = newFood(5f, LocalDate.now());

            boolean result = inventory.checkProduct(food);

            assertTrue(result);
            assertTrue(food.isExpired());
        }

        @Test
        @DisplayName("Marks as not expired and returns false when expiry date is in the future")
        void marksAsNotExpiredWhenDateIsInTheFuture() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));

            boolean result = inventory.checkProduct(food);

            assertFalse(result);
            assertFalse(food.isExpired());
        }
    }

    @Nested
    @DisplayName("getFoods and getBeverages")
    class GetFoodsAndGetBeveragesTest {

        private final PrintStream originalOut = System.out;
        private ByteArrayOutputStream capturedOut;

        @BeforeEach
        void captureSystemOut() {
            capturedOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(capturedOut));
        }

        @AfterEach
        void restoreSystemOut() {
            System.setOut(originalOut);
        }

        @Test
        void throwsExceptionForNullFoodsList() {
            assertThrows(IllegalArgumentException.class, () -> inventory.getFoods(null));
        }

        @Test
        void throwsExceptionForNullBeveragesList() {
            assertThrows(IllegalArgumentException.class, () -> inventory.getBeverages(null));
        }

        @Test
        @DisplayName("Prints an 'is expired' message for a food that IS expired")
        void printsExpiredMessageForAnExpiredFood() {
            Food food = newFood(5f, LocalDate.now().minusDays(2));

            inventory.getFoods(List.of(food));

            assertTrue(capturedOut.toString().contains("is expired"));
        }

        @Test
        @DisplayName("Does not print an 'is expired' message for a food that is NOT expired")
        void doesNotPrintExpiredMessageForANonExpiredFood() {
            Food food = newFood(5f, LocalDate.now().plusDays(10));

            inventory.getFoods(List.of(food));

            assertFalse(capturedOut.toString().contains("is expired"));
        }

        @Test
        @DisplayName("Prints an 'is expired' message for a beverage that IS expired")
        void printsExpiredMessageForAnExpiredBeverage() {
            Beverage beverage = newBeverage(3f, LocalDate.now().minusDays(2));

            inventory.getBeverages(List.of(beverage));

            assertTrue(capturedOut.toString().contains("is expired"));
        }

        @Test
        @DisplayName("Does not print an 'is expired' message for a beverage that is NOT expired")
        void doesNotPrintExpiredMessageForANonExpiredBeverage() {
            Beverage beverage = newBeverage(3f, LocalDate.now().plusDays(10));

            inventory.getBeverages(List.of(beverage));

            assertFalse(capturedOut.toString().contains("is expired"));
        }
    }
}