package com.example.frc_scouting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class DataEntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)

        whatsThisListeners()

    }

    private fun whatsThisListeners()
    {
        // get reference to form submission button
        val teamNumberHelp = findViewById<Button>(R.id.teamNumberHelp)
        val matchNumberHelp = findViewById<Button>(R.id.matchNumberHelp)

        // set on-click listener
        teamNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the team number of the team you are currently scouting. It should be printed on the bumper guard of their robot.", Toast.LENGTH_LONG).show() }
        matchNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the match number for the match you are currently scouting. The match number should be visible on the scoreboard or main display.", Toast.LENGTH_LONG).show() }

    }
}