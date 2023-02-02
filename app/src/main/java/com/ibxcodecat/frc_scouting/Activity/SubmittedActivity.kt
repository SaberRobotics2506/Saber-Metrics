package com.ibxcodecat.frc_scouting.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import com.ibxcodecat.frc_scouting.R

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
        Toast.makeText(this@SubmittedActivity, "This feature has been disablesd", Toast.LENGTH_LONG).show()
        return;
    }

    private fun onNewDataEntry()
    {
        val switchActivityIntent = Intent(this, DataEntryActivity::class.java)
        startActivity(switchActivityIntent)
    }
}