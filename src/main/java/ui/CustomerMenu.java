package ui;

import java.sql.SQLException;
import java.util.Scanner;

import toy_store.dao.CustomerDao;
import toy_store.dto.Customer;

public class CustomerMenu extends Menu {

    private CustomerDao customerDao = new CustomerDao();

    @Override
    public void display() {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                displayMenuHeader("Customer details..");
                System.out.println("\n 1. Register \n 2. Login \n 3. Exit ");
                System.out.println("Enter your option (1-3):");

                int option = sc.nextInt();
                sc.nextLine(); // Clear the newline

                switch (option) {
                    case 1:
                        register(sc);
                        break;
                    case 2:
                        login(sc);
                        break;
                    case 3:
                        System.out.println("Thank you for visiting! Please come again.");
                        super.display();
                        return; // Exit the loop and return to the previous menu
                    default:
                        System.out.println("Invalid option! Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    // Register a new customer
    public void register(Scanner sc) {
        try {
            System.out.println("Enter your name:");
            String name = sc.nextLine();

            System.out.println("Enter your email:");
            String email = sc.nextLine();

            System.out.println("Enter your password:");
            String password = sc.nextLine();

            // Check if customer already exists
            if (customerDao.findByEmail(email) != null) {
                System.out.println("Email already registered! Please login.");
                return;
            }

            System.out.println("Enter your wallet balance:");
            double wallet = sc.nextDouble();

            System.out.println("Enter your phone number:");
            long phone = sc.nextLong();

            // Create a new customer
            Customer customer = new Customer(0, name, email, password, wallet, phone);
            customerDao.saveCustomer(customer);
            System.out.println("Registration successful! Please login.");

        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    // Login a customer
    public void login(Scanner sc) {
        try {
            System.out.println("Enter your email:");
            String email = sc.nextLine();

            System.out.println("Enter your password:");
            String password = sc.nextLine();

            // Check if customer exists and password is correct
            Customer customer = customerDao.findByEmailAndPassword(email, password);
            if (customer != null) {
                System.out.println("Login successful! Welcome, " + customer.getName() + "!");
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }
}
