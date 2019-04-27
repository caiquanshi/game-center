package project.csc207;

import java.io.Serializable;
import java.util.HashMap;

public class AccountManager implements Serializable {

    /**
     * All accounts with key account Id and variable Account
     */
    private HashMap<String, Account> allAccount;

    private Account currentAccount;

    AccountManager() {
        this.currentAccount = null;
        this.allAccount = null;
    }

    /**
     * Get the current account
     *
     * @return an Account that is the current logged in account
     */
    public Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Get all accounts
     *
     * @return a Hashmap of all accounts
     */
    public HashMap<String, Account> getAllAccount() {
        return allAccount;
    }

    /**
     * Set current account
     *
     * @param id id of the account to be set as current account
     */
    void setCurrentAccount(String id) {

        this.currentAccount = allAccount.get(id);
    }

    /**
     * Check if user id has been used
     *
     * @param id id of the new user
     * @return whether the new user id is usable
     */
    boolean notNewUser(String id) {
        return this.allAccount.containsKey(id);
    }

    /**
     * Update current account information to allAccount
     */
    public void updateAccount() {
        allAccount.put(currentAccount.getUserName(), currentAccount);
    }

    /**
     * Return true if the password is correct for indexing user id
     *
     * @param id       id of the user that attempts to log in
     * @param password password imputed by the user
     * @return whether the password for the user is correct
     */
    boolean rightPassword(String id, String password) {
        try{
            return this.allAccount.get(id).getPassword().equals(password);
        }catch (NullPointerException e){
            return false;
        }
    }

    /**
     * Set a new user and add it to allAccount
     *
     * @param id       id of the new user
     * @param password password of the new user
     */
    void signUp(String id, String password) {
        this.allAccount.put(id, new Account(id, password));
    }

    /**
     * Set allAccount
     *
     * @param hMap the HashMap to be set as allAccount
     */
    void setAllAccount(HashMap<String, Account> hMap) {
        this.allAccount = hMap;
    }
}
