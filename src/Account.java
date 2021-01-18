import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Account {
    private String loginID;
    private String password;
    private String customerId;
    private Date joinDate;

    public Account(){};
    public Account(String loginID, String password, String customerId){
        if(!Customer.checkCustomerId(customerId)){
            throw new IllegalArgumentException("Unable to create Account. Invalid Customer ID");
        }
        else{
            this.loginID = loginID;
            this.password = password;
            this.customerId = customerId;
            this.joinDate = new Date();
        }
    }
    public String getLoginId(){ 
		return this.loginID; 
	}
    public String getPassword(){
        return this.password;
    }
    public String getCustomerId() { 
		return this.customerId; 
	}
    public Date getJoinDate() { 
		return this.joinDate; 
	}
    public void setLoginId(String loginID){
        this.loginID = loginID;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setCustomerId(String customerId) {
        if(!Customer.checkCustomerId(customerId)){
            throw new IllegalArgumentException("Unable to set Customer ID. Invalid Customer ID");
        }
        else{
			this.customerId = customerId;
		}
	}
    public void setJoinDate(String joinDate){
        SimpleDateFormat formatter=new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        try {
            this.joinDate = formatter.parse(joinDate);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }
    public String toString(){
        return this.loginID + " " +  this.password + " " + this.customerId;
    }

}