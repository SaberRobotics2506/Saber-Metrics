package com.example.frc_scouting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.Button;
import android.widget.Toast;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar)) This line is commented because with it the app didn't build correctly... Since idk what this does I keep it

        // get reference to form submission button
        val sendButton = findViewById<Button>(R.id.Send)

        // set on-click listener
        sendButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            onFormSubmission()
        }
    }

    fun onFormSubmission() {
        Toast.makeText(this@MainActivity, "The data you have entered is being sent to the server.\nDO NOT TURN OFF YOUR DEVICE OR DISABLE BLUETOOTH.", Toast.LENGTH_LONG).show()
    }
}