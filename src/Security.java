import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Security {
    ArrayList<Account> loginDB;
    static String currentCustomerId;
    private ArrayList<String> loginIDs, passwords;

    public ArrayList<Account> getLoginDB() {
        var temp = new ArrayList<Account>();
        for(int i = 0; i < loginDB.size(); i++) {
            temp.add(loginDB.get(i));
        }
        return temp;
    }

    public static String getCurrentCustomerId() {
        return currentCustomerId;
    }

    Security(String filename) {
        loginDB = new ArrayList<>();
        loginIDs = new ArrayList<>();
        passwords = new ArrayList<>();
        loadLoginDB(filename);
        currentCustomerId = loginDB.get(0).getCustomerId();
    }

    public void loadLoginDB(String filename){
        try {
            File file = new File(filename);
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String[] splitted = s.nextLine().trim().split(",");
                loginDB.add(new Account(splitted[0], splitted[1], splitted[2]));
                loginIDs.add(splitted[0]);
                passwords.add(splitted[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean checkLogin(String loginID, String password) {
        if (!loginIDs.contains(loginID)) {
            System.out.print("INVALID LOGIN!\n");
            return false;
        } else if (!passwords.contains(password)) {
            System.out.print("INVALID PASSWORD!\n");
            return false;
        } else {
            System.out.printf("WELCOME %s%n", loginID);
            for(Account a: loginDB) {
                if (a.getLoginId().equals(loginID) && a.getPassword().equals(password)) {
                    currentCustomerId = a.getCustomerId();
                    break;
                }
            }
            return true;
        }
    }
}
