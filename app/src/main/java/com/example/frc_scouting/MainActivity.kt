package com.example.frc_scouting

//imports
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private var dataEntryMode: Boolean = true; //True means that the input UI is active

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.toolbar)) This line is commented because with it the app didn't build correctly... Since idk what this does I keep it

        // get reference to form submission button
        val sendButton = findViewById<Button>(R.id.Send)

        // set on-click listener
        sendButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            onFormSubmission()
        }
    }

    public fun callToast(text: String, mode: Int)
    {
        Toast.makeText(this@MainActivity, text, mode).show()
    }

    private fun onFormSubmission() {
        //Warn the user not to disable bluetooth or to turn off the device
        callToast("⚠ DO NOT DISABLE BLUETOOTH OR CLOSE THE APP ⚠", Toast.LENGTH_LONG)

        toggleUI()


        //Toast.makeText(this@MainActivity, "Your data has been sent successfully!", Toast.LENGTH_SHORT).show() will use later
    }

    private fun toggleUI()
    {
        findViewById<Button>(R.id.Send).isVisible = !dataEntryMode; //toggle the send button to prevent unwanted button press
        findViewById<ProgressBar>(R.id.progressBar).isVisible = dataEntryMode; //toggle the loading bar
        findViewById<ProgressBar>(R.id.progressBar2).isVisible = dataEntryMode; //toggle the spinning thing

        dataEntryMode = !dataEntryMode //Toggle the "dataEntryMode" boolean
    }
}