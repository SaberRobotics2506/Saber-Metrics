package com.ibxcodecat.frc_scouting

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.frc_scouting.R


class BluetoothActivity : AppCompatActivity() {
    var disableNavigation: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        val teamNumber: String =
            intent.getStringExtra("Team Number")!! //-1 will be invalid in checkData()
        val matchNumber: String = intent.getStringExtra("Match Number")!!


        updateLoadingText("Validating your data...")

        if (checkData(teamNumber, matchNumber)) {
            updateLoadingText("Setting up Bluetooth...")

            if (setupBluetooth()) {
                updateLoadingText("Sending your data...")
                disableNavigation = true;
            } else {
                throwError("ERR 0x02: [Null Adapter]") //T
            }
        }
    }

    //Override default android navigation when 'disableNavigation' is true with a popup
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(disableNavigation) {
            if (keyCode == KeyEvent.KEYCODE_APP_SWITCH || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ALL_APPS) {
                Toast.makeText(this@BluetoothActivity, "Android navigation buttons are locked because we're sending data...", Toast.LENGTH_LONG).show()
                return true;
            }

        }
        return super.onKeyDown(keyCode, event)
    }

    //Update the loading text bellow the spinning thing and turn it black
    private fun updateLoadingText(text: String) {
        val view: TextView = findViewById<TextView>(R.id.wait)
        view.text = text
        view.setTextColor(Color.BLACK)
    }

    //Update the loading text bellow the spinning thing and turn it red
    private fun throwError(text: String)
    {
        val view: TextView = findViewById<TextView>(R.id.wait);
        view.text = text;
        view.setTextColor(Color.RED)
    }

    //Check the data that was passed into the bluetooth thing
    private fun checkData(teamNumber: String, matchNumber: String): Boolean
    {
        updateLoadingText("Validating Data...")

        try
        {
            val validator = DataValidator();

            if(validator.ValidateTeamNumber(teamNumber)) //team number valid?
            {
                if(validator.ValidateMatchNumber(matchNumber)) //The team number and match number are valid
                {
                    return true;
                }
            }

            throwError("ERR: 0x00 [DATA_INVALID]")
            return false;
        }
        catch(ex: Exception)
        {
            throwError("ERR: 0x01 [INTENT_REJECTED]")
            return false;
        }

    }

    private fun setupBluetooth(): Boolean
    {

        updateLoadingText("Setting up Bluetooth...")

        val adapter: BluetoothAdapter? = getBluetoothAdapter();

        if(getBluetoothAdapter() == null)
        {
            return false;
        }
        else
        {
            if(!adapter?.isEnabled!!)
            {
                val enableBluetoothIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                var activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult> { })

                activityResult.launch(enableBluetoothIntent)
            }

            return true;
        }
    }

    private fun getBluetoothAdapter(): BluetoothAdapter?
    {
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            Toast.makeText(this@BluetoothActivity, "Your device does not support bluetooth", Toast.LENGTH_LONG).show();
        }

        return bluetoothAdapter;
    }
}