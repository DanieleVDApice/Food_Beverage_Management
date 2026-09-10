package com.example.FoodBeverageManagement;

import java.time.LocalDate;
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

	public EnumFoodState getFoodState() {
		return foodState;
	}

	
	public static EnumCategory parseCategory(String input) {
		if (input == null) {
			throw new IllegalArgumentException("Category cannot be null");
		}
		try {
			return EnumCategory.valueOf(input.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid category: " + input, e);
		}
	}

	public static EnumFoodState parseFoodState(String input) {
		if (input == null) {
			throw new IllegalArgumentException("Food state cannot be null");
		}
		try {
			return EnumFoodState.valueOf(input.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid food state: " + input, e);
		}
	}

	
	public static EnumCategory setCategory() {
		Scanner scanner = new Scanner(System.in);
		EnumCategory category = null;

		while (category == null) {
			System.out.print("Category (MEAT, FISH, DAIRY, VEGETABLE, FRUIT, GRAIN, LEGUME, EGG, SWEET): ");
			String inputCategory = scanner.nextLine();
			try {
				category = parseCategory(inputCategory);
			} catch (IllegalArgumentException e) {
				System.out.println("Error: insert MEAT, FISH, DAIRY, VEGETABLE, FRUIT, GRAIN, LEGUME, EGG or SWEET.");
			}
		}
		return category;
	}

	public static EnumFoodState setFoodState() {
		Scanner scanner = new Scanner(System.in);
		EnumFoodState foodState = null;

		while (foodState == null) {
			System.out.print("Food state (FRESH, CANNED, PACKAGED, FROZEN): ");
			String inputState = scanner.nextLine();
			try {
				foodState = parseFoodState(inputState);
			} catch (IllegalArgumentException e) {
				System.out.println("Error: insert FRESH, CANNED, PACKAGED or FROZEN.");
			}
		}
		return foodState;
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