package com.example.FoodBeverageManagement;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Product> products = new ArrayList<>();
	private List<Beverage> beverages = new ArrayList<>();
	private List<Food> foods = new ArrayList<>();

	public static class ErrorQuantityException extends Exception {
		public ErrorQuantityException(String message) {
			super(message);
		}
	}

	public void addProduct(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}
		if (product instanceof Beverage beverage) {
			beverages.add(beverage);
			products.add(product);
		} else if (product instanceof Food food) {
			foods.add(food);
			products.add(product);
		} else {
			throw new IllegalArgumentException(
					"Product temporarily not managed: " + product.getClass().getSimpleName());
		}
	}

	public void increaseProduct(int quantity, Product product) throws ErrorQuantityException {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity to increase must be positive");
		}
		if (!findProduct(product)) {
			throw new IllegalArgumentException("Product not found in inventory: " + product.getName());
		}
		
		if (product instanceof Beverage beverage) {
			product.setQuantity(product.getQuantity() + quantity);
			beverage.setQuantity(beverage.getQuantity() + quantity);
		} else if (product instanceof Food food) {
			product.setQuantity(product.getQuantity() + quantity);
			food.setQuantity(food.getQuantity() + quantity);
		}
	}

	public void removeProduct(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}

		if (!findProduct(product)) {
			throw new IllegalArgumentException("Product not found in inventory: " + product.getName());
		}
		if (product instanceof Beverage beverage) {
			products.remove(product);
			beverages.remove(beverage);
		} else if (product instanceof Food food) {
			products.remove(product);
			foods.remove(food);
		}
	}

	public void reduceProduct(int quantity, Product product) throws ErrorQuantityException {
		if (product == null) {
			throw new IllegalArgumentException("Product cannot be null");
		}
		if (!findProduct(product)) {
			throw new IllegalArgumentException("Product not found in inventory: " + product.getName());
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity to reduce must be positive");
		}
		if (quantity > product.getQuantity()) {
			throw new ErrorQuantityException("Not enough quantity available for " + product.getName());
		}

		if (quantity == product.getQuantity()) {
			if (product instanceof Beverage beverage) {
				products.remove(product);
				beverages.remove(beverage);
			} else if (product instanceof Food food) {
				products.remove(product);
				foods.remove(food);
			}
		} else {
			product.setQuantity(product.getQuantity() - quantity);
		}
	}

	public boolean findProduct(Product product) {
		for (Product p : products) {
			if (p.equals(product)) {
				return true;
			}
		}
		return false;
	}
}
