package com.ibxcodecat.frc_scouting.Activity

//Import AndroidX

//Import Android Components
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.SyncStateContract
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ibxcodecat.frc_scouting.Classes.DataValidator
import com.ibxcodecat.frc_scouting.R
import com.ibxcodecat.frc_scouting.Data.SerializationData
import com.ibxcodecat.frc_scouting.Classes.FileSystem
import com.ibxcodecat.frc_scouting.Data.Constants
import com.ibxcodecat.frc_scouting.Data.TeamData

import java.lang.Exception
import kotlin.system.exitProcess


var highAutoMakesNum: Int = 0
var highAutoMissNum: Int = 0
var lowAutoMakesNum: Int = 0
var lowAutoMissNum: Int = 0
var highTeleopMakesNum: Int = 0
var highTeleopMissNum: Int = 0
var lowTeleopMakesNum: Int = 0
var lowTeleopMissNum: Int = 0
var defPlaysNum: Int = 0
var selectedMatch: Int = 0
val teamNumberArray = IntArray(42)

class DataEntryActivity : AppCompatActivity() {

    //region android_methods

    override fun onStart() {
        super.onStart()

        try {
            val data = loadAutofillData()
        }
        catch (ex: Exception)
        {
            Toast.makeText(this@DataEntryActivity, "Oh no, something appears to have gone wrong! Let Nathan or Dominic know! \n\n " + ex.toString(), Toast.LENGTH_LONG).show()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)

        whatsThisListeners()
        submissionListener()
        numberManipulationListeners()
    }

    //Used to override the back button to prevent fields from being pre-populated with previous data
    override fun onBackPressed() {
        Toast.makeText(this@DataEntryActivity, "This feature has been disabled to save dumb users like you from their own destructive behaviour!", Toast.LENGTH_LONG).show()
        return
    }
    //endregion

    //region page_formatting
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
    //endregion

