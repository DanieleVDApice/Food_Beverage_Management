package com.example.FoodBeverageManagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Product {

	private String name;
	private float quantity;
	private float price;
	private String batchNumber;
	private LocalDate boughtDate;
	private LocalDate expiryDate;
	private boolean expired = false;

	public Product(String name, float quantity, float price, String batchNumber, LocalDate boughtDate,
			LocalDate expiryDate, boolean expired) {

		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.batchNumber = batchNumber;
		this.boughtDate = boughtDate;
		this.expiryDate = expiryDate;
		this.expired = expired;
	}

	public String getName() {
		return name;
	}

	public float getQuantity() {
		return quantity;
	}

	public float getPrice() {
		return price;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public LocalDate getBoughtDate() {
		return boughtDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public boolean isExpired() {
		return expired;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public void setBoughtDate(LocalDate boughtDate) {
		this.boughtDate = boughtDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Boolean setExpired(boolean expired) {
		return this.expired = expired;
	}

	
	public static String createName() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Name: ");
		String name = scanner.nextLine();
		return name;
	}

	public static float createQuantity() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Quantity: ");
		float quantity = Float.parseFloat(scanner.nextLine());
		return quantity;
	}

	public static float createPrice() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Price: ");
		float price = Float.parseFloat(scanner.nextLine());
		return price;
	}

	public static String createBatchNumber() {
		Scanner scanner = new Scanner(System.in);
		boolean hasBatchNumber = true;
		String batchNumber;

		if (hasBatchNumber) {
			System.out.println("Batch Number: ");
			batchNumber = scanner.nextLine().toUpperCase();
		} else {
			batchNumber = null;
		}
		return batchNumber;
	}

	public static LocalDate createBoughtDate() {
		Scanner scanner = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		System.out.print("Bought date (yyyy/mm/dd): ");
		LocalDate boughtDate = LocalDate.parse(scanner.nextLine(), formatter);
		return boughtDate;
	}

	public static LocalDate createExpiryDate() {
		Scanner scanner = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		System.out.print("Expiry date (yyyy/mm/dd): ");
		LocalDate expiryDate = LocalDate.parse(scanner.nextLine(), formatter);
		return expiryDate;
	}

	public static Boolean createExpired() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Is it expired? (true/false): ");
		boolean expired = Boolean.parseBoolean(scanner.nextLine());
		return expired;
	}

	public static Product createProduct() {
		String name = createName();
		float quantity = createQuantity();
		float price = createPrice();
		String batchNumber = createBatchNumber();
		LocalDate boughtDate = createBoughtDate();
		LocalDate expiryDate = createExpiryDate();
		boolean expired = createExpired();

		return new Product(name, quantity, price, batchNumber, boughtDate, expiryDate, expired);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Product: ").append(getClass()).append("\n");
		sb.append("Name: ").append(getName()).append(", ");
		sb.append("Quantity: ").append(getQuantity()).append(", ");
		sb.append("Price: ").append(getPrice()).append(",\n");
		sb.append("Expiry date: ").append(getExpiryDate()).append(", ");
		sb.append("Expired: ").append(isExpired()).append(",\n");
		return sb.toString();
	}

}
