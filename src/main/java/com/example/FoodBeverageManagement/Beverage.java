package com.example.FoodBeverageManagement;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Beverage extends Product {
	private EnumContainer container;

	public Beverage(String name, float quantity, float price, String batchNumber, LocalDate boughtDate,
			LocalDate expiryDate, boolean expired, EnumContainer container) {
		super(name, quantity, price, batchNumber, boughtDate, expiryDate, expired);
		this.container = container;
	}

	public EnumContainer getContainer() {
		return container;
	}

	public static EnumContainer parseContainer(String input) {
		if (input == null) {
			throw new IllegalArgumentException("Container cannot be null");
		}
		try {
			return EnumContainer.valueOf(input.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid container: " + input, e);
		}
	}

	public static EnumContainer setContainer() {
		Scanner scanner = new Scanner(System.in);
		EnumContainer container = null;

		while (container == null) {
			System.out.print("Container (BOTTLE, CAN, TAP): ");
			String inputContainer = scanner.nextLine().toUpperCase();

			try {
				container = parseContainer(inputContainer);
			} catch (IllegalArgumentException e) {
				System.out.println("Error: insert BOTTLE, CAN, TAP");
			}
		}
		return container;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("\n");
		sb.append("Container: ").append(getContainer()).append(".");
		return sb.toString();
	}
}
