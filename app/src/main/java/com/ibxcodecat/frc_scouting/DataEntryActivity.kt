package com.ibxcodecat.frc_scouting

//Import AndroidX
import androidx.appcompat.app.AppCompatActivity

//Import Android Components
import android.content.Intent
import android.graphics.Color
import android.os.Bundle

//Import Android Widgets
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton

class DataEntryActivity : AppCompatActivity() {

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

    private fun resetTextFormatting(teamNumber: EditText, matchNumber: EditText, scoutedBy: EditText, score: EditText, comments: EditText)
    {
        teamNumber.setTextColor(Color.BLACK)
        teamNumber.setHintTextColor(Color.BLACK)
        matchNumber.setTextColor(Color.BLACK)
        matchNumber.setHintTextColor(Color.BLACK)
        scoutedBy.setTextColor(Color.BLACK)
        scoutedBy.setHintTextColor(Color.BLACK)
        score.setTextColor(Color.BLACK)
        score.setHintTextColor(Color.BLACK)
        comments.setTextColor(Color.BLACK)
        comments.setHintTextColor(Color.BLACK)
    }

    private fun checkData(): Boolean
    {
        val teamNumber = findViewById<EditText>(R.id.teamNumber)
        val matchNumber = findViewById<EditText>(R.id.matchNumber)
        val scoutedBy = findViewById<EditText>(R.id.scoutedBy)
        val score = findViewById<EditText>(R.id.score)
        val comments = findViewById<EditText>(R.id.comments)

        resetTextFormatting(teamNumber, matchNumber, scoutedBy, score, comments)

        val validator = DataValidator()

        when(validator.CheckData(teamNumber.text.toString(), matchNumber.text.toString(), scoutedBy.text.toString(), score.text.toString(), comments.text.toString()))
        {
            DataValidator.DataError.TeamNumberError -> errorTextFormatting(teamNumber)
            DataValidator.DataError.MatchNumberError -> errorTextFormatting(matchNumber)
            DataValidator.DataError.ScoutedByError -> errorTextFormatting(scoutedBy)
            DataValidator.DataError.ScoreError -> errorTextFormatting(score)
            DataValidator.DataError.CommentsError -> errorTextFormatting(comments)
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
        val taxiHelp = findViewById<Button>(R.id.taxiHelp)
        val scoreHelp = findViewById<Button>(R.id.scoreHelp)

        // listen for on-click and run Toast
        teamNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the team number of the team you are currently scouting. It should be printed on the bumper guard of their robot.", Toast.LENGTH_LONG).show() }
        matchNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the match number for the match you are currently scouting. The match number should be visible on the scoreboard or main display.", Toast.LENGTH_LONG).show() }
        scouterNameHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is your name num nuts!", Toast.LENGTH_LONG).show() }
        regionalHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the regional you are currently attending", Toast.LENGTH_LONG).show() }
        taxiHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "Did the robot taxi out of its spot during auto? You should know this unless you're blind you dum-dum!", Toast.LENGTH_LONG).show() }
        scoreHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the total score for the alliance the team you are scouting is a member of!", Toast.LENGTH_LONG).show() }
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
                val taxiToggle = findViewById<ToggleButton>(R.id.taxiSelector)
                val score = findViewById<EditText>(R.id.score)
                val comments = findViewById<EditText>(R.id.comments)

                val dataToSerialize = SerializationData(
                    teamNumber.text.toString().toInt(),
                    matchNumber.text.toString().toInt(),
                    scoutedBy.text.toString(),
                    regionalToggle.text.toString(),
                    taxiToggle.isChecked,
                    score.text.toString().toInt(),
                    comments.text.toString(),
                )

                val fileSystem = FileSystem()

                if(fileSystem.WriteGSON(dataToSerialize, this@DataEntryActivity))
                {
                    Toast.makeText(this@DataEntryActivity, "Saved your data here: " + this@DataEntryActivity.getExternalFilesDir(null).toString(), Toast.LENGTH_LONG).show()

                    val switchActivityIntent = Intent(this, SubmittedActivity::class.java)
                    startActivity(switchActivityIntent)
                }
                else
                {
                    Toast.makeText(this@DataEntryActivity, "It no workie :(", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this@DataEntryActivity, "Either Nathan doesn't believe you or your data is invalid...", Toast.LENGTH_LONG).show()
            }
        }
    }
}