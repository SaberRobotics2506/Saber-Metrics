package com.example.frc_scouting

//import android utilities
import android.os.Bundle

//import androidx utilities
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

//Import android widgets
import android.widget.Button;
import android.widget.ProgressBar
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

    private fun onFormSubmission() {
        //Warn the user not to disable bluetooth or to turn off the device
        Toast.makeText(this@MainActivity, "⚠ DO NOT DISABLE BLUETOOTH OR CLOSE THE APP ⚠", Toast.LENGTH_LONG).show()

        findViewById<Button>(R.id.Send).isVisible = false; //Remove the send button to prevent another button press
        findViewById<ProgressBar>(R.id.progressBar).isVisible = true; //Show the loading bar
        findViewById<ProgressBar>(R.id.progressBar2).isVisible = true; //Show the spinning thing


        //Toast.makeText(this@MainActivity, "Your data has been sent successfully!", Toast.LENGTH_SHORT).show() will use later
    }
}