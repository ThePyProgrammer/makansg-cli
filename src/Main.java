import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Main {
	private static Database db;
	private static Security sec;
	private static Customer cust;
	private static String postal;
	private static Scanner s;
	private static ArrayList<Supplier> suppliers;
	public static void main(String[] args) {
		s = new Scanner(System.in);
		db = new Database("Customer.txt", "Supplier.txt", "Postal.txt");  // Create database object to manage database
		sec = new Security("Secure.txt");
		String login, password;
		do {
			Database.displayLogo();
			System.out.print("Enter login: ");
			login = s.nextLine();
			System.out.print("Enter password: ");
			password = s.nextLine();

		} while (!sec.checkLogin(login, password));
		cust = db.getCustomer(Security.getCurrentCustomerId());
		getPostal();
		var relatedSectors = db.getRelatedSector(postal);
		System.out.printf("Searching for food items at %s with related sectors "+relatedSectors+"...%n", db.getArea(postal));
		db.addListedItems(postal);
		suppliers = db.getListedItems();
		Collections.sort(suppliers, new SortByID());
		if (suppliers.size() == 0) {
			System.out.println("Sorry, there isn't any food available within your area!");
			return ;
		}
		prog();

	}

	public static void getPostal() {
		while (true) {
			System.out.print("Enter Postal Code: ");
			postal = s.nextLine();
			if (!db.checkPostal(postal)) {
				System.out.println("Invalid Postal Code!");
				continue;
			} else if (!db.checkWithinDistrictArea(postal)) {
				System.out.println("Postal code entered is not within the district area of your delivery address!");
				continue;
			} else break;
		}
	}

	public static void prog() {
		String current = "";
		int index = 0;
		for(Supplier supplier: suppliers) {
			index++;
			if (current.length() == 0 || !supplier.getSupplierID().equals(current)) {
				current = supplier.getSupplierID();
				System.out.printf("%n%s%n------------------------------------------------%n", current);
			}
			System.out.printf("[%3d] %s %-25s$%.2f%n", index, supplier.getFoodID(), supplier.getFood(), supplier.getPrice());
		}
		System.out.print("\n\nPLEASE MAKE YOUR ORDER BY ENTERING THE ITEM NUMBER IN [ ].\nENTER R TO REVIEW ORDER, X TO QUIT: ");
		String entry = s.nextLine();
		while (true) {
			if (entry.length() == 0) System.out.println("Entry cannot be empty!\n");
			else if (entry.equals("R")) {
				if (db.getShoppingCart().size() > 0) {
					break;
				} else System.out.println("Your shopping cart is empty!\n");
			}
			else if (entry.equals("X")) {
				System.out.println("System terminated!");
				return ;
			} else {
				try {
					int value = Integer.parseInt(entry)-1;
					if (value < 0 || value >= index) {
						System.out.println("Invalid Selection!\n");
					} else {
						db.getShoppingCart().add(suppliers.get(value));
						System.out.println("ADDED: "+suppliers.get(value)+"\n");
					}
				} catch (Exception ex) {
					System.out.println("Invalid Entry!\n");
				}
			}
			System.out.print("PLEASE MAKE YOUR ORDER BY ENTERING THE ITEM NUMBER IN [ ].\nENTER R TO REVIEW ORDER, X TO QUIT: ");
			entry = s.nextLine();
		}
		db.getOrderSummary();
		System.out.print("\n\nENTER ITEM NUMBER IN [ ] TO DELETE.\nENTER C TO CONFIRM ORDER: ");
		entry = s.nextLine();
		while (true) {
			if (entry.strip().equals("C")) {
				System.out.println("\nYOUR ORDER IS CONFIRM. THANK YOU!");
				db.writeTransaction("Transaction.txt");
				System.out.println("TRANSACTION COMPLETED SUCCESSFULLY.\nYOUR FOOD WILL BE DELIVERED TO:\n\n"+cust.getName()+'\n'+cust.getAddress().toUpperCase()+"\nSINGAPORE "+cust.getPostalCode());
				System.out.print("\nDO YOU WANT TO MAKE ANOTHER ORDER? [Y/N] ");
				String yesno = s.nextLine();
				while (true) {
					if (yesno.equals("Y")) {
						prog();
						return ;
					}
					else if (yesno.equals("N")) {
						System.out.println("System terminated!");
						return ;
					} else if (yesno.length() == 0) System.out.println("Entry cannot be empty!\n");
					else System.out.println("Invalid Entry!\n");
					System.out.print("DO YOU WANT TO MAKE ANOTHER ORDER? [Y/N] ");
					yesno = s.nextLine();
				}
			}
			else {
				try {
					int value = Integer.parseInt(entry)-1;
					if (value < 0 || value >= db.getShoppingCart().size()) {
						System.out.println("Invalid Selection!");
					} else {
						var temp = db.getShoppingCart().get(value);
						System.out.println("DELETED: "+temp+"");
						db.getShoppingCart().remove(value);
					}
				} catch (Exception ex) {
					System.out.println("Invalid Entry!");
				}
			}
			if (db.getShoppingCart().size() == 0) {
				System.out.println("Shopping cart is empty!! Press any key to continue.");
				s.nextLine();
				System.out.println();
				prog();
				return ;
			}
			System.out.println();
			db.getOrderSummary();
			System.out.print("\n\nENTER ITEM NUMBER IN [ ] TO DELETE.\nENTER C TO CONFIRM ORDER: ");
			entry = s.nextLine();
		}
	}
}

class SortByID implements Comparator<Supplier>
{
	// Used for sorting in ascending order of
	// roll number
	public int compare(Supplier a, Supplier b)
	{
		return (a.getSupplierID().compareTo(b.getSupplierID()));
	}
}