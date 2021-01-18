import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Database {
	//add your code below
	ArrayList<Customer> customerDB;
	ArrayList<Supplier> supplierDB, shoppingCartDB, listedItemsDB;
	ArrayList<Postal> postalDB;

	Database(String customerFile, String supplierFile, String postalFile) {
		customerDB = new ArrayList<>();
		supplierDB = new ArrayList<>();
		shoppingCartDB = new ArrayList<>();
		listedItemsDB = new ArrayList<>();
		postalDB = new ArrayList<>();
		loadCustomerDB(customerFile);
		loadSupplierDB(supplierFile);
		loadPostalDB(postalFile);
	}

	public ArrayList<Supplier> getSupplier() { return supplierDB; }
	public ArrayList<Supplier> getListedItems() { return listedItemsDB; }
	public ArrayList<Supplier> getShoppingCart() { return shoppingCartDB; }
	public Customer getCustomer(String customerId) {
		for(Customer cust:customerDB) {
			if (cust.getCustomerId().equals(customerId)) return new Customer(cust.getCustomerId(), cust.getName(), cust.getAddress(), cust.getPostalCode());
		}
		return null;
	}

	public void loadSupplierDB(String filename){
		try {
			File file = new File(filename);
			Scanner s = new Scanner(file);
			while (s.hasNext()) {
				String[] splitted = s.nextLine().trim().split(",");
				supplierDB.add(new Supplier(splitted[0], splitted[1], splitted[2], Double.parseDouble(splitted[3])));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void loadCustomerDB(String filename){
		try {
			File file = new File(filename);
			Scanner s = new Scanner(file);
			while (s.hasNext()) {
				String[] splitted = s.nextLine().trim().split(",");
				customerDB.add(new Customer(splitted[0], splitted[1], splitted[2], splitted[3]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void loadPostalDB(String filename){
		try {
			File file = new File(filename);
			Scanner s = new Scanner(file);
			while (s.hasNext()) {
				String[] splitted = s.nextLine().trim().split(",");
				postalDB.add(new Postal(splitted[0], splitted[1]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean checkPostal(String postalCode) {
		return postalCode.length() == 6 &&
				Character.isDigit(postalCode.charAt(0)) && Character.isDigit(postalCode.charAt(1)) &&
				Character.isDigit(postalCode.charAt(2)) && Character.isDigit(postalCode.charAt(3)) &&
				Character.isDigit(postalCode.charAt(4)) && Character.isDigit(postalCode.charAt(5)) &&
				Integer.parseInt(postalCode.substring(0, 2)) != 74 && Integer.parseInt(postalCode.substring(0, 2)) <= 81 && Integer.parseInt(postalCode.substring(0, 2)) >= 1;
	}

	public String getArea(String postalCode) {
		if (!checkPostal(postalCode)) return "";
		String id = postalCode.substring(0, 2);
		for(Postal p : postalDB) {
			if (p.getSectorCode().equals(id)) return p.getDistrictArea();
		}
		return "";
	}

	public ArrayList<String> getRelatedSector(String postalCode) {
		String s = getArea(postalCode);
		var array = new ArrayList<String>();
		for(Postal p : postalDB) {
			if (p.getDistrictArea().equals(s)) array.add(p.getSectorCode());
		}
		Collections.sort(array);
		return array;
	}

	public boolean checkWithinDistrictArea(String postalCode) {
		var array = getRelatedSector(postalCode);
		String address = getCustomer(Security.getCurrentCustomerId()).getPostalCode().substring(0, 2);
		for(String s : array) {
			if (address.equals(s)) return true;
		}
		return false;
	}

	public void addListedItems(String postalCode) {
		var array = getRelatedSector(postalCode);
		Collections.sort(array);
		for(Supplier s : supplierDB) {
			if (array.contains(s.getSupplierID().substring(0, 2))) listedItemsDB.add(s);
		}
	}

	public void getOrderSummary() {
		displayLogo();
		System.out.println("+========================================================+");
		System.out.println("YOUR CURRENT ORDER:");
		double total = 0;
		for(int i = 0; i < shoppingCartDB.size(); i++) {
			var temp = shoppingCartDB.get(i);
			total += temp.getPrice();
			System.out.printf("[%3d] %s %-25s$%.2f%n", i+1, temp.getFoodID(), temp.getFood(), temp.getPrice());
		}
		System.out.printf("%nYOUR TOTAL BILL IS : $%.2f %n", total);
	}

	public void writeTransaction(String filename) {
		try {
			var file = new FileOutputStream(filename, true);
			var pw = new PrintWriter(file);
			for(int i = 0; i < shoppingCartDB.size(); i++) {
				var temp = shoppingCartDB.get(i);
				pw.printf(new Date() + " %s %s %s $%.2f%n", Security.getCurrentCustomerId(), temp.getFoodID(), temp.getFood(), temp.getPrice());
			}
			pw.close();
		} catch (FileNotFoundException e) {}
	}
	//Given, no edits required.
	public static void displayLogo(){
		System.out.println("_______ _______ _     _ _______ __   _  _____   ______");
		System.out.println("|  |  | |_____| |____/  |_____| | \\  | |_____  |  ____");
		System.out.println("|  |  | |     | |    \\_ |     | |  \\_|  _____| |_____|");
		System.out.println();
	}
}
