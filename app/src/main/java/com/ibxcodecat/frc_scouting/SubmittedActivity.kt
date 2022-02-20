package com.ibxcodecat.frc_scouting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Button

class SubmittedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submitted)

        val sendButton = findViewById<Button>(R.id.next)

        sendButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            onNewDataEntry()
        }
    }

    //Used to override the back button to prevent fields from being pre-populated with previous data
    override fun onBackPressed() {
        Toast.makeText(this@SubmittedActivity, "This action is disabled to protect previous data entries... Tap \"New Entry\" Instead...", Toast.LENGTH_LONG).show()
        return;
    }

    private fun onNewDataEntry()
    {
        val switchActivityIntent = Intent(this, DataEntryActivity::class.java)
        startActivity(switchActivityIntent)
    }
}