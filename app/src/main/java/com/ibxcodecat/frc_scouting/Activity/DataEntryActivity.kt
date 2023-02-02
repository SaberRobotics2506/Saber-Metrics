package com.ibxcodecat.frc_scouting.Activity

//Import AndroidX

//Import Android Components
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ibxcodecat.frc_scouting.Classes.DataValidator
import com.ibxcodecat.frc_scouting.R
import com.ibxcodecat.frc_scouting.Data.SerializationData
import com.ibxcodecat.frc_scouting.Classes.FileSystem
import com.ibxcodecat.frc_scouting.Data.Constants
import com.ibxcodecat.frc_scouting.Data.TeamData


var highAutoMakesNum: Int = 0
var midAutoMakesNum: Int = 0
var lowAutoMakesNum: Int = 0
var cubeMakesNum: Int = 0
var conesMakesNum: Int = 0
var defPlaysNum: Int = 0
var selectedMatch: Int = 0
val teamNumberArray = IntArray(42)

class DataEntryActivity : AppCompatActivity() {

    //region android_methods

    override fun onStart() {
        super.onStart()
//
//        try {
//            val data = loadAutofillData(findViewById<TextView>(R.id.teamNumber), selectedMatch);
//
//        }
//        catch (ex: Exception)
//        {
//            Toast.makeText(this@DataEntryActivity, "Oh no, something appears to have gone wrong! Let Nathan or Dominic know! \n\n " + ex.toString(), Toast.LENGTH_LONG).show()
//        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)

        whatsThisListeners()
        submissionListener()
        numberManipulationListeners()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        loadAutofillData(findViewById<TextView>(R.id.teamNumber), selectedMatch)
        return super.onContextItemSelected(item)
    }

    //Used to override the back button to prevent fields from being pre-populated with previous data
    override fun onBackPressed() {
        Toast.makeText(this@DataEntryActivity, "You can not go back. If there is something you missed, your fine don't worry about it.", Toast.LENGTH_LONG).show()
        return
    }
    //endregion

    //region page_formatting
    private fun errorDropdown(dropdown: Spinner)
    {
        dropdown.setBackgroundColor(Color.RED)
    }

