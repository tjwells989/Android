package com.example.tomwells.loginsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class RegComplete extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_complete);

        //Grabs home button from the GUI
        Button home = (Button) findViewById(R.id.home);

        //Runs when the home button is clicked
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creates an intent to move to the next screen
                Intent nextscreen = new Intent(getApplicationContext(), Login.class);
                startActivity(nextscreen);
            }
        });
    }
}
