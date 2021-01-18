
public class Supplier {
	//Attributes
	private String supplierID, foodID, food;
	private double price;
	//Constructors
	public Supplier(){
	}
	public Supplier(String supplierID, String foodID, String food, double price){
		this.supplierID = supplierID;
		this.foodID = foodID;
		this.food = food;
		this.price = price;
	}
	//Accessors
	public String getSupplierID() {
		return supplierID;
	}
	public String getFoodID() {
		return foodID;
	}
	public String getFood() {
		return food;
	}
	public double getPrice() {
		return price;
	}
	//Mutators
	public void setSupplierID(String supplierId) {
		this.supplierID = supplierID;
	}
	public void setFoodID(String foodId) {
		this.foodID = foodID;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public void setPrice(double price) {
		if(price > 0)
			this.price = price;
	}
	//Other methods
	public String toString() {
		return supplierID+" "+foodID+" "+food+" $"+String.format("%.2f",price);
	}
}
