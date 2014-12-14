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


public class Recover extends Activity {

    Database myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        //Opens the database
        openDB();

        //Opens the bundle and gets the ints
        Bundle gotbasket = getIntent().getExtras();
        final int id = gotbasket.getInt("userID");

        //Cursor that returns the row the matches the ID
        final Cursor cursor = myDb.getRow(id);
        //Gets the recovery question from that row
        String Question = cursor.getString(myDb.COL_RECOVERYQUESTION);

        //Sets the TextView equal to the question
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(Question);

        Button changepassword = (Button) findViewById(R.id.ChangePassword);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText password = (EditText) findViewById(R.id.EditPassword);
                EditText cpassword = (EditText) findViewById(R.id.EditCpassword);
                EditText answer = (EditText) findViewById(R.id.EditAnswer);

                //Checks if the answer matches the answer in the database and also check that the
                //passwords match
                if (answer.getText().toString().equals(cursor.getString(myDb.COL_RECOVERYANSWER))) {
                    if (password.getText().toString().equals(cpassword.getText().toString())) {
                        myDb.updatePassword(id, password.getText().toString());
                        Toast.makeText(Recover.this, "Password updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Recover.this, "Passwords do NOT match", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Recover.this, "Incorrect answer to the question", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button home = (Button) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sends the user back to the home page
                Intent LoginScreen = new Intent(getApplicationContext(), Login.class);
                startActivity(LoginScreen);
            }
        });
    }

    private void openDB() {
        myDb = new Database(this);
        myDb.open();
    }
}
