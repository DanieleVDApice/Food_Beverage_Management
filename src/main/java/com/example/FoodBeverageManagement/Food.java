package com.example.FoodBeverageManagement;

import java.time.LocalDate;

public class Food extends Product {
	private EnumCategory category;
	private EnumFoodState foodState;

	public Food(String name, float quantity, float price, LocalDate expiryDate, LocalDate boughtDate, boolean expired,
			EnumCategory category, EnumFoodState foodState) {
		super(name, quantity, price, expiryDate, boughtDate, expired);
		this.category = category;
		this.foodState = foodState;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("\n");
		sb.append("Category").append(getCategory()).append(", ");
		sb.append("Food State").append(getFoodState()).append(".");
		return sb.toString();
	}
}
