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

        //Number displays to be manipulated (I won't crack the same joke twice)
        val missText: TextView = view.findViewById(R.id.missNumText)
        val makeText: TextView = view.findViewById(R.id.makeNumText)

        //Listeners to manipulate the numbers
        makeDecBtn.setOnClickListener{makeNum--; if(makeNum < 0) makeNum = 0; if(makeNum == 1) makeText.setText(makeNum.toString() + " bucket") else makeText.setText(makeNum.toString() + " buckets")}
        makeIncBtn.setOnClickListener{makeNum++; if(makeNum == 1) makeText.setText(makeNum.toString() + " bucket") else makeText.setText(makeNum.toString() + " buckets")}
        missDecBtn.setOnClickListener{missNum--; if(missNum < 0) missNum = 0; if(missNum == 1) missText.setText(missNum.toString() + "miss") else missText.setText(missNum.toString() + " misses")}
        missIncBtn.setOnClickListener{missNum++; if(missNum == 1) missText.setText(missNum.toString() + "miss") else missText.setText(missNum.toString() + " misses")}
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                              savedInstanceState: Bundle?): View?{

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counter_component, container, false)
    }


}