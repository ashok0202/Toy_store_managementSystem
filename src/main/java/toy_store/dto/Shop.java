package toy_store.dto;

public class Shop {
	
	private int id;
	private String name;
	private String address;
	private String venderEmail;
	
//	ArgsConstructer
	public Shop(int id, String name, String address, String venderEmail) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.venderEmail = venderEmail;
	}
	
//	NoArgsConstructer
	public Shop() {
		super();
		// TODO Auto-generated constructor stub
	}
	
//	Setters and Getters
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getVenderEmail() {
		return venderEmail;
	}
	public void setVenderEmail(String venderEmail) {
		this.venderEmail = venderEmail;
	}
	
	
	

}
