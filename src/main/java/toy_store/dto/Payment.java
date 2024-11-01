package toy_store.dto;

public class Payment {
	
	private int id;
	private String bill;
	private int customerId;
	
//	ArgsConstructer
	public Payment(int id, String bill, int customerId) {
		super();
		this.id = id;
		this.bill = bill;
		this.customerId = customerId;
	}
	
//	NoArgsConstructer
	public Payment() {
		super();
		
	}
	
//	Setters and Getters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	

}
