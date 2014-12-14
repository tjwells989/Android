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


public class Recover extends Activity {

    Database myDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        openDB();

        Bundle gotbasket = getIntent().getExtras();
        final int id = gotbasket.getInt("userID");

       final Cursor cursor = myDb.getRow(id);
        String Question = cursor.getString(myDb.COL_RECOVERYQUESTION);

        TextView question = (TextView) findViewById(R.id.question);
        question.setText(Question);

        Button changepassword = (Button) findViewById(R.id.ChangePassword);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText password = (EditText) findViewById(R.id.EditPassword);
                EditText cpassword = (EditText) findViewById(R.id.EditCpassword);
                EditText answer = (EditText) findViewById(R.id.EditAnswer);

                if(answer.getText().toString().equals(cursor.getString(myDb.COL_RECOVERYANSWER))) {
                    if (password.getText().toString().equals(cpassword.getText().toString())) {
                        myDb.updatePassword(id, password.getText().toString());
                        Toast.makeText(Recover.this, "Password updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Recover.this, "Passwords do NOT match", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Recover.this, "Incorrect answer to the question", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button home = (Button) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginScreen = new Intent(getApplicationContext(), Login.class);
                startActivity(LoginScreen);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recover, menu);
        return true;
    }

    private void openDB() {
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
}
