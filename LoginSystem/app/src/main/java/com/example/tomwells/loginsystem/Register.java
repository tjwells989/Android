package com.example.tomwells.loginsystem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Register extends Activity {

    Database myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Grabs register button from the GUI
        Button reg = (Button) findViewById(R.id.Register);

        //Runs when the register button is clicked
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Grabs EditText's and Spinner from GUI
                EditText username = (EditText) findViewById(R.id.EditUsername);
                EditText password = (EditText) findViewById(R.id.EditPassword);
                EditText cpassword = (EditText) findViewById(R.id.EditCpassword);
                Spinner question = (Spinner) findViewById(R.id.spinner);
                EditText answer = (EditText) findViewById(R.id.EditAnswer);

                //Defines strings and makes them equal to EditText
                String Username = username.getText().toString();
                String Password = password.getText().toString();
                String Cpassword = cpassword.getText().toString();
                String RecoverQ = question.getSelectedItem().toString();
                String RecoveryA = answer.getText().toString();

                //Checks that the passwords match
                if (Password.equals(Cpassword)) {
                    //Opens the database
                    openDB();
                    //Creates a cursor to go through the database. Returns all rows
                    Cursor cursor = myDb.getAllRows();
                    boolean duplicate = false;
                    //Loops through the database row by row
                    if (cursor.moveToFirst()) {
                        do {
                            //If the username is equal to the username in the database
                            if (cursor.getString(myDb.COL_USERNAME).equals(Username)) {
                                //Error to say that the username already exists
                                Toast.makeText(Register.this, "Sorry username already exists", Toast.LENGTH_LONG).show();
                                duplicate = true;
                            }

                        } while (cursor.moveToNext());
                    }
                    if (!duplicate) {
                        //If the username hasn't been used, add a new row in the database
                        myDb.insertRow(Username, Password, RecoverQ, RecoveryA);
                        //Create an intent to move to activity RegComplete.class
                        Intent nextscreen = new Intent(getApplicationContext(), RegComplete.class);
                        startActivity(nextscreen);
                    }
                } else {
                    // MAKE ERROR IMAGE VISIBLE
                    Toast.makeText(Register.this, "Error: Passwords do NOT match", Toast.LENGTH_LONG).show();
                }
            }

        });

        //Gets home button from GUI
        Button home = (Button) findViewById(R.id.home);

        //Runs when the home button is clicked
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creates an intent to move to activity Login.class
                Intent nextscreen = new Intent(getApplicationContext(), Login.class);
                startActivity(nextscreen);
            }
        });
    }

    //Method to open the database
    private void openDB() {
        //Opens the database
        myDb = new Database(this);
        myDb.open();
    }

    //Method to close the database
    private void closeDB() {
        //Closes the database
        myDb.close();
    }
}
