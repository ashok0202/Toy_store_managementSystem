package toy_store.dto;

public class Toys {
	
	private int id;
	private String brand;
	private double cost;
	private String colour;
	private int quantity;
	private String type;
	
//	ArgsConstructor
	public Toys(int id, String brand, double cost, String colour, int quantity, String type) {
		super();
		this.id = id;
		this.brand = brand;
		this.cost = cost;
		this.colour = colour;
		this.quantity = quantity;
		this.type = type;
	}
	
//	NoArgsConstructor
	public Toys() {
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
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
