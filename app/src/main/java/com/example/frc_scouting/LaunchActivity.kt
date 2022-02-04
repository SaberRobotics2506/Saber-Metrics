package com.example.frc_scouting

//imports
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class LaunchActivity : AppCompatActivity() {

    private var dataEntryMode: Boolean = true; //True means that the input UI is active

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar)) This line is commented because with it the app didn't build correctly... Since idk what this does I keep it

        // get reference to form submission button
        val sendButton = findViewById<Button>(R.id.LetsGo)

        // set on-click listener
        sendButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            onFormSubmission()
        }
    }

    private fun onFormSubmission() {
        val switchActivityIntent = Intent(this, DataEntryActivity::class.java)
        startActivity(switchActivityIntent)
    }
}