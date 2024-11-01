package toy_store.controller;

import java.sql.SQLException;
import java.util.Scanner;

import toy_store.dao.VendorDao;
import toy_store.dto.Vendor;

public class VenderController extends Menu {
    
    private VendorDao vendorDao = new VendorDao();

    @Override
    public void display() {
        try(Scanner sc = new Scanner(System.in)) {
        	
        	
        	// Create the vendor table if it does not exist
            vendorDao.createTableForVendor();  // Ensure the table exists
            
            while (true) {
                displayMenuHeader("Vendor details....");
                System.out.println("\n 1. Register \n 2. Login \n 3. Exit ");
                System.out.print("Enter your option (1-3): ");
                
                int otp = sc.nextInt();
                sc.nextLine(); // Clear the newline character
                
                switch (otp) {
                    case 1:
                        register(sc);
                        break;
                    case 2:
                        login(sc);
                        break;
                    case 3:
                        System.out.println("Thank you for choosing the vendor. Please visit again...");
                        super.display();
                        return; // Exit the loop and return to the previous menu
                    default:
                        System.out.println("Invalid option! Please enter a valid input (1-3).");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Register a new vendor
    public void register(Scanner sc) {
    	
        try {
            System.out.print("Enter your name: ");
            String name = sc.nextLine();

            System.out.print("Enter your email: ");
            String email = sc.nextLine();

            System.out.print("Enter your password: ");
            String password = sc.nextLine();

            // Check if vendor already exists
            if (vendorDao.findByEmail(email) != null) {
                System.out.println("Email already registered! Please login.");
                return;
            }

            System.out.print("Enter your phone number: ");
            long phone = sc.nextLong();

            // Create a new vendor
            Vendor vendor = new Vendor(0, name, email, password, phone);
            vendorDao.saveVendor(vendor);
            System.out.println("Registration successful! Please login.");

        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    // Login a vendor
    public void login(Scanner sc) {
        try {
            System.out.print("Enter your email: ");
            String email = sc.nextLine();

            System.out.print("Enter your password: ");
            String password = sc.nextLine();

            // Check if vendor exists and password is correct
            Vendor vendor = vendorDao.findByEmailAndPassword(email, password);
            if (vendor != null) {
                //System.out.println("Login successful! Welcome, " + vendor.getName() + "!");
            	new VenderSucessLogin().display(vendor.getName());
            	
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }
}
