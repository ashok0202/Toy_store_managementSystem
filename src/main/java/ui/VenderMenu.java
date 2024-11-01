package ui;

import java.util.Scanner;

public class VenderMenu extends Menu{
	
	@Override
	public void display() {
		try {
			Scanner sc=new Scanner(System.in);
			while(true) {
				displayMenuHeader("Vender details..");
				System.out.println("\n 1.Register \n 2.Login \n 3. Exit ");
				System.out.println("Enter the opt (1-3) :");
			
				int otp=sc.nextInt();
				switch (otp) {
				case 1:
					Register();
					break;
				case 2:
					login();
				case 3:
					System.out.println("Tanq for choosing vender Register again visit...");
					super.display();
				default:
					break;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void login() {
		
		
	}

	private void Register() {
		
		
	}

}
