package com.example.tomwells.loginsystem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        openDB();

         final Button Login = (Button) findViewById(R.id.Login);
         final EditText username = (EditText) findViewById(R.id.editusername);
         final EditText password = (EditText) findViewById(R.id.editpassword);

         Login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String Username;
                 String Password;

                 Username = username.getText().toString();
                 Password = password.getText().toString();

                  Cursor cursor = myDb.getAllRows();

                 if (cursor.moveToFirst()) {
                     do{
                        // Toast.makeText(Login.this, cursor.getString(myDb.COL_USERNAME), Toast.LENGTH_LONG).show();
                         if(cursor.getString(myDb.COL_USERNAME).equals(Username)) {
                             if(cursor.getString(myDb.COL_PASSWORD).equals(Password)){
                                // Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                                 Intent nextScreen = new Intent(getApplicationContext(), LoginSuccessful.class);
                                 startActivity(nextScreen);
                             }else {
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
                if(username.getText().toString().equals("")){
                    Toast.makeText(Login.this, "Please enter your username", Toast.LENGTH_LONG).show();
                }
                else {
                    Cursor cursor = myDb.getAllRows();
                    boolean found = false;
                    if(cursor.moveToFirst()){
                        do{
                            if(cursor.getString(myDb.COL_USERNAME).equals(username.getText().toString())){
                                found = true;

                                int id = cursor.getInt(myDb.COL_ROWID);
                                Bundle basket = new Bundle();
                                basket.putInt("userID", id);

                                Intent nextScreen = new Intent(getApplicationContext(), Recover.class);
                                nextScreen.putExtras(basket);
                                startActivity(nextScreen);
                            }
                        }
                        while (cursor.moveToNext());
                    }
                    if(!found){
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    private void openDB() {
        //Opens the database
        myDb = new Database(this);
        myDb.open();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnCLKREG(View v){
        Toast.makeText(Login.this, "swag", Toast.LENGTH_LONG).show();
    }
}
