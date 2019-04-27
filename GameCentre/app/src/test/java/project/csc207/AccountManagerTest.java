package project.csc207;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class AccountManagerTest {

    /**
     * Test whether the getter of the account is working
     */
    @Test
    public void getCurrentAccount() {
        AccountManager currentAccountManager = new AccountManager();
        Account testAccount = new Account("1234", "4321");
        HashMap<String, Account> hashAccount = new HashMap<>();
        hashAccount.put("1234", testAccount);

        currentAccountManager.setAllAccount(hashAccount);
        currentAccountManager.setCurrentAccount("1234");
        assertEquals(testAccount, currentAccountManager.getCurrentAccount());
    }

    /**
     * Test whether the getter of the account hash map is working
     */
    @Test
    public void getAllAccount() {
        AccountManager currentAccountManager = new AccountManager();
        Account testAccount = new Account("hello", "4321");
        HashMap<String, Account> hashAccount = new HashMap<>();
        hashAccount.put("hello", testAccount);

        currentAccountManager.setAllAccount(hashAccount);
        assertEquals(hashAccount, currentAccountManager.getAllAccount());

    }

    /**
     * Test whether the setter of the account is working
     */
    @Test
    public void setCurrentAccount() {
        AccountManager currentAccountManager = new AccountManager();
        Account testAccount = new Account("5678", "4321");
        HashMap<String, Account> hashAccount = new HashMap<>();
        hashAccount.put("5678", testAccount);

        currentAccountManager.setAllAccount(hashAccount);
        currentAccountManager.setCurrentAccount("5678");
        assertEquals(testAccount, currentAccountManager.getCurrentAccount());

    }

    /**
     * Test whether the user already exists in hash map that stores all the existing accounts
     */
    @Test
    public void notNewUser() {
        AccountManager currentAccountManager = new AccountManager();
        Account testAccount = new Account("5678", "4321");
        HashMap<String, Account> hashAccount = new HashMap<>();
        hashAccount.put("5678", testAccount);

        currentAccountManager.setAllAccount(hashAccount);
        currentAccountManager.setCurrentAccount("5678");

        assertTrue(currentAccountManager.notNewUser("5678"));
        assertFalse(currentAccountManager.notNewUser("1234"));

    }

    /**
     * Test whether the updateAccount method updates the user info to hash map
     */
    @Test
    public void updateAccount() {
        AccountManager currentAccountManager = new AccountManager();

        assertNull(currentAccountManager.getAllAccount());

        Account testAccount = new Account("hello", "4321");
        HashMap<String, Account> hashAccount = new HashMap<>();
        hashAccount.put("hello", testAccount);

        currentAccountManager.setAllAccount(hashAccount);
        currentAccountManager.setCurrentAccount("hello");
        currentAccountManager.updateAccount();

        assertEquals(testAccount, currentAccountManager.getAllAccount().get("hello"));

    }

    /**
     * Test whether the rightPassword check the username and corresponding password correctly
     */
    @Test
    public void rightPassword() {
        AccountManager currentAccountManager = new AccountManager();
        Account testAccount = new Account("5678", "4321");
        HashMap<String, Account> hashAccount = new HashMap<>();
        hashAccount.put("5678", testAccount);

        currentAccountManager.setAllAccount(hashAccount);
        currentAccountManager.setCurrentAccount("5678");

        assertTrue(currentAccountManager.rightPassword("5678", "4321"));
        assertFalse(currentAccountManager.rightPassword("5678", "21"));
        assertFalse(currentAccountManager.rightPassword("123", "21"));
    }

    /**
     * Test whether the signUp saved the username and passwords in hash map of accounts
     */
    @Test
    public void signUp() {
        AccountManager currentAccountManager = new AccountManager();
        Account testAccount = new Account("5678", "4321");
        HashMap<String, Account> hashAccount = new HashMap<>();

        currentAccountManager.setAllAccount(hashAccount);
        currentAccountManager.signUp("5678", "4321");
        currentAccountManager.setCurrentAccount("5678");

        assertEquals(testAccount.getUserName(), currentAccountManager.getCurrentAccount().getUserName());
        assertEquals(testAccount.getPassword(), currentAccountManager.getCurrentAccount().getPassword());

    }

    /**
     * Test whether the setter of the hash map of account is working
     */
    @Test
    public void setAllAccount() {
        AccountManager currentAccountManager = new AccountManager();
        Account testAccount = new Account("hello", "4321");
        HashMap<String, Account> hashAccount = new HashMap<>();
        hashAccount.put("hello", testAccount);

        currentAccountManager.setAllAccount(hashAccount);
        assertEquals(hashAccount, currentAccountManager.getAllAccount());
    }
}