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
            "Salmone",
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
    // COSTRUTTORE (verifico anche i campi ereditati da Product)
    // =========================================================================

    @Nested
    @DisplayName("Costruttore")
    class CostruttoreTest {

        @Test
        void salvaLaCategoria() {
            assertEquals(EnumCategory.FISH, food.getCategory());
        }

        @Test
        void salvaLoStatoDelCibo() {
            assertEquals(EnumFoodState.FRESH, food.getFoodState());
        }

        @Test
        @DisplayName("Salva correttamente anche i campi ereditati da Product")
        void salvaICampiEreditati() {
            assertEquals("Salmone", food.getName());
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
        @DisplayName("Accetta tutti i valori validi dell'enum")
        void accettaValoriValidi(String valore) {
            assertEquals(EnumCategory.valueOf(valore), Food.parseCategory(valore));
        }

        @Test
        @DisplayName("È case-insensitive")
        void eCaseInsensitive() {
            assertEquals(EnumCategory.MEAT, Food.parseCategory("meat"));
            assertEquals(EnumCategory.MEAT, Food.parseCategory("MeAt"));
        }

        @Test
        @DisplayName("Ignora spazi bianchi ai lati")
        void ignoraSpaziAiLati() {
            assertEquals(EnumCategory.FRUIT, Food.parseCategory("  fruit  "));
        }

        @Test
        void lanciaEccezioneConValoreNonValido() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseCategory("PIZZA"));
        }

        @Test
        void lanciaEccezioneConStringaVuota() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseCategory(""));
        }

        @Test
        void lanciaEccezioneConNull() {
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
        @DisplayName("Accetta tutti i valori validi dell'enum")
        void accettaValoriValidi(String valore) {
            assertEquals(EnumFoodState.valueOf(valore), Food.parseFoodState(valore));
        }

        @Test
        @DisplayName("È case-insensitive")
        void eCaseInsensitive() {
            assertEquals(EnumFoodState.FROZEN, Food.parseFoodState("frozen"));
        }

        @Test
        @DisplayName("Ignora spazi bianchi ai lati")
        void ignoraSpaziAiLati() {
            assertEquals(EnumFoodState.CANNED, Food.parseFoodState("  canned  "));
        }

        @Test
        void lanciaEccezioneConValoreNonValido() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseFoodState("ROTTEN"));
        }

        @Test
        void lanciaEccezioneConStringaVuota() {
            assertThrows(IllegalArgumentException.class, () -> Food.parseFoodState(""));
        }

        @Test
        void lanciaEccezioneConNull() {
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
        void contieneLaCategoria() {
            assertTrue(food.toString().contains("FISH"));
        }

        @Test
        void contieneLoStatoDelCibo() {
            assertTrue(food.toString().contains("FRESH"));
        }

        @Test
        @DisplayName("Contiene anche le informazioni ereditate da Product (nome)")
        void contieneInfoEreditate() {
            assertTrue(food.toString().contains("Salmone"));
        }
    }
}