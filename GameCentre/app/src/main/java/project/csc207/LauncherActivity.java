package project.csc207;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class LauncherActivity extends AppCompatActivity implements SaveLoad {


    /**
     * The main save file
     */
    public static final String SAVE_FILENAME = "save_file.ser";

    /**
     * The account manager
     */
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        loadFromFile(LauncherActivity.SAVE_FILENAME);
        if (accountManager.getAllAccount() == null) {
            accountManager.setAllAccount(new HashMap<String, Account>());
        }
        addLogInButtonListener();
        addSignUpButtonListener();
    }

    /**
     * Activate the log in button
     */
    private void addLogInButtonListener() {
        Button btnLogin = findViewById(R.id.LogInButton);
        final EditText etUserName = findViewById(R.id.UsernameBox);
        final EditText etPassword = findViewById(R.id.PasswordBox);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                if (accountManager.notNewUser(user) && accountManager.rightPassword(user, password)) {
                    Intent accountActivity = new Intent(LauncherActivity.this, AccountActivity.class);
                    accountManager.setCurrentAccount(user);
                    saveToFile(SAVE_FILENAME);
                    startActivity(accountActivity);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        Button btnRegister = findViewById(R.id.SignUpButton);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(LauncherActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
            }
        });
    }

    @Override
    public void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) input.readObject();
                inputStream.close();
            }
        } catch (Exception e) {
            accountManager = new AccountManager();
            System.out.println("not read ");
        }
    }

    @Override
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(accountManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}