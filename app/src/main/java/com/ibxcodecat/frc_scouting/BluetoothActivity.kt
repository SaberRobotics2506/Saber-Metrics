package com.ibxcodecat.frc_scouting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frc_scouting.R

class BluetoothActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        Toast.makeText(this@BluetoothActivity, "⚠ DO NOT DISABLE BLUETOOTH OR EXIT THIS ACTIVITY ⚠", Toast.LENGTH_LONG).show()
    }
}