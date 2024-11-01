package toy_store.dto;

public class Vendor {
	
	private int id;
	private String name,email,pwd;
	private long phone;
	
//	ArgsConstructor 
	public Vendor(int id, String name, String email, String pwd, long phone) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.pwd = pwd;
		this.phone = phone;
	}
	
//	NoArgsConstructer
	public Vendor() {
		super();
		
	}
	
//	setters and getters for Vender;
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
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	
	
	

}
