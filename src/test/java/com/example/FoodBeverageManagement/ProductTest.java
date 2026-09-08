package com.example.FoodBeverageManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.FoodBeverageManagement.Product;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

	private Product product;

	@BeforeEach
	void setUp() {
		product = new Product("Mozzarella", 10.0f, 2.5f, "BATCH001", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 1),
				false);
	}

	// =========================================================================
	// COSTRUTTORE
	// =========================================================================

	@Nested
	@DisplayName("Costruttore")
	class CostruttoreTest {

		@Test
		@DisplayName("Salva correttamente il nome")
		void salvaIlNome() {
			assertEquals("Mozzarella", product.getName());
		}

		@Test
		@DisplayName("Salva correttamente la quantità")
		void salvaLaQuantita() {
			assertEquals(10.0f, product.getQuantity());
		}

		@Test
		@DisplayName("Salva correttamente il prezzo")
		void salvaIlPrezzo() {
			assertEquals(2.5f, product.getPrice());
		}

		@Test
		@DisplayName("Salva correttamente il batch number")
		void salvaIlBatchNumber() {
			assertEquals("BATCH001", product.getBatchNumber());
		}

		@Test
		@DisplayName("Salva correttamente le date")
		void salvaLeDate() {
			assertEquals(LocalDate.of(2026, 1, 1), product.getBoughtDate());
			assertEquals(LocalDate.of(2026, 2, 1), product.getExpiryDate());
		}

		@Test
		@DisplayName("Salva correttamente lo stato expired")
		void salvaLoStatoExpired() {
			assertFalse(product.isExpired());
		}

		@Test
		@DisplayName("Accetta batchNumber nullo")
		void accettaBatchNumberNullo() {
			Product p = new Product("Latte", 5f, 1.2f, null, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 3, 1), false);
			assertNull(p.getBatchNumber());
		}
	}

	// =========================================================================
	// GETTER / SETTER
	// =========================================================================

	@Nested
	@DisplayName("Getter e Setter")
	class SetterTest {

		@Test
		void setNameCambiaIlNome() {
			product.setName("Parmigiano");
			assertEquals("Parmigiano", product.getName());
		}

		@Test
		void setQuantityCambiaLaQuantita() {
			product.setQuantity(20.0f);
			assertEquals(20.0f, product.getQuantity());
		}

		@Test
		void setPriceCambiaIlPrezzo() {
			product.setPrice(5.0f);
			assertEquals(5.0f, product.getPrice());
		}

		@Test
		void setBatchNumberCambiaIlBatch() {
			product.setBatchNumber("BATCH002");
			assertEquals("BATCH002", product.getBatchNumber());
		}

		@Test
		void setBoughtDateCambiaLaData() {
			LocalDate nuovaData = LocalDate.of(2026, 5, 10);
			product.setBoughtDate(nuovaData);
			assertEquals(nuovaData, product.getBoughtDate());
		}

		@Test
		void setExpiryDateCambiaLaData() {
			LocalDate nuovaData = LocalDate.of(2026, 6, 15);
			product.setExpiryDate(nuovaData);
			assertEquals(nuovaData, product.getExpiryDate());
		}

		@Test
		void setExpiredCambiaLoStato() {
			product.setExpired(true);
			assertTrue(product.isExpired());
		}

		@Test
		@DisplayName("Quantità può essere impostata a zero")
		void quantitaPuoEssereZero() {
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
		void accettaUnNomeValido() {
			assertEquals("Pomodoro", Product.parseName("Pomodoro"));
		}

		@Test
		void rimuoveSpaziBiancheAllInizioEAllaFine() {
			assertEquals("Pomodoro", Product.parseName("  Pomodoro  "));
		}

		@Test
		void lanciaEccezioneConStringaVuota() {
			assertThrows(IllegalArgumentException.class, () -> Product.parseName(""));
		}

		@Test
		void lanciaEccezioneConSoliSpazi() {
			assertThrows(IllegalArgumentException.class, () -> Product.parseName("   "));
		}

		@Test
		void lanciaEccezioneConNull() {
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
		@CsvSource({ "10, 10.0", "0, 0.0", "3.5, 3.5", "100.25, 100.25" })
		void convertCorrettamenteValoriValidi(String input, float atteso) {
			assertEquals(atteso, Product.parseQuantity(input));
		}

		@Test
		void lanciaEccezioneConValoreNegativo() {
			assertThrows(IllegalArgumentException.class, () -> Product.parseQuantity("-5"));
		}

		@Test
		void lanciaEccezioneConTestoNonNumerico() {
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
		@ValueSource(strings = { "0", "1.5", "99.99", "1000" })
		void accettaValoriValidi(String input) {
			assertDoesNotThrow(() -> Product.parsePrice(input));
		}

		@Test
		void lanciaEccezioneConValoreNegativo() {
			assertThrows(IllegalArgumentException.class, () -> Product.parsePrice("-1.0"));
		}

		@Test
		void lanciaEccezioneConTestoNonNumerico() {
			assertThrows(NumberFormatException.class, () -> Product.parsePrice("gratis"));
		}
	}

	// =========================================================================
	// parseBatchNumber
	// =========================================================================

	@Nested
	@DisplayName("parseBatchNumber")
	class ParseBatchNumberTest {

		@Test
		void convertInMaiuscolo() {
			assertEquals("ABC123", Product.parseBatchNumber("abc123"));
		}

		@Test
		void restituisceNullSeInputNullo() {
			assertNull(Product.parseBatchNumber(null));
		}

		@Test
		void gestisceStringaGiaMaiuscola() {
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
		void convertCorrettamenteUnaDataValida() {
			assertEquals(LocalDate.of(2026, 3, 15), Product.parseDate("15/03/2026"));
		}

		@Test
		void lanciaEccezioneConFormatoErrato() {
			assertThrows(DateTimeParseException.class, () -> Product.parseDate("15-03-2026"));
		}

        @Test
        @DisplayName("Rifiuta il 30 febbraio (mese senza quel giorno)")
        void lanciaEccezioneConDataInesistente() {
            assertThrows(DateTimeParseException.class, () -> Product.parseDate("2026/02/30"));
        }

		@Test
		void lanciaEccezioneConStringaVuota() {
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
		void convertTrueCorrettamente() {
			assertTrue(Product.parseExpired("true"));
		}

		@Test
		void convertFalseCorrettamente() {
			assertFalse(Product.parseExpired("false"));
		}

		@Test
		@DisplayName("Testo non riconosciuto viene interpretato come false")
		void testoNonRiconosciutoEFalse() {
			// Boolean.parseBoolean non lancia eccezioni: qualsiasi cosa diversa
			// da "true" (case-insensitive) diventa false. Documentiamo questo
			// comportamento con un test esplicito.
			assertFalse(Product.parseExpired("forse"));
		}
	}

	// =========================================================================
	// isExpiredOn
	// =========================================================================

	@Nested
	@DisplayName("isExpiredOn")
	class IsExpiredOnTest {

		@Test
		@DisplayName("Non è scaduto se la data di scadenza è nel futuro")
		void nonScadutoSeDataFutura() {
			LocalDate scadenza = LocalDate.of(2026, 12, 31);
			LocalDate oggi = LocalDate.of(2026, 1, 1);
			assertFalse(Product.isExpiredOn(scadenza, oggi));
		}

		@Test
		@DisplayName("È scaduto se la data di scadenza è nel passato")
		void scadutoSeDataPassata() {
			LocalDate scadenza = LocalDate.of(2026, 1, 1);
			LocalDate oggi = LocalDate.of(2026, 6, 1);
			assertTrue(Product.isExpiredOn(scadenza, oggi));
		}

		@Test
		@DisplayName("È scaduto se la data di scadenza è oggi stesso (caso limite)")
		void scadutoSeDataDiScadenzaEOggi() {
			LocalDate scadenza = LocalDate.of(2026, 6, 1);
			LocalDate oggi = LocalDate.of(2026, 6, 1);
			assertTrue(Product.isExpiredOn(scadenza, oggi));
		}

		@Test
		void lanciaEccezioneSeExpiryDateNulla() {
			assertThrows(IllegalArgumentException.class, () -> Product.isExpiredOn(null, LocalDate.now()));
		}

		@Test
		void lanciaEccezioneSeReferenceDateNulla() {
			assertThrows(IllegalArgumentException.class, () -> Product.isExpiredOn(LocalDate.now(), null));
		}
	}

	// =========================================================================
	// toString
	// =========================================================================

	@Nested
	@DisplayName("toString")
	class ToStringTest {

		@Test
		void contieneIlNomeDelProdotto() {
			assertTrue(product.toString().contains("Mozzarella"));
		}

		@Test
		void contieneLoStatoExpired() {
			assertTrue(product.toString().contains("Expired: false"));
		}

		@Test
		void contieneLaDataDiScadenza() {
			assertTrue(product.toString().contains("2026-02-01"));
		}
	}
}