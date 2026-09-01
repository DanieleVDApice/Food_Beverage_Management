package com.example.FoodBeverageManagement;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Product> products = new ArrayList<>();
	private List<Beverage> beverages = new ArrayList<>();
	private List<Food> foods = new ArrayList<>();

	public void addProduct(Product product) {
		products.add(product);
		if (product instanceof Beverage beverage) {
			beverages.add(beverage);
		} else if (product instanceof Food food) {
			foods.add(food);
		} else {
			throw new IllegalArgumentException(
					"Product temporarily not managed: " + product.getClass().getSimpleName());
		}
	}

	public void removeAllProduct(Product product) {
		boolean productFound = findProduct(product);
		if (productFound == true) {
			products.remove(product);
			if (product instanceof Beverage beverage) {
				beverages.remove(beverage);
			} else if (product instanceof Food food) {
				foods.remove(food);
			} else {
				throw new IllegalArgumentException(
						"Product temporarily not managed: " + product.getClass().getSimpleName());
			}
		}
	}

	public boolean findProduct(Product product) {
		for (Product p : products) {
			if (p.equals(product)) {
				product.toString();
				return true;
			}
		}
		System.out.println("Product not present.");
		return false;
	}
}
