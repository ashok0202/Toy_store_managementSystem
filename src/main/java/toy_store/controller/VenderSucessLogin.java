package toy_store.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import toy_store.dao.ShopDao;
import toy_store.dao.ToyDao;
import toy_store.dto.Shop;
import toy_store.dto.Toys;

public class VenderSucessLogin extends VenderController {
	
	private ShopDao shopDao=new ShopDao();
	private ToyDao  toyDao=new ToyDao();
	
	
	public void display(String name) {
		try (Scanner sc = new Scanner(System.in)) {
			
			shopDao.createTableForShop();
			toyDao.CreateTableForToy();
  
            while (true) {
                displayMenuHeader("Welcome "+name);
                System.out.println("\n 1. Shopping  \n 2. Profile \n 3. Exit ");
                System.out.println("Enter your option (1-3):");

                int option = sc.nextInt();
                sc.nextLine(); // Clear the newline

                switch (option) {
                    case 1:
                    	displayVendorOptions(name);
                        break;
                    case 2:
                    	new ProfileVendor().display(name);
                        break;
                    case 3:
                        System.out.println("Thank you for visiting! Please come again.");
                        super.display();
                        break; 
                    default:
                        System.out.println("Invalid option! Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void displayVendorOptions(String venName) {
		
		try(Scanner sc=new Scanner(System.in)) {
			
			while(true) {
				displayMenuHeader("What you want"+venName);
				System.out.println("\n 1.create shop \n 2.update Shop \n 3.delete Shop \n 4. Add toys \n 5.update toy \n 6.showToys \n 7.delete toy \n 8.exit");
				System.out.println("Enter the option (1-7): ");
			
				int opt=sc.nextInt();
				sc.nextLine();

				switch (opt) {
				case 1:
					createShop(sc);
					break;
				case 2:
					updateShop(sc);
					break;
				case 3:
					deleteShop(sc);
					break;
				case 4:
					addToy(sc);
					break;
				case 5:
					updateToy(sc);
					break;
				case 6:
					showToys();
					break;
				case 7:
					deletToy(sc);
					break;
				case 8:
				    new VenderSucessLogin().display(venName);
				    return;
				    
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createShop(Scanner sc) {
		try {
			System.out.println("Enter the Shop Details...");
			System.out.println();
			
			System.out.println("Enter shop name: ");
			String name=sc.nextLine();
			
			System.out.println("Enter the shop Address(place and streat): ");
			String address=sc.nextLine();
			
			System.out.println("Enter the Vender Email : ");
			String email=sc.nextLine();
			
			Shop shop=new Shop(0,name,address,email);
			shopDao.saveShop(shop);
			
			System.out.println("SucessFully register Shop....");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addToy(Scanner sc) {
	    try {
	        System.out.println("Enter the Toy Details....");
	        System.out.println();
	        
	        System.out.print("Enter the brand: ");
	        String brand = sc.nextLine();
	        
	        System.out.print("Enter the cost of toy: ");
	        Double cost = sc.nextDouble();
	        
	        sc.nextLine();  // Consume the leftover newline after nextDouble
	        
	        System.out.print("Enter the colour: ");
	        String colour = sc.nextLine();
	        
	        System.out.print("Enter the quantity: ");
	        int quantity = sc.nextInt();
	        
	        sc.nextLine();  // Consume the leftover newline after nextInt
	        
	        System.out.print("Enter the type: ");
	        String type = sc.nextLine();
	        
	        Toys toy = new Toys(0, brand, cost, colour, quantity, type);
	        toyDao.saveToy(toy);
	        
	        System.out.println("Successfully registered the toy!");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
    //Delete the shop By id;
	public void deleteShop(Scanner sc) {
		try {
			System.out.println("Enter the Delete id : ");
			int id=sc.nextInt();
			boolean isDeleted = shopDao.deleteById(id);
			if(isDeleted) {
				System.out.println("Sucessfully Deleted Shop");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//updateShop by user Id (shop name and user email);
	public void updateShop(Scanner sc) {  
	    try {
	        System.out.println("Updating the Shop Details...");
	        System.out.println();
	        
	        System.out.print("Enter the Shop ID to be updated: ");
	        int id = sc.nextInt();  // Get the shop ID
	        sc.nextLine(); 
	        
	        System.out.print("Enter the new Shop name: ");
	        String name = sc.nextLine();  // Get the new shop name
	        
	        System.out.print("Enter the new Shop Address: ");
	        String address = sc.nextLine();  // Get the new shop address
	        
	        // Update the shop using the DAO method
	        boolean isUpdated = shopDao.updateById(name, address, id);
	        
	        // Provide feedback to the user
	        if (isUpdated) {
	            System.out.println("Shop updated successfully.");
	        } else {
	            System.out.println("Failed to update the shop. Please check the ID and try again.");
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Database error: " + e.getMessage());  // More specific error handling
	    } catch (Exception e) {
	        e.printStackTrace();  // Handle any other exceptions
	    }
	}
	public void updateToy(Scanner sc) {
		try {
			
			System.out.println("Updating the toy details.....");
			System.out.println();
			
		    System.out.print("Enter the Toy Id: ");
		    int id=sc.nextInt();
		    
		    System.out.print("Enter the UpdateCost: ");
		    double cost=sc.nextDouble();
		    
		    System.out.print("Enter the updateQuantity: ");
		    int quantity=sc.nextInt();
		    
		    boolean resUpadate=toyDao.updateToy(cost,quantity,id);
		    if(resUpadate) {
		    	System.out.println("Shop updated successfully.");
		    	
		    }else {
	            System.out.println("Failed to update the Toy. Please check the ID and try again.");
	        }
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void deletToy(Scanner sc) {
		try {
			
			System.out.println("Delete the toy...");
			System.out.println();
			
			System.out.print("Entre the Brand Name: ");
			String brand=sc.nextLine();
			
			boolean resDelete=toyDao.deleteToy(brand);
			
			if(resDelete) {
				System.out.println("SucessFully Deleted the toy..");
			}
			else {
				System.out.println("Failed to Delete the Toy. Please check the ID and try again.");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showToys() {
		try {
			List<Toys> toys = toyDao.getToyAll();

			if (toys != null && !toys.isEmpty()) {
				String dashesLine = new String(new char[100]).replace('\0', '-');
				displayMenuHeader("Toys Available");
				System.out.printf("%-8s %-30s %-15s %-20s %-10s \n", "Id", "Brand", "Cost", "Colour", "Quantity");
				System.out.println(dashesLine);

				for (Toys toy : toys) {
					// Ensure all fields match the format specifiers
					System.out.printf("%-8d %-30s %-15.2f %-20s %-10d\n", toy.getId(), toy.getBrand(), toy.getCost(),
							toy.getColour(), toy.getQuantity());
				}
			} else {
				System.err.println("No Toys Found!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
