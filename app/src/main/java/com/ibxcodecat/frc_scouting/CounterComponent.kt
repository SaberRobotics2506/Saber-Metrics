package com.ibxcodecat.frc_scouting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class CounterComponent : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //Number manipulation button declarations
        val makeDecBtn: Button = view.findViewById(R.id.makeDecrementBtn)
        val makeIncBtn: Button = view.findViewById(R.id.makeIncrementBtn)
        val missDecBtn: Button = view.findViewById(R.id.missDecrementBtn)
        val missIncBtn: Button = view.findViewById(R.id.missIncrementBtn)
        
        //Variables to be manipulated (just like my ex in my previous relationship)
        var makeNum: Int = 0
        var missNum: Int = 0

        //Listeners to manipulate the numbers
        makeDecBtn.setOnClickListener()
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                              savedInstanceState: Bundle?): View?{

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counter_component, container, false)
    }


}