package com.example.tomwells.contactsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Grabs the view button from the GUI
        Button view = (Button) findViewById(R.id.view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creates an intent to change the activity to FirstScreen.class
                Intent ViewScreen = new Intent(getApplicationContext(), FirstScreen.class);
                startActivity(ViewScreen);
            }
        });

        //Grabs the add button from the GUI
        Button add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sets an intent to change the activity to SecondScreen.class
                Intent AddScreen = new Intent(getApplicationContext(), SecondScreen.class);
                startActivity(AddScreen);

            }
        });
    }
}
