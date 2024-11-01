package toy_store.controller;

import java.util.Scanner;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

import toy_store.dao.CustomerDao;
import toy_store.dto.Customer;


public class profileCustomer extends CustomerSucessLogin {
	private CustomerDao customerDao = new CustomerDao();

	public void display(String name) {

		try {
			Scanner sc = new Scanner(System.in);
			while (true) {
				displayMenuHeader("Welcome profile " + name + " !");
				System.out.println("\n 1. update \n 2. delete \n 3. fetch \n 4. Exit.. ");

				System.out.print("Enter the Option(1-4): ");

				int opt = sc.nextInt();

				switch (opt) {
				case 1:
					updateCustomer(name, sc);
					break;
				case 2:
					break;
				case 3:
					fetchprofile(name);
					break;
				case 4:
					new CustomerSucessLogin().display(name);
					return;
				default:
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Fetching and displaying the customer profile
	public void fetchprofile(String name) {
	    try {
	        // Corrected the method call to fetchProfile from customerDao
	        Customer customer = customerDao.fetchProfile(name); 
	        
	        if (customer != null) {
	            String dashesLine = new String(new char[100]).replace('\0', '-');
	            displayMenuHeader("Welcome " + name);
	            
	            System.out.printf("%-8s %-30s %-30s %-30s\n", "Id", "Name", "E-mail", "Phone");
	            System.out.println(dashesLine);
	            
	            System.out.printf("%-8d %-30s %-30s %-30s\n", customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
	            
	            // Adding some spacing after the customer profile
	            for (int i = 0; i <= 3; i++) {
	                System.out.println();
	            }
	        } else {
	            System.out.println("No customer found with the name: " + name);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println(e.getMessage());
	    }
	}


	// Update vendor password
	public void updateCustomer(String name, Scanner sc) {
		try {
			System.out.println("Hello, Customer: " + name);

			// Prompt the user to enter a new password
			System.out.print("Enter how much add in wallet: ");
			double wallet=sc.nextDouble();
			
			sc.nextLine();
			System.out.print("Enter the new password if not update Enter old password : ");
			String pass = sc.nextLine();

			// Call the DAO method to update the password
			boolean res = customerDao.updateCustomer(name,wallet, pass);

			// Check if the password update was successful
			if (res) {
				System.out.println("Password updated successfully!");
			} else {
				System.out.println("Failed to update password. Please check the name and try again.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error: " + e.getMessage());// Print any exceptions for debugging
		}
	}

}
