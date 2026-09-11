package com.example.FoodBeverageManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static final Inventory inventory = new Inventory();
	private static final List<Food> foods = new ArrayList<>();
	private static final List<Beverage> beverages = new ArrayList<>();
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		boolean running = true;

		while (running) {
			printMenu();
			String choice = scanner.nextLine().trim();

			switch (choice) {
				case "1" -> addFood();
				case "2" -> addBeverage();
				case "3" -> increaseQuantity();
				case "4" -> reduceQuantity();
				case "5" -> removeProduct();
				case "6" -> checkExpiry();
				case "7" -> listFoods();
				case "8" -> listBeverages();
				case "0" -> {
					running = false;
					System.out.println("Goodbye!");
				}
				default -> System.out.println("Invalid option, please try again.");
			}
		}

		scanner.close();
	}

	private static void printMenu() {
		System.out.println();
		System.out.println("===== Food & Beverage Inventory =====");
		System.out.println("1. Add food");
		System.out.println("2. Add beverage");
		System.out.println("3. Increase product quantity");
		System.out.println("4. Reduce product quantity");
		System.out.println("5. Remove product");
		System.out.println("6. Check product expiry");
		System.out.println("7. List foods");
		System.out.println("8. List beverages");
		System.out.println("0. Exit");
		System.out.print("Choose an option: ");
	}

	private static void addFood() {
		Food food = Inventory.createFood();
		inventory.addProduct(food);
		foods.add(food);
		System.out.println("Food added: " + food.getName());
	}

	private static void addBeverage() {
		Beverage beverage = Inventory.createBeverage();
		inventory.addProduct(beverage);
		beverages.add(beverage);
		System.out.println("Beverage added: " + beverage.getName());
	}

	private static void increaseQuantity() {
		Product product = selectProduct();
		if (product == null) {
			return;
		}
		System.out.print("Quantity to add: ");
		int amount = readPositiveInt();
		if (amount <= 0) {
			return;
		}
		try {
			inventory.increaseProduct(amount, product);
			System.out.println("New quantity: " + product.getQuantity());
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void reduceQuantity() {
		Product product = selectProduct();
		if (product == null) {
			return;
		}
		System.out.print("Quantity to remove: ");
		int amount = readPositiveInt();
		if (amount <= 0) {
			return;
		}
		try {
			inventory.reduceProduct(amount, product);
			System.out.println("Operation completed.");
		} catch (IllegalArgumentException | Inventory.ErrorQuantityException e) {
			System.out.println("Error: " + e.getMessage());
		}

		if (!inventory.findProduct(product)) {
			foods.remove(product);
			beverages.remove(product);
		}
	}

	private static void removeProduct() {
		Product product = selectProduct();
		if (product == null) {
			return;
		}
		try {
			inventory.removeProduct(product);
			foods.remove(product);
			beverages.remove(product);
			System.out.println("Product removed.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void checkExpiry() {
		Product product = selectProduct();
		if (product == null) {
			return;
		}
		boolean expired = inventory.checkProduct(product);
		System.out.println(product.getName() + (expired ? " is expired." : " is not expired."));
	}

	private static void listFoods() {
		if (foods.isEmpty()) {
			System.out.println("No foods in inventory.");
			return;
		}
		inventory.getFoods(foods);
	}

	private static void listBeverages() {
		if (beverages.isEmpty()) {
			System.out.println("No beverages in inventory.");
			return;
		}
		inventory.getBeverages(beverages);
	}

	private static Product selectProduct() {
		List<Product> all = new ArrayList<>();
		all.addAll(foods);
		all.addAll(beverages);

		if (all.isEmpty()) {
			System.out.println("There are no products yet. Add one first.");
			return null;
		}

		System.out.println("Available products:");
		for (Product p : all) {
			System.out.println(" - " + p.getName() + " (qty: " + p.getQuantity() + ")");
		}
		System.out.print("Enter the product name: ");
		String name = scanner.nextLine().trim();

		for (Product p : all) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		System.out.println("Product not found.");
		return null;
	}

	private static int readPositiveInt() {
		try {
			return Integer.parseInt(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			System.out.println("Invalid number.");
			return 0;
		}
	}
}