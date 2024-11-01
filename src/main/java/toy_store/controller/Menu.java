package toy_store.controller;

import java.util.Scanner;


public class Menu {
public Menu() {}
	
	public void display() {
		try(Scanner sc = new Scanner(System.in)) {
			while (true) {
				displayMenuHeader("Welcome to Toyys Shop....");
				System.out.println();

				System.out.println("\n 1.Customer \n 2. Vender \n 3. Exit");
				System.out.print("Enter the number (1-3) :");
				int opt = sc.nextInt();

				switch (opt) {
				case 1:
					new CustomerController().display();
					break;
				case 2:
					new VenderController().display();
					break;
				case 3:
					System.out.println("Thanks for choosing toys App, See you again !");
					System.exit(0);
					break;

				default:
					System.out.println("Invalid Input. Please enter the valid input from(1-3)");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void displayMenuHeader(String menuHeader) {
		printDashLine();
		String spaces = new String(new char[50]).replace('\0', ' ');
		System.out.printf("%-50s %-10s %-50s \n", spaces, menuHeader, spaces);
		printDashLine();
	}

	public void printDashLine() {
		String dashesLine = new String(new char[100]).replace('\0', '-');
		System.out.println(dashesLine);
	}

}
