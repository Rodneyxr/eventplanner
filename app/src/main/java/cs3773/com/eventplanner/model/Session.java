package cs3773.com.eventplanner.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cs3773.com.eventplanner.server.ServerLink;
import cs3773.com.eventplanner.server.ServerRequest;
import cs3773.com.eventplanner.server.ServerRequestException;

/**
 * Created by Rodney on 4/8/2015.
 * <p/>
 * This class will manage the current session. In charge of what the user can do.
 */
public class Session {

    private static Account account;

    /**
     * @param username the employee's username
     * @param password the employee's hashed password
     * @return true if the account is successfully created
     */
    public static boolean setAccount(String username, String password) {
        try {
            ServerRequest request = new ServerRequest(ServerLink.GET_ACCOUNT);
            request.put("username", username);
            request.put("password", password);
            request.send();

            String result = request.getResponse();
            if (result.equals("false")) {
                return false;
            }

            // parse the result
            JSONObject page = new JSONObject(result);
            EmployeeAccount employeeAccount = new EmployeeAccount();
            employeeAccount.setUsername(page.getString("username"));
            employeeAccount.setAccountNumber(UUID.fromString(page.getString("account_id")));
            employeeAccount.setPhoneNumber(page.getString("phone_number"));
            employeeAccount.setEmail(page.getString("email"));
            employeeAccount.setFullName(page.getString("full_name"));
            employeeAccount.setRole(Role.valueOf(page.getString("role")));

            // set the account active
            account = employeeAccount;

        } catch (ServerRequestException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (JSONException je) {
            je.printStackTrace();
            return false;
        }

        return true;
    }

    public static Account getAccount() {
        return account;
    }

}
