package com.example.FoodBeverageManagement;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

	}
}

@Override
public void checkExpired() {

	LocalDate today = LocalDate.now();
	long daysPassed = ChronoUnit.DAYS.between(getPreparedDay(), today);
	if (!getPreparedDay().plusDays(getDaysLimit()).isAfter(today)) {
		System.out.println(getName() + " needs to be checked. " + daysPassed + " days have passed.");

		Scanner scanner = new Scanner(System.in);
		String answer = "";
		boolean validAnswer = false;

		while (!validAnswer) {
			System.out.println("Is the food still good? Y/N");
			answer = scanner.nextLine().trim();

			if (answer.equalsIgnoreCase("Y")) {
				setExpired(false);
				validAnswer = true;
				System.out.println(getName() + " is still good. Remember to use it as soon as possible.");
			} else if (answer.equalsIgnoreCase("N")) {
				setExpired(true);
				validAnswer = true;
				System.out.println(getName() + " is expired. Throw it away.");
			} else {
				System.out.println("Incorrect answer. Please, insert Y or N.");
			}
		}
	} else {
		setExpired(false);
		System.out.println(getName() + " doesn't need to be checked. " + daysPassed + " days have passed.");
	}
}

@Override
public void checkExpired() {
	LocalDate today = LocalDate.now();
	long daysToExpiry = ChronoUnit.DAYS.between(today, getExpiringDate());
	if (today.isAfter(getExpiringDate()) || daysToExpiry == 0) {
		System.out.println(getName() + " has passed the expiring date: " + getExpiringDate());
		setExpired(true);
	} else if (daysToExpiry > 0){
		System.out.println(getName() + " is not expired: " + daysToExpiry + " days to expiry.");
		setExpired(false);
	}
}
