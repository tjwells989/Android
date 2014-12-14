package com.example.tomwells.contactsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.database.Cursor;

import java.util.ArrayList;

import static com.example.tomwells.contactsapp.R.layout.*;

public class FirstScreen extends Activity {

    //Making an instance of the Database.java
    Database myDb;

    @Override
    //Method runs when the activity is created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_first_screen);

        //Opens the database
        openDB();

        //Fills the list view with data from the database
        filllistview();

        //Method that creates an onClickListener for the listview items
        registerclickonlist();

        Button add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddScreen = new Intent(getApplicationContext(), SecondScreen.class);
                startActivity(AddScreen);
            }
        });

        final Button home = (Button) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeScreen = new Intent(getApplicationContext(), home.class);
                startActivity(HomeScreen);
            }
        });


    }

    //Creates a ArrayList. Used over an normal array as it doesn't need a predefined size.
    final ArrayList<String> myitems = new ArrayList<String>();
    final ArrayList<Integer> myID = new ArrayList<Integer>();

    //This method grabes the code from the database and puts in the list view (Uses other methods).
    private void filllistview() {
        //Creates a cursor to go through the database
        Cursor cursor = myDb.getAllRows();
        //calls the method that puts the database data into the listview
        displayRecordSet(cursor);

        //Adds the array to the list view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, myitems);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

    }

    @Override
    //Runs when the activity is brought back to the screen
    protected void onResume() {
        //creates an adapter for the listview.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.the_items, myitems);
        super.onResume();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        //deletes all the data in the list view
        adapter.clear();
        //fills the list view again, making sure all records are up to date
        filllistview();

    }

    //Creating variables for the contact's
    public int RowID;
    public String name;
    public String lname;
    public String mob;
    public String email;

    //Setting a listener to activate when an item in the list view is clicked
    private void registerclickonlist() {
        final ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    Cursor cursor = myDb.getRow(Integer.valueOf(myID.get(i)));
                    RowID = cursor.getInt(myDb.COL_ROWID);
                    email = cursor.getString(myDb.COL_EMAIL);
                    mob = cursor.getString(myDb.COL_MOBILENUM);
                    lname = cursor.getString(myDb.COL_LASTNAME);
                    name = cursor.getString(myDb.COL_NAME);

                    //Creates a bundle to carry objects
                    Bundle basket = new Bundle();
                    //Add the variables to the bundle
                    basket.putInt("RowID", RowID);
                    basket.putString("name", name);
                    basket.putString("lname", lname);
                    basket.putString("mob", mob);
                    basket.putString("email", email);

                    //Makes an intent to move activity
                    Intent nextScreen = new Intent(getApplicationContext(), MoreInfo.class);
                    //Adds the bundle to the intent
                    nextScreen.putExtras(basket);
                    //Moves to activity (MoreInfo.java)
                    startActivity(nextScreen);
                } catch (Exception ex) {
                }

            }
        });

    }

    //Displays the entire record set to the listview
    private void displayRecordSet(Cursor cursor) {
        // Reset cursor to start, checking to see if there's data.
        if (cursor.moveToFirst()) {
            do {
                //adds each row of the database to the list array, only taking their name and lastname.
                myitems.add(cursor.getString(myDb.COL_NAME) + " " + cursor.getString(myDb.COL_LASTNAME));
                myID.add(cursor.getInt(myDb.COL_ROWID));
            } while (cursor.moveToNext());
        }
        // Close the cursor to avoid a resource leak.
        cursor.close();
    }

    @Override
    //When the app is closed
    protected void onDestroy() {
        super.onDestroy();
        //closes the database
        closeDB();
    }

    private void openDB() {
        //Opens the database
        myDb = new Database(this);
        myDb.open();
    }

    //Method for closing the database
    private void closeDB() {
        myDb.close();
    }

}
