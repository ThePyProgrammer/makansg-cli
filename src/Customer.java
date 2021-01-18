
public class Customer {
	private String customerId;
	private String name;
	private String address;
	private String postalCode;

	public Customer(){}
	public Customer(String customerId, String name, String address, String postalCode ){
		if(!checkCustomerId(customerId)){
			throw new IllegalArgumentException("Unable to create Customer. Invalid Customer ID");
		}
		else{
			this.customerId = customerId;
			this.name = name;
			this.address = address;
			this.postalCode = postalCode;
		}
	}
	public void setCustomerID(String customerId){
		if(!checkCustomerId(customerId)){
			throw new IllegalArgumentException("Unable to set Customer ID. Invalid Customer ID");
		}
		else{
			this.customerId = customerId;
		}
	}
	public void setName(String name){
		this.name = name;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}

	public String getCustomerId(){
		return this.customerId;
	}
	public String getName(){
		return this.name;
	}
	public String getAddress(){
		return this.address;
	}
	public String getPostalCode(){
		return this.postalCode;
	}

	public static boolean checkCustomerId(String customerId){
		if(customerId.length()!=9)  // check length
			return false;
		customerId = customerId.toUpperCase();	    // convert to uppercase
		if(!customerId.startsWith("M")) //check starts with M
			return false;
		if(!customerId.endsWith("SG")) //check ends with SG
			return false;
		//check if 2nd to 7th digits are digits
		for(int i=1; i<customerId.length()-2; i++ ) {
			if(!Character.isDigit(customerId.charAt(i))){
				return false;
			}
		}
		return true;
	}
	public String toString(){
		return this.customerId + " " + this.name + " " + this.address + " " + postalCode;
	}
}
