package com.ibxcodecat.frc_scouting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.frc_scouting.R

class DataEntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)

        whatsThisListeners()
        submissionListener()

    }

    private fun whatsThisListeners()
    {
        // get reference to what's this buttons
        val teamNumberHelp = findViewById<Button>(R.id.teamNumberHelp)
        val matchNumberHelp = findViewById<Button>(R.id.matchNumberHelp)

        // listen for on-click and run Toast
        teamNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the team number of the team you are currently scouting. It should be printed on the bumper guard of their robot.", Toast.LENGTH_LONG).show() }
        matchNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the match number for the match you are currently scouting. The match number should be visible on the scoreboard or main display.", Toast.LENGTH_LONG).show() }

    }

    private fun submissionListener()
    {
        //Get reference to form submission button
        val submitButton = findViewById<Button>(R.id.submit)

        //Switch to BlueTooth Activity
        submitButton.setOnClickListener(){
            val submissionIntent = Intent(this, BluetoothActivity::class.java) //Create submission intent and activity

            //Extra data to send with the activity switch
            submissionIntent.putExtra("Team Number", Integer.parseInt(findViewById<EditText>(R.id.teamNumber).text.toString()))
            submissionIntent.putExtra("Match Number", Integer.parseInt(findViewById<EditText>(R.id.matchNumber).text.toString()))



            startActivity(submissionIntent) //Start the activity with extra data
        }
    }
}