    private fun resetDropdownFormatting(matchNumber: Spinner)
    {
        //matchNumber.setBackgroundColor(Color.BLACK)
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

    private fun loadAutofillData(teamText: TextView, match: Int): TeamData {
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

        val deviceID = fileSystem.ReadDeviceID(this@DataEntryActivity)

        //when(deviceID)
        //{
        //    "R1" -> teamText.text = loadedData.redTeamNumbers["Match: " + match][0];
        //}

        Toast.makeText(this@DataEntryActivity, deviceID, Toast.LENGTH_LONG).show()

        return loadedData
    }

    private fun checkData(): Boolean
    {
        val matchNumber = findViewById<Spinner>(R.id.matchNumber)

        val scoutedBy = findViewById<EditText>(R.id.scoutedBy)
        val score = findViewById<EditText>(R.id.score)
        val comments = findViewById<EditText>(R.id.comments)


        resetTextFormatting(scoutedBy, score, comments)
        resetDropdownFormatting(matchNumber)

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
        val highAutoMakesIncrement = findViewById<Button>(R.id.hiAutoIncreaseBtn)
        val midAutoMakesIncrement = findViewById<Button>(R.id.midAutoIncreaseBtn)
        val lowAutoMakesIncrement = findViewById<Button>(R.id.lowAutoIncreaseBtn)
        val squareMakesIncrement = findViewById<Button>(R.id.cubeIncreaseBtn)
        val coneMakesIncrement = findViewById<Button>(R.id.coneIncreaseBtn)
//        val defensePlaysIncrement = findViewById<Button>(R.id.defPlaysIncrementBtn)

        // Decrement buttons
        val highAutoMakesDecrement = findViewById<Button>(R.id.hiAutoDecreaseBtn)
        val midAutoMakesDecrement = findViewById<Button>(R.id.midAutoDecreaseBtn)
        val lowAutoMakesDecrement = findViewById<Button>(R.id.lowAutoDecreaseBtn)
        val squareMakesDecrement = findViewById<Button>(R.id.cubeDecreaseBtn)
        val coneMakesDecrement = findViewById<Button>(R.id.coneDecreaseBtn)
//        val lowTeleopMissDecrement = findViewById<Button>(R.id.lowMissTeleopDownBtn)
//        val defensePlaysDecrement = findViewById<Button>(R.id.defPlaysDecrementBtn)

        // Text boxes
        val hTM = findViewById<TextView>(R.id.highAutoNumText)
        val mTM = findViewById<TextView>(R.id.midAutoNumText)
        val lTM = findViewById<TextView>(R.id.lowAutoNumText)
        val cubeM = findViewById<TextView>(R.id.cubeNumText)
        val coneM = findViewById<TextView>(R.id.coneNumText)
        val defense = findViewById<TextView>(R.id.defenseSwitch)

        //auto increase/decrease
        highAutoMakesIncrement.setOnClickListener{
            highAutoMakesNum++; if(highAutoMakesNum == 1) hTM.setText(
            highAutoMakesNum.toString() + " on-step") else hTM.setText(highAutoMakesNum.toString() + " on-step")}
        midAutoMakesIncrement.setOnClickListener{
            midAutoMakesNum++; if(midAutoMakesNum == 1) mTM.setText(
            midAutoMakesNum.toString() + " on-step") else mTM.setText(midAutoMakesNum.toString() + " on-step")}
        lowAutoMakesIncrement.setOnClickListener{
            lowAutoMakesNum++; if(lowAutoMakesNum == 1) lTM.setText(
            lowAutoMakesNum.toString() + " on-step") else lTM.setText(lowAutoMakesNum.toString() + " on-step")}
//        lowTeleopMissIncrement.setOnClickListener{
//            lowTeleopMissNum++; if(lowTeleopMissNum == 1) lTI.setText(
//            lowTeleopMissNum.toString() + " miss") else lTI.setText(lowTeleopMissNum.toString() + " misses")}
        highAutoMakesDecrement.setOnClickListener{
            highAutoMakesNum--; if(highAutoMakesNum < 0) highAutoMakesNum = 0; if(highAutoMakesNum == 1) hTM.setText(
            highAutoMakesNum.toString() + " on-step") else hTM.setText(highAutoMakesNum.toString() + " on-step")}
        midAutoMakesDecrement.setOnClickListener{
            midAutoMakesNum--; if(midAutoMakesNum < 0) midAutoMakesNum = 0; if(midAutoMakesNum == 1) mTM.setText(
            midAutoMakesNum.toString() + " on-step") else mTM.setText(midAutoMakesNum.toString() + " on-step")}
        lowAutoMakesDecrement.setOnClickListener{
            lowAutoMakesNum--; if(lowAutoMakesNum < 0) lowAutoMakesNum = 0; if(lowAutoMakesNum == 1) lTM.setText(
            lowAutoMakesNum.toString() + " on-step") else lTM.setText(lowAutoMakesNum.toString() + " on-step")}
        //tele increase/decrease
        squareMakesIncrement.setOnClickListener{
            cubeMakesNum++; if(cubeMakesNum < 0) cubeMakesNum = 0; if(cubeMakesNum == 1) cubeM.setText(
            cubeMakesNum.toString() + " cubes") else cubeM.setText(cubeMakesNum.toString() + " cubes")}
        squareMakesDecrement.setOnClickListener{
            cubeMakesNum--; if(cubeMakesNum < 0) cubeMakesNum = 0; if(cubeMakesNum == 1) cubeM.setText(
            cubeMakesNum.toString() + " cubes") else cubeM.setText(cubeMakesNum.toString() + " cubes")}
        coneMakesIncrement.setOnClickListener{
            conesMakesNum++; if(conesMakesNum < 0) conesMakesNum = 0; if(conesMakesNum == 1)  coneM.setText(
            conesMakesNum.toString() + " cones") else coneM.setText(conesMakesNum.toString() + " cones")}
        coneMakesDecrement.setOnClickListener{
            conesMakesNum--; if(conesMakesNum < 0) conesMakesNum = 0; if(conesMakesNum == 1)  coneM.setText(
            conesMakesNum.toString() + " cones") else coneM.setText(conesMakesNum.toString() + " cones")}
        //defense increase/decrease
//        defensePlaysIncrement.setOnClickListener{ defPlaysNum++; defense.setText(defPlaysNum.toString())}
//        defensePlaysDecrement.setOnClickListener{
//            defPlaysNum--; if(defPlaysNum < 0) defPlaysNum = 0; defense.setText(
//            defPlaysNum.toString())}
//        lowTeleopMissDecrement.setOnClickListener{
//            lowTeleopMissNum--; if(lowTeleopMissNum < 0) lowTeleopMissNum = 0; if(lowTeleopMissNum == 1) lTI.setText(
//            lowTeleopMissNum.toString() + " miss") else lTI.setText(lowTeleopMissNum.toString() + " misses")}
//        defensePlaysIncrement.setOnClickListener{ defPlaysNum++; defense.setText(defPlaysNum.toString())}
//        defensePlaysDecrement.setOnClickListener{
//            defPlaysNum--; if(defPlaysNum < 0) defPlaysNum = 0; defense.setText(
//            defPlaysNum.toString())}
    }
    //endregion

    private fun resetInputVariables()
    {
        highAutoMakesNum = 0
        midAutoMakesNum = 0
        lowAutoMakesNum = 0
        conesMakesNum = 0
        cubeMakesNum = 0
        //defPlaysNum = 0
    }

    //region whats_this
    private fun whatsThisListeners()
    {
        // get reference to what's this buttons
        val teamNumberHelp = findViewById<Button>(R.id.teamNumberHelp)
        val matchNumberHelp = findViewById<Button>(R.id.matchNumberHelp)
        val scouterNameHelp = findViewById<Button>(R.id.scoutedByHelp)
        val taxiHelp = findViewById<Button>(R.id.mobilityHelp)
        val scoreHelp = findViewById<Button>(R.id.scoreHelp)
        val parkedHelp = findViewById<Button>(R.id.parkedHelp)

        // listen for on-click and run Toast
        teamNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the team number of the team you are currently scouting.", Toast.LENGTH_LONG).show() }
        matchNumberHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the match number for the match you are currently scouting.", Toast.LENGTH_LONG).show() }
        scouterNameHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is where you put your name!", Toast.LENGTH_LONG).show() }
        taxiHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "Did the robot drive across the line during autonomous?", Toast.LENGTH_LONG).show() }
        scoreHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "This is the total alliance score for the robot you are scouting!", Toast.LENGTH_LONG).show() }
        parkedHelp.setOnClickListener { Toast.makeText(this@DataEntryActivity, "Did the robot finish inside of it's community", Toast.LENGTH_LONG).show()}

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
                val comments = findViewById<EditText>(R.id.comments)
                val gameResult = findViewById<Spinner>(R.id.gameResult)
                val allianceScore = findViewById<EditText>(R.id.score)
                val isParked = findViewById<ToggleButton>(R.id.parkedSelector)
                val didMobility = findViewById<ToggleButton>(R.id.mobilitySelector)
                val autoHighStep = findViewById<TextView>(R.id.highAutoNumText)
                val autoMidStep = findViewById<TextView>(R.id.midAutoNumText)
                val autoLowStep = findViewById<TextView>(R.id.lowAutoNumText)
                val autoBalancePlatform = findViewById<Spinner>(R.id.autoPlatform)
                val numCubes = findViewById<TextView>(R.id.cubeNumText)
                val numCones = findViewById<TextView>(R.id.coneNumText)
                val teleBalancePlatform = findViewById<Spinner>(R.id.telePlatform)
                //val defensePlays = findViewById<Switch>(R.id.defenseSwitch)

                //var defensePlaysInt = 0;
                //if(defensePlays.isChecked) defensePlaysInt = 1;

                val dataToSerialize = SerializationData(
                    teamNumber.selectedItem.toString().toInt(),
                    matchNumber.selectedItemPosition,
                    scoutedBy.text.toString(),
                    comments.text.toString(),
                    gameResult.selectedItemPosition,
                    allianceScore.text.toString().toInt(),
                    isParked.isChecked,
                    didMobility.isChecked,
                    highAutoMakesNum,
                    midAutoMakesNum,
                    lowAutoMakesNum,
                    autoBalancePlatform.selectedItemPosition,
                    cubeMakesNum,
                    conesMakesNum,
                    teleBalancePlatform.selectedItemPosition,
                    //defensePlaysInt
                )

                val fileSystem = FileSystem()

                if(fileSystem.WriteGSON(dataToSerialize, this@DataEntryActivity))
                {
                    Toast.makeText(this@DataEntryActivity, "Saved your data here: " + this@DataEntryActivity.getExternalFilesDir(null).toString(), Toast.LENGTH_LONG).show()

                    resetInputVariables()
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
                Toast.makeText(this@DataEntryActivity, "Invalid Data, please try again", Toast.LENGTH_LONG).show()
            }
        }
    }
    //endregion
}