package com.example.tomwells.contactsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SecondScreen extends Activity {

    //Making an instance of the Database.java
    Database myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        //Open the database
        openDB();

        //Define the button editbutton in the code
        Button add = (Button) findViewById(R.id.add);

        //Listening to button event
        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                // Declaring contact's variables
                String FirstName;
                String LastName;
                String Numberstr;
                String Email;

                //Declaring the Edit Text's on the GUI
                EditText First = (EditText) findViewById(R.id.FirstName);
                EditText Last = (EditText) findViewById(R.id.LastName);
                EditText Numb = (EditText) findViewById(R.id.MobileNum);
                EditText email = (EditText) findViewById(R.id.Email);

                //Setting variables equal to text in the Edit Text's
                FirstName = First.getText().toString();
                LastName = Last.getText().toString();
                Numberstr = Numb.getText().toString();
                Email = email.getText().toString();

                //Error checking
                Boolean NoError = false;

                /*The following if statements check that the user has entered something in each
                  field. If they fail to do so, an error message will appear on screen, while
                  all fields that haven't been filled in will have their text changed to red */
                if (FirstName.equals("")) {
                    First.setHintTextColor(Color.parseColor("#FF0000"));
                    NoError = true;
                }
                if (LastName.equals("")) {
                    Last.setHintTextColor(Color.parseColor("#FF0000"));
                    NoError = true;
                }
                if (Numberstr.equals("")) {
                    Numb.setHintTextColor(Color.parseColor("#FF0000"));
                    NoError = true;
                }
                if (Email.equals("")) {
                    email.setHintTextColor(Color.parseColor("#FF0000"));
                    NoError = true;
                }
                if (NoError) {
                    Toast.makeText(SecondScreen.this, "Please fill all required fields.", Toast.LENGTH_LONG).show();
                } else {
                    //Insert the contact into the database.
                    myDb.insertRow(FirstName, LastName, Numberstr, Email);

                    Intent Contacts = new Intent(getApplicationContext(), FirstScreen.class);
                    startActivity(Contacts);
                }

            }
        });

        //Grabs home button from the GUI
        Button home = (Button) findViewById(R.id.home);

        //Runs when the home button is clicked
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Makes an intent to start a new activity (home.class)
                Intent HomeScreen = new Intent(getApplicationContext(), home.class);
                startActivity(HomeScreen);
            }
        });
    }

    @Override
    //When the app is closed
    protected void onDestroy() {
        super.onDestroy();
        //Closes the database
        closeDB();
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
