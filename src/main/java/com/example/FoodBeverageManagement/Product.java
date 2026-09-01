package com.example.FoodBeverageManagement;

import java.time.LocalDate;

public abstract class Product {

	private String name;
	private float quantity;
	private float price;
	private LocalDate expiryDate;
	private LocalDate boughtDate;
	private boolean expired = false;

	public Product(String name, float quantity, float price, LocalDate expiryDate, LocalDate boughtDate,
			boolean expired) {

		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.expiryDate = expiryDate;
		this.boughtDate = boughtDate;
		this.expired = expired;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getBoughtDate() {
		return boughtDate;
	}

	public void setBoughtDate(LocalDate boughtDate) {
		this.boughtDate = boughtDate;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Product: ").append(getClass()).append("\n");
		sb.append("Name: ").append(getName()).append(", ");
		sb.append("Quantity").append(getQuantity()).append(", ");
		sb.append("Price").append(getPrice()).append(";\n");
		sb.append("Expiry date").append(getExpiryDate()).append(", ");
		sb.append("Bought date").append(getBoughtDate()).append(", ");
		sb.append("Expired").append(isExpired()).append(";");
		return sb.toString();
	}

}
