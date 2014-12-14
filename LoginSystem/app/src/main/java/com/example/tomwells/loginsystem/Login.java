package com.example.tomwells.loginsystem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends Activity {

    Database myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Open the database
        openDB();

        //Grab the Button and EditText's from the GUI
        final Button Login = (Button) findViewById(R.id.Login);
        final EditText username = (EditText) findViewById(R.id.editusername);
        final EditText password = (EditText) findViewById(R.id.editpassword);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Username;
                String Password;

                //Set strings equal to the text in EditText fields.
                Username = username.getText().toString();
                Password = password.getText().toString();

                //Cursor to get all rows from the database
                Cursor cursor = myDb.getAllRows();

                if (cursor.moveToFirst()) {
                    do {
                        // Toast.makeText(Login.this, cursor.getString(myDb.COL_USERNAME), Toast.LENGTH_LONG).show();
                        if (cursor.getString(myDb.COL_USERNAME).equals(Username)) {
                            if (cursor.getString(myDb.COL_PASSWORD).equals(Password)) {
                                // Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent nextScreen = new Intent(getApplicationContext(), LoginSuccessful.class);
                                startActivity(nextScreen);
                            } else {
                                Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    }
                    while (cursor.moveToNext());
                }
            }
        });

        TextView ForgottenPassword = (TextView) findViewById(R.id.ForgottenPassword);

        ForgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the username edittext is empty show this error message
                if (username.getText().toString().equals("")) {
                    Toast.makeText(Login.this, "Please enter your username", Toast.LENGTH_LONG).show();
                } else {
                    //Make a cursor to get all the rows from the database
                    Cursor cursor = myDb.getAllRows();
                    //Creating a found boolean
                    boolean found = false;

                    if (cursor.moveToFirst()) {
                        do {
                            //If the Username exists
                            if (cursor.getString(myDb.COL_USERNAME).equals(username.getText().toString())) {
                                //Set found to true
                                found = true;

                                //get the ID of the row and put it into a bundle
                                int id = cursor.getInt(myDb.COL_ROWID);
                                Bundle basket = new Bundle();
                                basket.putInt("userID", id);

                                //Make an intent to move screens
                                Intent nextScreen = new Intent(getApplicationContext(), Recover.class);
                                nextScreen.putExtras(basket);
                                startActivity(nextScreen);
                            }
                        }
                        while (cursor.moveToNext());
                    }
                    //If username is not found show this error message
                    if (!found) {
                        Toast.makeText(Login.this, "Username does not exist", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        TextView Reg = (TextView) findViewById(R.id.Register);

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Makes an intent to move activity
                Intent nextScreen = new Intent(getApplicationContext(), Register.class);
                //Moves to activity (Register.java)
                startActivity(nextScreen);

            }
        });
    }

    private void openDB() {
        //Opens the database
        myDb = new Database(this);
        myDb.open();
    }
}
