package com.example.frc_scouting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.Button;
import android.widget.Toast;
import androidx.core.view.isVisible

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
            onFormSubmission(sendButton)
        }
    }

    fun onFormSubmission(btn: Button) {

        btn.isVisible = false; //Remove the send button to prevent another button press

        //Warn the user not to disable bluetooth or to turn off the device
        Toast.makeText(this@MainActivity, "⚠ DO NOT DISABLE BLUETOOTH OR CLOSE THE APP ⚠", Toast.LENGTH_LONG).show()

    }
}