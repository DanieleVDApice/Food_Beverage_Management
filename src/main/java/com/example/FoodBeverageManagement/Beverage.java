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

	public static EnumContainer setContainer() {
		Scanner scanner = new Scanner(System.in);
		EnumContainer container = null;

		while (container == null) {
			System.out.print("Container (BOTTLE, CAN, TAP): ");
			String inputContainer = scanner.nextLine().toUpperCase();

			if (inputContainer.equals("BOTTLE") || inputContainer.equals("CAN") || inputContainer.equals("TAP")) {

				container = EnumContainer.valueOf(inputContainer);

			} else {
				System.out.println("Error: insert BOTTLE, CAN or TAP.");
			}
		}
		return container;
	}

	public static Beverage createBeverage() {
		Product product = createProduct();
		EnumContainer container = setContainer();

		return new Beverage(product.getName(), product.getQuantity(), product.getPrice(), product.getBatchNumber(),
				product.getBoughtDate(), product.getExpiryDate(), product.isExpired(), container);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("\n");
		sb.append("Container: ").append(getContainer()).append(".");
		return sb.toString();
	}
}
