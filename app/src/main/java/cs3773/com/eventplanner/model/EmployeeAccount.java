package cs3773.com.eventplanner.model;

import java.util.UUID;

import cs3773.com.eventplanner.server.ServerLink;
import cs3773.com.eventplanner.server.ServerRequest;
import cs3773.com.eventplanner.server.ServerRequestException;

/**
 * Created by Rodney on 3/26/2015.
 * <p/>
 * This class will hold information for employee accounts
 */
public class EmployeeAccount extends Account {

    public EmployeeAccount() {
    }

    public EmployeeAccount(UUID accountNumber, String username, String fullName, String email, String phoneNumber, Role role) {
        super(accountNumber, username, fullName, email, phoneNumber, role);
    }

    @Override
    public boolean saveAccount() {
        ServerRequest request = new ServerRequest(ServerLink.UPDATE_ACCOUNT);
        request.put("account_id", getAccountNumber().toString());
        request.put("phone_number", getPhoneNumber());
        request.put("email", getEmail());
        request.put("full_name", getFullName());

        try {
            request.send();
        } catch (ServerRequestException sre) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteAccount() {
        return false;
    }

}
