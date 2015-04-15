package cs3773.com.eventplanner.model;

import java.util.UUID;

/**
 * Created by Rodney on 3/26/2015.
 * <p/>
 * This abstract class will hold general account information that applies to all types of accounts.
 */
public abstract class Account {

    private UUID accountNumber;
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    private Role role;

    public Account() {

    }

    public Account(UUID accountNumber, String username, String fullName, String email, String phoneNumber, Role role) {
        setAccountNumber(accountNumber);
        setUsername(username);
        setFullName(fullName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setRole(role);
    }

    public UUID getAccountNumber() {
        return accountNumber;
    }

    protected void setAccountNumber(UUID accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public abstract boolean saveAccount();

    public abstract boolean deleteAccount();
}
