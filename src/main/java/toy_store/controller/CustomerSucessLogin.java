package toy_store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import toy_store.dao.CustomerDao;
import toy_store.dao.PaymentDao;
import toy_store.dao.ToyDao;
import toy_store.dto.CartItem;
import toy_store.dto.Toys;

public class CustomerSucessLogin extends CustomerController {
    private PaymentDao paymentDao = new PaymentDao();
    private ToyDao toyDao = new ToyDao();
    private List<CartItem> cart = new ArrayList<>(); 

    public void display(String name) {
        try (Scanner sc = new Scanner(System.in)) {
            paymentDao.createTablePayment(); 
            while (true) {
                displayMenuHeader("Welcome " + name);
                System.out.println("\n 1. Shopping  \n 2. Profile \n 3. Exit ");
                System.out.println("Enter your option (1-3):");

                int option = sc.nextInt();
                sc.nextLine(); // Clear the newline

                switch (option) {
                    case 1:
                        shoppingCustomer(name, sc); // Go to shopping menu
                        break;
                    case 2:
                        new profileCustomer().display(name); // Profile view
                        break;
                    case 3:
                        System.out.println("Thank you for visiting! Please come again.");
                        super.display(); // Returning to the previous menu
                        return;
                    default:
                        System.out.println("Invalid option! Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shoppingCustomer(String name, Scanner sc) {
        try {
            while (true) {
                displayMenuHeader("Hello Customer " + name);
                System.out.println("\n 1. Browse Toys  \n 2. View Cart  \n 3. payment  \n 4. Exit ");
                System.out.println("Enter your option (1-4): ");

                int opt = sc.nextInt();
                sc.nextLine();

                switch (opt) {
                    case 1:
                        browseToy(name, sc); // Go to browse toys
                        break;
                    case 2:
                        displayCart(); // Display the cart
                        break;
                    case 3:
                    	Payment(sc,cart);
                        
                        break;
                    case 4:
                        new CustomerSucessLogin().display(name); // Return to main menu
                        return;
                    default:
                        System.out.println("Invalid option! Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void browseToy(String name, Scanner sc) {
        try {
            List<Toys> toys = toyDao.getToyAll();  // Fetch all toys
            if (toys != null && !toys.isEmpty()) {
                String dashesLine = new String(new char[100]).replace('\0', '-');
                displayMenuHeader("Toys Available");
                System.out.printf("%-8s %-30s %-15s %-20s %-10s \n", "Id", "Brand", "Cost", "Colour", "Quantity");
                System.out.println(dashesLine);

                for (Toys toy : toys) {
                    System.out.printf("%-8d %-30s %-15.2f %-20s %-10d\n", toy.getId(), toy.getBrand(), toy.getCost(),
                            toy.getColour(), toy.getQuantity());
                }
            } else {
                System.err.println("No Toys Found!");
                return;
            }
            while (true) {
                System.out.print("Enter the toy id (or 0 to finish shopping): ");
                int id = sc.nextInt();
                if (id == 0) {
                    break;  
                }
                Toys selectedToy = null;
                for (Toys toy : toys) {
                    if (toy.getId() == id) {
                        selectedToy = toy;
                        break;
                    }
                }
                if (selectedToy == null) {
                    System.err.println("Invalid Toy ID. Try again.");
                    continue;
                }
                System.out.print("Enter the quantity: ");
                int quantity = sc.nextInt();
                if (quantity > selectedToy.getQuantity()) {
                    System.err.println("Not enough stock. Available quantity: " + selectedToy.getQuantity());
                } else {
                    System.out.println("You have selected: " + selectedToy.getBrand() + " | Quantity: " + quantity);
                    selectedToy.setQuantity(selectedToy.getQuantity() - quantity);
                    toyDao.updateToy(selectedToy.getCost(), selectedToy.getQuantity(), selectedToy.getId());
                    System.out.println("Added to cart successfully!");

                    cart.add(new CartItem(selectedToy, quantity));  // Add selected toy to the cart

                    System.out.println("Do you want to continue shopping? (y/n): ");
                    char ch = sc.next().charAt(0);
                    if (ch == 'n') {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to display the cart
    public void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\nYour Cart:");
        String dashesLine = new String(new char[100]).replace('\0', '-');
        System.out.println(dashesLine);
        System.out.printf("%-8s %-30s %-10s %-10s\n", "Id", "Brand", "Quantity", "Total Cost");
        System.out.println(dashesLine);
        double totalPrice = 0;

        for (CartItem item : cart) {
            Toys toy = item.getToy();
            System.out.printf("%-8d %-30s %-10d %-10.2f\n", toy.getId(), toy.getBrand(), item.getQuantity(), item.getTotalCost());
            totalPrice += item.getTotalCost();
        }

        System.out.println(dashesLine);
        System.out.printf("Total Price: %-10.2f\n", totalPrice);
    }
    
    public void Payment(Scanner sc, List<CartItem> cart) {
        double totalPrice = 0;
        try {
            // Display cart items and total price
            for (CartItem item : cart) {
                Toys toy = item.getToy();
                System.out.printf("%-8d %-30s %-10d %-10.2f\n", toy.getId(), toy.getBrand(), item.getQuantity(), item.getTotalCost());
                totalPrice += item.getTotalCost();
            }

            System.out.printf("Total Price: %-10.2f\n", totalPrice);

            // Payment options loop
            while (true) {
                System.out.println("\n1. Proceed to Payment 2. Exit");
                System.out.print("Enter your option (1-2): ");
                int opt = sc.nextInt();

                switch (opt) {
                    case 1:
                        // Payment processing logic
                        System.out.print("Enter customer ID: ");
                        String bill = "Amount: " + totalPrice;
                        int customerId = sc.nextInt();
                        
                        // Deduct balance from customer's wallet
                        CustomerDao customerDao = new CustomerDao();
                        if (customerDao.deductBalance(customerId, totalPrice)) {
                        	paymentDao.addPaymentRecord(bill, customerId);
                        	cart.clear();
                            System.out.println("Payment successful! Amount deducted from wallet.");
                        } else {
                            System.out.println("Insufficient balance in wallet. Payment failed.");
                            return; // Exit if payment fails
                        }
                        return; // Exit after successful payment
                    case 2:
                        System.out.println("Exiting payment process.");
                        return; // Exit without payment
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
