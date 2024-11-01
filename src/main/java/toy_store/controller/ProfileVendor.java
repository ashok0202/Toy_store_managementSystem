package toy_store.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import toy_store.dao.VendorDao;
import toy_store.dto.Vendor;

public class ProfileVendor extends VenderSucessLogin{
	
	private VendorDao venderDao=new VendorDao();
	
	public void display(String name) {
		
		try {
			Scanner sc=new Scanner(System.in);
			while(true) {
				displayMenuHeader("Welcome profile "+name+" !");
				System.out.println("\n 1. update \n 2. delete \n 3. fetch \n 4. Exit.. ");
				
				System.out.print("Enter the Option(1-4): ");
				
				int opt=sc.nextInt();
				
				switch (opt) {
				case 1:
					updateVendor(name,sc);
					break;
				case 2:
					break;
				case 3:
					fetchProfile(name);
					break;
				case 4:
					new VenderSucessLogin().display(name);
					return;
				default:
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//fetching the profile
	public void fetchProfile(String name) {
	    try {
	        Vendor vendor = venderDao.fetchProfile(name);  // Fetch vendor by name
	        
	        if (vendor != null) {
	      
	        	String dashesLine = new String(new char[100]).replace('\0', '-');
	            displayMenuHeader("Welcome "+name);
	            System.out.printf("%-8s %-30s %-30s %-30s\n", "Id", "Name", "E-mail", "Phone");
	            System.out.println(dashesLine);
	            System.out.printf("%-8s %-30s %-30s %-30s\n",vendor.getId(),vendor.getName(),vendor.getEmail(),vendor.getPhone());
	            for(int i=0;i<=3;i++) {
	            	System.out.println();
	            } 
	        } else {
	            System.out.println("No vendor found with the name: " + name);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    }
	}
	
	// Update vendor password
	public void updateVendor(String name,Scanner sc) {
	    try {
	        System.out.println("Hello, Vendor: " + name);
	        
	        // Prompt the user to enter a new password
	        sc.nextLine();
	        System.out.print("Enter the new password: ");
	        String pass = sc.nextLine();
	        
	        // Call the DAO method to update the password
	        boolean res = venderDao.updateVendor(name, pass);
	        
	        // Check if the password update was successful
	        if (res) {
	            System.out.println("Password updated successfully!");
	        } else {
	            System.out.println("Failed to update password. Please check the name and try again.");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("error: "+e.getMessage());// Print any exceptions for debugging
	    }
	}


	

}
