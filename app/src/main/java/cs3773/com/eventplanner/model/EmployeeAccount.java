package cs3773.com.eventplanner.model;

import java.util.UUID;

/**
 * Created by Rodney on 3/26/2015.
 * <p/>
 * This class will hold information for employee accounts
 */
public class EmployeeAccount extends Account {

    public EmployeeAccount() {

    }

    public EmployeeAccount(UUID accountNumber, String username, String password, String fullName, String email, String phoneNumber) {
        super(accountNumber, username, password, fullName, email, phoneNumber);
    }

    @Override
    public boolean saveAccount() {
        return false;
    }

    @Override
    public boolean deleteAccount() {
        return false;
    }

}
