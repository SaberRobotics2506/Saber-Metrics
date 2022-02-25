package com.ibxcodecat.frc_scouting

//Import AndroidX
import androidx.appcompat.app.AppCompatActivity

//Import Android Components
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import com.example.frc_scouting.R

class DataEntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)

        whatsThisListeners()
        submissionListener()
    }

    //Used to override the back button to prevent fields from being pre-populated with previous data
    override fun onBackPressed() {
        Toast.makeText(this@DataEntryActivity, "This feature has been disabled to save dumb users like you from their own destructive behaviour!", Toast.LENGTH_LONG).show()
        return
    }

    private fun errorDropdown(dropdown: Spinner)
    {
        dropdown.setBackgroundColor(Color.RED)
    }

    private fun resetDropdownFormatting(teamNumber: Spinner, matchNumber: Spinner)
    {
        teamNumber.setBackgroundColor(Color.WHITE)
        matchNumber.setBackgroundColor(Color.WHITE)
    }

    private fun errorTextFormatting(text: EditText)
    {
        text.setTextColor(Color.RED)
        text.setHintTextColor(Color.RED)
    }

    private fun resetTextFormatting(scoutedBy: EditText, score: EditText, comments: EditText)
    {
        scoutedBy.setTextColor(Color.BLACK)
        scoutedBy.setHintTextColor(Color.BLACK)
        score.setTextColor(Color.BLACK)
        score.setHintTextColor(Color.BLACK)
        comments.setTextColor(Color.BLACK)
        comments.setHintTextColor(Color.BLACK)
    }

    private fun checkData(): Boolean
    {
        val teamNumber = findViewById<Spinner>(R.id.teamNumber)
        val matchNumber = findViewById<Spinner>(R.id.matchNumber)

        val scoutedBy = findViewById<EditText>(R.id.scoutedBy)
        val score = findViewById<EditText>(R.id.score)
        val comments = findViewById<EditText>(R.id.comments)

        resetTextFormatting(scoutedBy, score, comments)
        resetDropdownFormatting(teamNumber, matchNumber)

        val validator = DataValidator()

        when(validator.CheckData(teamNumber.selectedItem.toString(), matchNumber.selectedItemPosition, scoutedBy.text.toString(), score.text.toString(), comments.text.toString()))
        {
            DataValidator.DataError.TeamNumberError -> errorDropdown(teamNumber)
            DataValidator.DataError.MatchNumberError -> errorDropdown(matchNumber)

            DataValidator.DataError.ScoutedByError -> errorTextFormatting(scoutedBy)
            DataValidator.DataError.ScoreError -> errorTextFormatting(score)
            DataValidator.DataError.CommentsError -> errorTextFormatting(comments)
            else -> { return true }
        }

        return false
    }

    private fun incrementListeners()
    {
        val highAutoMakesIncrement = findViewById<Button>(R.id.hiMakeAutoUpBtn)
        val highAutoMissIncrement = findViewById<Button>(R.id.hiMissAutoUpBtn)
        val lowAutoMakesIncrement = findViewById<Button>(R.id.lowMakeAutoUpBtn)
        val lowAutoMissIncrement = findViewById<Button>(R.id.lowMissAutoUpBtn)

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
        teamNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the team number of the team you are currently scouting.", Toast.LENGTH_LONG).show() }
        matchNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the match number for the match you are currently scouting.", Toast.LENGTH_LONG).show() }
        scouterNameHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is your name num nuts!", Toast.LENGTH_LONG).show() }
        regionalHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "Are you in Milwaukee or Duluth?", Toast.LENGTH_LONG).show() }
        taxiHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "Did the robot drive across the line during autonomous?", Toast.LENGTH_LONG).show() }
        scoreHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the total alliance score for the robot ou are scouting!", Toast.LENGTH_LONG).show() }
    }

    private fun submissionListener()
    {
        //Get reference to form submission button
        val submitButton = findViewById<Button>(R.id.submit)

        //Switch to BlueTooth Activity
        submitButton.setOnClickListener {

            if(checkData())
            {
                val teamNumber = findViewById<Spinner>(R.id.teamNumber)
                val matchNumber = findViewById<Spinner>(R.id.matchNumber)
                val scoutedBy = findViewById<EditText>(R.id.scoutedBy)
                val regionalToggle = findViewById<ToggleButton>(R.id.regionalSelector)
                val taxiToggle = findViewById<ToggleButton>(R.id.taxiSelector)
                val score = findViewById<EditText>(R.id.score)
                val comments = findViewById<EditText>(R.id.comments)

                val dataToSerialize = SerializationData(
                    teamNumber.selectedItem.toString().toInt(),
                    matchNumber.selectedItemPosition,
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
                    Toast.makeText(this@DataEntryActivity, "We have tried, and we have failed :(", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this@DataEntryActivity, "Either Nathan doesn't believe you or your data is invalid...", Toast.LENGTH_LONG).show()
            }
        }
    }
}