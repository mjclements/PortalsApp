package com.example.mjclements.myapplication;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mjclements.myapplication.DataStorageWrapper;
import com.example.mjclements.myapplication.R;
import com.example.mjclements.myapplication.User;


public class AccountSetup extends welcome_screen {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setup);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataStorageWrapper db = new DataStorageWrapper(getApplicationContext());
                EditText name = (EditText) findViewById(R.id.name);
                EditText password = (EditText) findViewById(R.id.password);
                EditText email = (EditText) findViewById(R.id.email);
                String nameStr = name.getText().toString();
                String passStr = password.getText().toString();
                String emailStr = email.getText().toString();
                User user1 = new User( nameStr, emailStr );
                db.insert_User(user1, passStr);

                db.close();
//                DataStorageWrapper ac = new DataStorageWrapper(getApplicationContext());
//                Toast.makeText(getApplicationContext(), getApplicationContext().getApplicationContext().getFilesDir().getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                User userGet = ac.get_User(user1.ID);
//                ac.close();
//                TextView score = (TextView) findViewById(R.id.password);
//                score.setText(userGet.name);
               //do something
            }
        });

    }





}
