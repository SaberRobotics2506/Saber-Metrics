package com.ibxcodecat.frc_scouting

//Import Android Components
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

var highAutoMakesNum: Int = 0
var highAutoMissNum: Int = 0
var lowAutoMakesNum: Int = 0
var lowAutoMissNum: Int = 0
var highTeleopMakesNum: Int = 0
var highTeleopMissNum: Int = 0
var lowTeleopMakesNum: Int = 0
var lowTeleopMissNum: Int = 0

class DataEntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)
        whatsThisListeners()
        submissionListener()
        numberManipulationListeners()
    }

    //GUI formatting helper methods
    private fun errorDropdown(dropdown: Spinner) { dropdown.setBackgroundColor(Color.RED) }
    private fun resetDropdownFormatting(dropdown: Spinner) { dropdown.setBackgroundColor(Color.WHITE) }
    private fun errorTextFormatting(text: EditText) { text.setTextColor(Color.RED); text.setHintTextColor(Color.RED) }
    private fun resetTextFormatting(scoutedBy: EditText) { scoutedBy.setTextColor(Color.BLACK); scoutedBy.setHintTextColor(Color.BLACK) }

    //Used to override the back button pre determined functionality to prevent fields from being pre-populated with previous data
    override fun onBackPressed() {
        Toast.makeText(this@DataEntryActivity, "This feature has been disabled to save dumb users like you from their own destructive behaviour!", Toast.LENGTH_LONG).show()
        return
    }

    private fun checkData(): Boolean
    {
        //Store all of our dropdowns in an array
        val dropdowns = arrayOf(
            findViewById<Spinner>(R.id.teamNumber),
            findViewById<Spinner>(R.id.matchNumber),
            findViewById<Spinner>(R.id.climbAttemptDropdown),
            findViewById<Spinner>(R.id.gameResultDropdown)
        )

        //Loop through each item in our array of dropdowns and reset the error formatting
        for(dropdown : Spinner in dropdowns) { resetDropdownFormatting(dropdown) }

        //Loop through our array of dropdowns
        for(dropdown : Spinner in dropdowns)
        {
            //If the first item is selected (the prompt) the dropdwon is formatted as invalid
            if(dropdown.selectedItemPosition == 0)
            {
                errorDropdown(dropdown)
                return false //Invalid data, exit this evaluation
            }
        }

        //Store all of our editable text fields independently
        val scoutedBy = findViewById<EditText>(R.id.scoutedBy)
        val score = findViewById<EditText>(R.id.score)
        val comments = findViewById<EditText>(R.id.comments)

        //Store all of our editable text fields in an array
        val textFields = arrayOf( scoutedBy, score, comments )

        //Loop through each of our text fields and reset their error formatting
        for(textField : EditText in textFields) { resetTextFormatting(textField) }

        val validator = DataValidator() //Create our data validation object

        //Verify each human editable text field and throw an error when necessary
        when(validator.CheckData(scoutedBy.text.toString(), score.text.toString(), comments.text.toString()))
        {
            DataValidator.DataError.ScoutedByError -> errorTextFormatting(scoutedBy)
            DataValidator.DataError.ScoreError -> errorTextFormatting(score)
            DataValidator.DataError.CommentsError -> errorTextFormatting(comments)
            else -> { return true } //All data is valid
        }

        return false //The data was not valid
    }

    private fun numberManipulationListeners()
    {
        // Increment buttons and dropdowns
        val highTeleopMakesIncrement = findViewById<Button>(R.id.hiMakeTeleopUpBtn)
        val highTeleopMissIncrement = findViewById<Button>(R.id.hiMissTeleopUpBtn)
        val lowTeleopMakesIncrement = findViewById<Button>(R.id.lowMakeTeleopUpBtn)
        val lowTeleopMissIncrement = findViewById<Button>(R.id.lowMissTeleopUpBtn)
        val defensePlaysIncrement = findViewById<Button>(R.id.defPlaysIncrementBtn)

        // Decrement buttons
        val highTeleopMakesDecrement = findViewById<Button>(R.id.hiMakeTeleopDownBtn)
        val highTeleopMissDecrement = findViewById<Button>(R.id.hiMissTeleopDownBtn)
        val lowTeleopMakesDecrement = findViewById<Button>(R.id.lowMakeTeleopDownBtn)
        val lowTeleopMissDecrement = findViewById<Button>(R.id.lowMissTeleopDownBtn)
        val defensePlaysDecrement = findViewById<Button>(R.id.defPlaysDecrementBtn)

        // Goal integer variables
        var defPlaysNum: Int = 0

        // Text boxes
        val hTM = findViewById<TextView>(R.id.highTeleopMakesNumText)
        val hTI = findViewById<TextView>(R.id.highTeleopMissNumText)
        val lTM = findViewById<TextView>(R.id.lowTeleopMakesNumText)
        val lTI = findViewById<TextView>(R.id.lowTeleopMissNumText)
        val defense = findViewById<TextView>(R.id.defPlaysNumText)
        highTeleopMakesIncrement.setOnClickListener{highTeleopMakesNum++; if(highTeleopMakesNum == 1) hTM.setText(highTeleopMakesNum.toString() + " bucket") else hTM.setText(highTeleopMakesNum.toString() + " buckets")}
        highTeleopMissIncrement.setOnClickListener{highTeleopMissNum++; if(highTeleopMissNum == 1) hTI.setText(highTeleopMissNum.toString() + " miss") else hTI.setText(highTeleopMissNum.toString() + " misses")}
        lowTeleopMakesIncrement.setOnClickListener{lowTeleopMakesNum++; if(lowTeleopMakesNum == 1) lTM.setText(lowTeleopMakesNum.toString() + " bucket") else lTM.setText(lowTeleopMakesNum.toString() + " buckets")}
        lowTeleopMissIncrement.setOnClickListener{lowTeleopMissNum++; if(lowTeleopMissNum == 1) lTI.setText(lowTeleopMissNum.toString() + " miss") else lTI.setText(lowTeleopMissNum.toString() + " misses")}
        highTeleopMakesDecrement.setOnClickListener{highTeleopMakesNum--; if(highTeleopMakesNum < 0) highTeleopMakesNum = 0; if(highTeleopMakesNum == 1) hTM.setText(highTeleopMakesNum.toString() + " bucket") else hTM.setText(highTeleopMakesNum.toString() + " buckets")}
        highTeleopMissDecrement.setOnClickListener{highTeleopMissNum--; if(highTeleopMissNum < 0) highTeleopMissNum = 0; if(highTeleopMissNum == 1) hTI.setText(highTeleopMissNum.toString() + " miss") else hTI.setText(highTeleopMissNum.toString() + " misses")}
        lowTeleopMakesDecrement.setOnClickListener{lowTeleopMakesNum--; if(lowTeleopMakesNum < 0) lowTeleopMakesNum = 0; if(lowTeleopMakesNum == 1) lTM.setText(lowTeleopMakesNum.toString() + " bucket") else lTM.setText(lowTeleopMakesNum.toString() + " buckets")}
        lowTeleopMissDecrement.setOnClickListener{lowTeleopMissNum--; if(lowTeleopMissNum < 0) lowTeleopMissNum = 0; if(lowTeleopMissNum == 1) lTI.setText(lowTeleopMissNum.toString() + " miss") else lTI.setText(lowTeleopMissNum.toString() + " misses")}
        defensePlaysIncrement.setOnClickListener{defPlaysNum++; defense.setText(defPlaysNum.toString())}
        defensePlaysDecrement.setOnClickListener{defPlaysNum--; if(defPlaysNum < 0) defPlaysNum = 0; defense.setText(defPlaysNum.toString())}
    }

    private fun resetInputVariables()
    {
        highAutoMakesNum = 0
        highAutoMissNum = 0
        lowAutoMakesNum = 0
        lowAutoMissNum = 0
        highTeleopMakesNum = 0
        highTeleopMissNum = 0
        lowTeleopMakesNum = 0
        lowTeleopMissNum = 0
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

                val defensivePlays = findViewById<TextView>(R.id.defPlaysNumText)

                val climbAttempt = findViewById<Spinner>(R.id.climbAttemptDropdown)

                val gameResult = findViewById<Spinner>(R.id.gameResultDropdown)
                highAutoMakesNum = findViewById<Spinner>(R.id.autoHighMakesSpinner).selectedItemPosition
                lowAutoMakesNum = findViewById<Spinner>(R.id.autoLowMakesSpinner).selectedItemPosition

                val dataToSerialize = SerializationData(
                    teamNumber.selectedItem.toString().toInt(),
                    matchNumber.selectedItemPosition,
                    scoutedBy.text.toString(),
                    regionalToggle.text.toString(),
                    taxiToggle.isChecked,
                    score.text.toString().toInt(),
                    comments.text.toString(),
                    lowAutoMakesNum,
                    highAutoMakesNum,
                    lowTeleopMakesNum,
                    lowTeleopMissNum,
                    highTeleopMakesNum,
                    highTeleopMissNum,
                    defensivePlays.text.toString().toInt(),
                    climbAttempt.selectedItemPosition,
                    gameResult.selectedItemPosition
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
                    Toast.makeText(this@DataEntryActivity, "We have tried our best to save your data, and we have failed :(", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this@DataEntryActivity, "Either the code boy doesn't believe you or your data is in fact invalid...", Toast.LENGTH_LONG).show()
            }
        }
    }
}