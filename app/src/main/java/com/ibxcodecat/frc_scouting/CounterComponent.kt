package com.ibxcodecat.frc_scouting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CounterComponent : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counter_component, container, false)
    }

    val makeDecBtn = findViewById<Button>(R.id.makeDecrementBtn)
}