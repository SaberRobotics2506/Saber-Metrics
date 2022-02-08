package com.ibxcodecat.frc_scouting

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
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

        Toast.makeText(this@BluetoothActivity, "⚠ DO NOT DISABLE BLUETOOTH OR EXIT THIS ACTIVITY ⚠", Toast.LENGTH_LONG).show()

        if(setupBluetooth())
        {
            //Do stuff
        }
    }


    fun setupBluetooth(): Boolean
    {
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

                activityResult.launch(enableBluetoothIntent);
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