    private fun loadAutofillData(): TeamData {
        val fileSystem = FileSystem()
        val loadedData = fileSystem.ReadJSONFromAssets( this@DataEntryActivity,  Constants.redTeamFile, Constants.blueTeamFile )

        if(loadedData == null)
        {
            Toast.makeText(this@DataEntryActivity, "There was a problem loading autofill data, please contact Nathan or Dominic", Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(this@DataEntryActivity, "Autofill data successfully loaded!", Toast.LENGTH_LONG).show()
        }

        return loadedData
    }

    private fun checkData(): Boolean
    {
        val teamNumber = findViewById<Spinner>(R.id.teamNumber)
        val matchNumber = findViewById<Spinner>(R.id.matchNumber)

        val scoutedBy = findViewById<EditText>(R.id.scoutedBy)
        val score = findViewById<EditText>(R.id.score)
        val comments = findViewById<EditText>(R.id.comments)

        resetInputVariables()
        resetTextFormatting(scoutedBy, score, comments)
        resetDropdownFormatting(teamNumber, matchNumber)

        val validator = DataValidator()

        when(validator.CheckData(matchNumber.selectedItemPosition.toString(), scoutedBy.text.toString(), score.text.toString(), comments.text.toString()))
        {
            //DataValidator.DataError.TeamNumberError -> errorDropdown(teamNumber) To be removed when pre-set teams come in place
            DataValidator.DataError.MatchNumberError -> errorDropdown(matchNumber)
            DataValidator.DataError.ScoutedByError -> errorTextFormatting(scoutedBy)
            DataValidator.DataError.ScoreError -> errorTextFormatting(score)
            DataValidator.DataError.CommentsError -> errorTextFormatting(comments)
            else -> { return true }
        }

        return false
    }

    //region number_manipulators
    @SuppressLint("SetTextI18n")
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

        // Text boxes
        val hTM = findViewById<TextView>(R.id.highTeleopMakesNumText)
        val hTI = findViewById<TextView>(R.id.highTeleopMissNumText)
        val lTM = findViewById<TextView>(R.id.lowTeleopMakesNumText)
        val lTI = findViewById<TextView>(R.id.lowTeleopMissNumText)
        val defense = findViewById<TextView>(R.id.defPlaysNumText)
        highTeleopMakesIncrement.setOnClickListener{
            highTeleopMakesNum++; if(highTeleopMakesNum == 1) hTM.setText(
            highTeleopMakesNum.toString() + " bucket") else hTM.setText(highTeleopMakesNum.toString() + " buckets")}
        highTeleopMissIncrement.setOnClickListener{
            highTeleopMissNum++; if(highTeleopMissNum == 1) hTI.setText(
            highTeleopMissNum.toString() + " miss") else hTI.setText(highTeleopMissNum.toString() + " misses")}
        lowTeleopMakesIncrement.setOnClickListener{
            lowTeleopMakesNum++; if(lowTeleopMakesNum == 1) lTM.setText(
            lowTeleopMakesNum.toString() + " bucket") else lTM.setText(lowTeleopMakesNum.toString() + " buckets")}
        lowTeleopMissIncrement.setOnClickListener{
            lowTeleopMissNum++; if(lowTeleopMissNum == 1) lTI.setText(
            lowTeleopMissNum.toString() + " miss") else lTI.setText(lowTeleopMissNum.toString() + " misses")}
        highTeleopMakesDecrement.setOnClickListener{
            highTeleopMakesNum--; if(highTeleopMakesNum < 0) highTeleopMakesNum = 0; if(highTeleopMakesNum == 1) hTM.setText(
            highTeleopMakesNum.toString() + " bucket") else hTM.setText(highTeleopMakesNum.toString() + " buckets")}
        highTeleopMissDecrement.setOnClickListener{
            highTeleopMissNum--; if(highTeleopMissNum < 0) highTeleopMissNum = 0; if(highTeleopMissNum == 1) hTI.setText(
            highTeleopMissNum.toString() + " miss") else hTI.setText(highTeleopMissNum.toString() + " misses")}
        lowTeleopMakesDecrement.setOnClickListener{
            lowTeleopMakesNum--; if(lowTeleopMakesNum < 0) lowTeleopMakesNum = 0; if(lowTeleopMakesNum == 1) lTM.setText(
            lowTeleopMakesNum.toString() + " bucket") else lTM.setText(lowTeleopMakesNum.toString() + " buckets")}
        lowTeleopMissDecrement.setOnClickListener{
            lowTeleopMissNum--; if(lowTeleopMissNum < 0) lowTeleopMissNum = 0; if(lowTeleopMissNum == 1) lTI.setText(
            lowTeleopMissNum.toString() + " miss") else lTI.setText(lowTeleopMissNum.toString() + " misses")}
        defensePlaysIncrement.setOnClickListener{ defPlaysNum++; defense.setText(defPlaysNum.toString())}
        defensePlaysDecrement.setOnClickListener{
            defPlaysNum--; if(defPlaysNum < 0) defPlaysNum = 0; defense.setText(
            defPlaysNum.toString())}
    }
    //endregion

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
        defPlaysNum = 0
    }

    //region whats_this
    private fun whatsThisListeners()
    {
        // get reference to what's this buttons
        val teamNumberHelp = findViewById<Button>(R.id.teamNumberHelp)
        val matchNumberHelp = findViewById<Button>(R.id.matchNumberHelp)
        val scouterNameHelp = findViewById<Button>(R.id.scoutedByHelp)
        val taxiHelp = findViewById<Button>(R.id.taxiHelp)
        val scoreHelp = findViewById<Button>(R.id.scoreHelp)

        // listen for on-click and run Toast
        teamNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the team number of the team you are currently scouting.", Toast.LENGTH_LONG).show() }
        matchNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the match number for the match you are currently scouting.", Toast.LENGTH_LONG).show() }
        scouterNameHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is your name num nuts!", Toast.LENGTH_LONG).show() }
        taxiHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "Did the robot drive across the line during autonomous?", Toast.LENGTH_LONG).show() }
        scoreHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the total alliance score for the robot ou are scouting!", Toast.LENGTH_LONG).show() }
    }
    //endregion

    //region form_submission
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
                val taxiToggle = findViewById<ToggleButton>(R.id.taxiSelector)
                val score = findViewById<EditText>(R.id.score)
                val comments = findViewById<EditText>(R.id.comments)

                val climbAttempt = findViewById<Spinner>(R.id.climbAttDropdown)

                val gameResult = findViewById<Spinner>(R.id.climbResultDropdown)
                val traversalTime = findViewById<Spinner>(R.id.climbTimeDropdown)
                highAutoMakesNum = findViewById<Spinner>(R.id.autoHighMakesSpinner).selectedItemPosition
                lowAutoMakesNum = findViewById<Spinner>(R.id.autoLowMakesSpinner).selectedItemPosition

                val dataToSerialize = SerializationData(
                    teamNumber.selectedItem.toString().toInt(),
                    matchNumber.selectedItemPosition,
                    scoutedBy.text.toString(),
                    "Houston",
                    taxiToggle.isChecked,
                    score.text.toString().toInt(),
                    comments.text.toString(),
                    lowAutoMakesNum,
                    highAutoMakesNum,
                    lowTeleopMakesNum,
                    lowTeleopMissNum,
                    highTeleopMakesNum,
                    highTeleopMissNum,
                    defPlaysNum,
                    climbAttempt.selectedItemPosition,
                    gameResult.selectedItemPosition,
                    traversalTime.selectedItemPosition
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
    //endregion
}