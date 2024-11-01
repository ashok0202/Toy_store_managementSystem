package toy_store.dto;

public class Customer {
	
	private int id;
	private String name,email,pwd;
	private double wallet;
	private long phone;
	
//	ArgsConstructer
	public Customer(int id, String name, String email, String pwd, double wallet, long phone) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.pwd = pwd;
		this.wallet = wallet;
		this.phone = phone;
	}
//	NoArgsConstructor
	public Customer() {
		super();
	}
	
	
//	Setters and Getters Customer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public double getWallet() {
		return wallet;
	}
	public void setWallet(double wallet) {
		this.wallet = wallet;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	

}
