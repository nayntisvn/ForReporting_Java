package com.example.aweso.sampleproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOnClick(View v)
    {
        username = (EditText) findViewById(R.id.txt_Username);

        Toast toast = new Toast(getApplicationContext());
        toast.makeText(MainActivity.this, "Logging in as " + username.getText(), toast.LENGTH_SHORT).show();

        UserProfile.currentUserName = username.getText().toString();

        Intent myIntent = new Intent(this, Chat.class);
        startActivity(myIntent);
    }

    //hello pls
}
