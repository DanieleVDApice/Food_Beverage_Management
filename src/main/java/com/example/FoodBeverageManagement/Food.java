package com.example.FoodBeverageManagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Food extends Product {

	private EnumCategory category;
	private EnumFoodState foodState;

	public Food(String name, float quantity, float price, String batchNumber, LocalDate boughtDate,
			LocalDate expiryDate, boolean expired, EnumCategory category, EnumFoodState foodState) {
		super(name, quantity, price, batchNumber, boughtDate, expiryDate, expired);

		this.category = category;
		this.foodState = foodState;
	}

	public EnumCategory getCategory() {
		return category;
	}

	public static EnumCategory setCategory() {
		Scanner scanner = new Scanner(System.in);
		EnumCategory category = null;

		while (category == null) {
			System.out.print("Category (MEAT, FISH, DAIRY, VEGETABLE, FRUIT, GRAIN, LEGUME, EGG, SWEET): ");
			String inputCategory = scanner.nextLine().toUpperCase();

			if (inputCategory.equals("MEAT") || inputCategory.equals("FISH") || inputCategory.equals("DAIRY")
					|| inputCategory.equals("VEGETABLE") || inputCategory.equals("FRUIT")
					|| inputCategory.equals("GRAIN") || inputCategory.equals("LEGUME") || inputCategory.equals("EGG")
					|| inputCategory.equals("SWEET")) {

				category = EnumCategory.valueOf(inputCategory);

			} else {
				System.out.println("Error: insert MEAT, FISH, DAIRY, VEGETABLE, FRUIT, GRAIN, LEGUME, EGG or SWEET.");
			}
		}
		return category;
	}

	public EnumFoodState getFoodState() {
		return foodState;
	}

	public static EnumFoodState setFoodState() {
		Scanner scanner = new Scanner(System.in);
		EnumFoodState foodState = null;

		while (foodState == null) {
			System.out.print("Food state (FRESH, CANNED, PACKAGED, FROZEN): ");
			String inputState = scanner.nextLine().toUpperCase();

			if (inputState.equals("FRESH") || inputState.equals("CANNED") || inputState.equals("PACKAGED")
					|| inputState.equals("FROZEN")) {

				foodState = EnumFoodState.valueOf(inputState);

			} else {
				System.out.println("Error: insert FRESH, CANNED, PACKAGED or FROZEN.");
			}
		}
		return foodState;
	}

	public static Food createFood() {
		Product product = createProduct();
		EnumCategory category = setCategory();
		EnumFoodState foodState = setFoodState();

		return new Food(product.getName(), product.getQuantity(), product.getPrice(), product.getBatchNumber(),
				product.getBoughtDate(), product.getExpiryDate(), product.isExpired(), category, foodState);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("\n");
		sb.append("Batch Number: ").append(getBatchNumber()).append(", ");
		sb.append("Bought date: ").append(getBoughtDate()).append(", \n");
		sb.append("Category: ").append(getCategory()).append(", ");
		sb.append("Food State: ").append(getFoodState()).append(".");
		return sb.toString();
	}
}
