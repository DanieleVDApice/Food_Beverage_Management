package com.example.FoodBeverageManagement;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Beverage extends Product {
	private EnumContainer container;

	public Beverage(String name, float quantity, float price, LocalDate expiryDate, LocalDate boughtDate,
			boolean expired, EnumContainer container) {
		super(name, quantity, price, expiryDate, boughtDate, expired);
		this.container = container;
	}

	public EnumContainer getContainer() {
		return container;
	}

	public void setContainer(EnumContainer container) {
		this.container = container;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("\n");
		sb.append("Container").append(getContainer()).append(".");
		return sb.toString();
	}
}
