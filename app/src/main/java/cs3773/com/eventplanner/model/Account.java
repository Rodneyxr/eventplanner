package cs3773.com.eventplanner.model;

/**
 * Created by Rodney on 3/26/2015.
 * <p/>
 * This abstract class will hold general account information that applies to all types of accounts.
 */
public abstract class Account {

    private String fullName;
    private String phoneNumber;

    public Account(String fullName) {
        this.fullName = fullName;
    }

    protected String getFullName() {
        return fullName;
    }

    protected void setFullName(String fullName) {
        this.fullName = fullName;
    }

    protected String getPhoneNumber() {
        return phoneNumber;
    }

    protected void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
