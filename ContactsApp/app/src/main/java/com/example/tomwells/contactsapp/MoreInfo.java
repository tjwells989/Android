package com.example.tomwells.contactsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;


public class MoreInfo extends Activity {

    //Declearing variables
    int Rowid;
    String name;
    String lname;
    String email;
    String mob;
    Database MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        //Opens the Database
        openDB();

        //Declearing the TextViews and EditText's from the GUI
        final TextView textElement = (TextView) findViewById(R.id.textView);
        final TextView textElement2 = (TextView) findViewById(R.id.textView2);
        final TextView textElement3 = (TextView) findViewById(R.id.textView3);
        final TextView textElement4 = (TextView) findViewById(R.id.textView4);
        final EditText textElement5 = (EditText) findViewById(R.id.editfirstname);
        final EditText textElement6 = (EditText) findViewById(R.id.editlastname);
        final EditText textElement7 = (EditText) findViewById(R.id.editemail);
        final EditText textElement8 = (EditText) findViewById(R.id.editmobilenum);

        //Grabs the bundle from the previous activity containing the strings we added
        Bundle gotbasket = getIntent().getExtras();
        //Gets Int's and String's from the Bundle
        Rowid = gotbasket.getInt("RowID");
        name = gotbasket.getString("name");
        lname = gotbasket.getString("lname");
        email = gotbasket.getString("email");
        mob = gotbasket.getString("mob");

        //Setting text of textViews and EditText's
        textElement.setText(name);
        textElement2.setText(lname);
        textElement3.setText(email);
        textElement4.setText(mob);
        textElement5.setText(name);
        textElement6.setText(lname);
        textElement7.setText(email);
        textElement8.setText(mob);

        //Making the EditText fields invisible
        textElement5.setVisibility(View.INVISIBLE);
        textElement6.setVisibility(View.INVISIBLE);
        textElement7.setVisibility(View.INVISIBLE);
        textElement8.setVisibility(View.INVISIBLE);

        //Grabs button (edit) from the GUI
        Button edit = (Button) findViewById(R.id.edit);

        //Runs when the edit button is clicked
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textElement.setVisibility(View.INVISIBLE);
                textElement2.setVisibility(View.INVISIBLE);
                textElement3.setVisibility(View.INVISIBLE);
                textElement4.setVisibility(View.INVISIBLE);
                textElement5.setVisibility(View.VISIBLE);
                textElement6.setVisibility(View.VISIBLE);
                textElement7.setVisibility(View.VISIBLE);
                textElement8.setVisibility(View.VISIBLE);
            }
        });

        //Grabs button (save) from the GUI
        Button save = (Button) findViewById(R.id.save);

        //Runs when the save button is clicked
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Makes strings equal to what the user has entered in the EditText fields.
                name = textElement5.getText().toString();
                lname = textElement6.getText().toString();
                email = textElement7.getText().toString();
                mob = textElement8.getText().toString();

                textElement.setText(name);
                textElement2.setText(lname);
                textElement3.setText(email);
                textElement4.setText(mob);

                //Updates the data in the database
                MyDB.updateRow(Rowid, name, lname, email, mob);

                //Makes EditText Invisable and Textview Visable
                textElement.setVisibility(View.VISIBLE);
                textElement2.setVisibility(View.VISIBLE);
                textElement3.setVisibility(View.VISIBLE);
                textElement4.setVisibility(View.VISIBLE);
                textElement5.setVisibility(View.INVISIBLE);
                textElement6.setVisibility(View.INVISIBLE);
                textElement7.setVisibility(View.INVISIBLE);
                textElement8.setVisibility(View.INVISIBLE);
            }
        });

        //Grabs button (delete) from the GUI
        Button delete = (Button) findViewById(R.id.delete);

        //Runs when delte button is clicked
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MoreInfo.this)
                        .setTitle("Delete contact")
                        .setMessage("Are you sure you want to delete this contact?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MyDB.deleteRow(Rowid);
                                Intent firstscreen = new Intent(getApplicationContext(),FirstScreen.class);
                                startActivity(firstscreen);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        //Grabs button (call) from GUI
        Button call = (Button) findViewById(R.id.call);

        //runs when the call button is clicked
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gets mobile number from GUI
                String phone = textElement4.getText().toString();

                //Creates an intent to call the number. Also starts this intent.
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        Button home = (Button) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent nextscreen = new Intent(getApplicationContext(), home.class);
               startActivity(nextscreen);
            }
        });

    }

    private void openDB() {
        //Opens the database
        MyDB = new Database(this);
        MyDB.open();
    }
}
