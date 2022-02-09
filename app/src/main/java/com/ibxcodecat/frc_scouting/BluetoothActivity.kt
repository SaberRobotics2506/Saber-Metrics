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

        //Toast.makeText(this@BluetoothActivity, "⚠ DO NOT DISABLE BLUETOOTH OR EXIT THIS ACTIVITY ⚠", Toast.LENGTH_LONG).show()

        val teamNumber: String = intent.getStringExtra("Team Number")!! //-1 will be invalid in checkData()
        val matchNumber: String = intent.getStringExtra("Match Number")!!

        if(checkData(teamNumber, matchNumber))
        {
            if(setupBluetooth())
            {
                updateLoadingText("Sending your data...")
                //throwError("ERR: 1 [Permission Denied]")
            }
            else
            {
                throwError("ERR 0: [Null Adapter]")
            }
        }
        else
        {
            val kickIntent = Intent(this, DataEntryActivity::class.java) //Create submission intent and activity
            startActivity(kickIntent)
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

        val validator = DataValidator();

        if(validator.ValidateTeamNumber(teamNumber))
        {
            //The team number is valid

            if(validator.ValidateMatchNumber(matchNumber))
            {
                //The team number and match number are valid
                return true;
            }
        }

        throwError("ERR: 2 [BAD DATA]")
        return false;
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