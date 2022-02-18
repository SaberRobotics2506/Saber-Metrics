package com.ibxcodecat.frc_scouting

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.example.frc_scouting.R


class DataEntryActivity : AppCompatActivity() {

    //var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)

        whatsThisListeners()
        submissionListener()

    }

    private fun errorTextFormatting(text: EditText)
    {
        text.setTextColor(Color.RED)
        text.setHintTextColor(Color.RED)
    }

    private fun resetTextFormatting(teamNumber: EditText, matchNumber: EditText)
    {
        teamNumber.setTextColor(Color.BLACK)
        teamNumber.setHintTextColor(Color.BLACK)
        matchNumber.setTextColor(Color.BLACK)
        matchNumber.setHintTextColor(Color.BLACK)
    }

    private fun checkData(): Boolean
    {
        val teamNumber = findViewById<EditText>(R.id.teamNumber)
        val matchNumber = findViewById<EditText>(R.id.matchNumber)
        val scoutedBy = findViewById<EditText>(R.id.scoutedBy)

        resetTextFormatting(teamNumber, matchNumber)

        val validator = DataValidator()

        when(validator.CheckData(teamNumber.text.toString(), matchNumber.text.toString(), scoutedBy.text.toString()))
        {
            DataValidator.DataError.TeamNumberError -> errorTextFormatting(teamNumber)
            DataValidator.DataError.MatchNumberError -> errorTextFormatting(matchNumber)
            DataValidator.DataError.ScoutedByErrror -> errorTextFormatting(scoutedBy)
            else -> { return true }
        }

        return false
    }

    private fun whatsThisListeners()
    {
        // get reference to what's this buttons
        val teamNumberHelp = findViewById<Button>(R.id.teamNumberHelp)
        val matchNumberHelp = findViewById<Button>(R.id.matchNumberHelp)
        val scouterNameHelp = findViewById<Button>(R.id.scoutedByHelp)
        val regionalHelp = findViewById<Button>(R.id.regionalHelp)

        // listen for on-click and run Toast
        teamNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the team number of the team you are currently scouting. It should be printed on the bumper guard of their robot.", Toast.LENGTH_LONG).show() }
        matchNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the match number for the match you are currently scouting. The match number should be visible on the scoreboard or main display.", Toast.LENGTH_LONG).show() }
        scouterNameHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is your name num nuts!", Toast.LENGTH_LONG).show() }
        regionalHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the regional you are currently attending", Toast.LENGTH_LONG).show() }
    }

    private fun submissionListener()
    {
        //Get reference to form submission button
        val submitButton = findViewById<Button>(R.id.submit)

        //Switch to BlueTooth Activity
        submitButton.setOnClickListener {

            if(checkData())
            {
                val teamNumber = findViewById<EditText>(R.id.teamNumber)
                val matchNumber = findViewById<EditText>(R.id.matchNumber)
                val scoutedBy = findViewById<EditText>(R.id.scoutedBy)
                val regionalToggle = findViewById<ToggleButton>(R.id.regionalSelector)

                val dataToSerialize = SerializationData(
                    teamNumber.text.toString().toInt(),
                    matchNumber.text.toString().toInt(),
                    scoutedBy.text.toString(),
                    regionalToggle.text.toString()
                )

                val fileSystem = FileSystem()

                if(fileSystem.WriteGSON(dataToSerialize, this@DataEntryActivity))
                {
                    Toast.makeText(this@DataEntryActivity, "Saved your data here: " + this@DataEntryActivity.getExternalFilesDir(null).toString(), Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this@DataEntryActivity, "It no workie :(", Toast.LENGTH_LONG).show()
                }

            }
            else
            {
                Toast.makeText(this@DataEntryActivity, "The data you entered doesn't look quite right...", Toast.LENGTH_LONG).show()
            }
        }
    }
}