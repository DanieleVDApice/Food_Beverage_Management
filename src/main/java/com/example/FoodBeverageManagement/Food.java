package com.example.FoodBeverageManagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Food extends Product {
	private String batchNumber;
	private LocalDate boughtDate;
	private LocalDate cookedDate;
	private EnumCategory category;
	private EnumFoodState foodState;

	public Food(String name, float quantity, float price, LocalDate expiryDate, boolean expired, String batchNumber,
			LocalDate boughtDate, LocalDate cookedDate, EnumCategory category, EnumFoodState foodState) {
		super(name, quantity, price, expiryDate, expired);
		this.batchNumber = batchNumber;
		this.boughtDate = boughtDate;
		this.cookedDate = cookedDate;
		this.category = category;
		this.foodState = foodState;
	}
	
	public static Food createFreshFood() {
		Scanner scanner = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		
		System.out.println("Name: ");
		String name = scanner.nextLine();
		
		System.out.print("Quantity: ");
		float quantity = Float.parseFloat(scanner.nextLine());
		
		System.out.print("Price: ");
		float price = Float.parseFloat(scanner.nextLine());
		
		System.out.print("Expiry date (yyyy/mm/dd): ");
		LocalDate expiryDate = LocalDate.parse(scanner.nextLine(), formatter);
		
		System.out.println("Is it expired? (true/false): ");
		boolean expired = Boolean.parseBoolean(scanner.nextLine());
		
		System.out.print("Bought date (yyyy/mm/dd): ");
		LocalDate boughtDate = LocalDate.parse(scanner.nextLine(), formatter);
		
		System.out.print("Category (MEAT, FISH, DAIRY, VEGETABLES, FRUIT, GRAINS, LEGUMES, EGGS, SWEETS): ");
		String inputCategory = scanner.nextLine().toUpperCase();
		EnumCategory category = EnumCategory.valueOf(inputCategory);
		
		System.out.print("Food state (FRESH, CANNED, PACKAGED, FROZEN, COOKED): ");
		String inputState = scanner.nextLine().toUpperCase();
		EnumFoodState foodState = EnumFoodState.valueOf(inputState);
		
		return new Food(name, quantity, price, expiryDate, expired, boughtDate, category, foodState);
	}
	
	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public LocalDate getBoughtDate() {
		return boughtDate;
	}

	public void setBoughtDate(LocalDate boughtDate) {
		this.boughtDate = boughtDate;
	}

	public LocalDate getCookedDate() {
		return cookedDate;
	}

	public void setCookedDate(LocalDate cookedDate) {
		this.cookedDate = cookedDate;
	}

	public EnumCategory getCategory() {
		return category;
	}

	public void setCategory(EnumCategory category) {
		this.category = category;
	}

	public EnumFoodState getFoodState() {
		return foodState;
	}

	public void setFoodState(EnumFoodState foodState) {
		this.foodState = foodState;
	}

	public void checkFreshFood(Food food) {
		if (food == null) {
			throw new IllegalArgumentException("Food cannot be null");
		}
		LocalDate today = LocalDate.now();
		long daysToExpiry = ChronoUnit.DAYS.between(today, getExpiryDate());
		switch (food.getFoodState()) {

		case FRESH:

			if (today.isAfter(getExpiryDate()) || daysToExpiry == 0) {
				System.out.println(getName() + " has passed the expiring date: " + getExpiryDate());
				setExpired(true);
			} else if (daysToExpiry > 0) {
				System.out.println(getName() + " is not expired: " + daysToExpiry + " days to expiry.");
				setExpired(false);
			}
		default:
			throw new IllegalStateException("Incorrect food state: " + food.getFoodState());
		}
	}

	public void checkOtherFood(Food food) {

	}

	public void checkExpired(Food food) {
		if (food == null) {
			throw new IllegalArgumentException("Food cannot be null");
		}

		LocalDate today = LocalDate.now();

		switch (food.getFoodState()) {
		case FRESH:

		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("\n");
		sb.append("Bought date").append(getBoughtDate()).append(", ");
		sb.append("Category").append(getCategory()).append(", ");
		sb.append("Food State").append(getFoodState()).append(".");
		return sb.toString();
	}
}
