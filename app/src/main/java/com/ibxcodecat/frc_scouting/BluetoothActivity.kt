package com.ibxcodecat.frc_scouting

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.frc_scouting.R


class BluetoothActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        val teamNumber: String = intent.getStringExtra("Team Number")!! //-1 will be invalid in checkData()
        val matchNumber: String = intent.getStringExtra("Match Number")!!


        updateLoadingText("Validating your data...")

        if(checkData(teamNumber, matchNumber))
        {
            updateLoadingText("Setting up Bluetooth...")

            if(setupBluetooth())
            {
                updateLoadingText("Sending your data...")
            }
            else
            {
                throwError("ERR 0x02: [Null Adapter]") //T
            }
        }
    }

    private fun updateLoadingText(text: String) { findViewById<TextView>(R.id.wait).text = text }

    private fun throwError(text: String)
    {
        val view: TextView = findViewById<TextView>(R.id.wait);
        view.text = text;
        view.setTextColor(Color.RED)
    }

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