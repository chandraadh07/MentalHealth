package com.example.mentalhealth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_question2.*

class Question2Fragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        //check in data saved when next button clicked
        btnNext_Question1.setOnClickListener{
            saveData()
            findNavController().navigate(R.id.action_question2Fragment_to_question3Fragment)
        }
    }

    fun loadData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        //making buttons show what they did before
        when (sharedPreferences?.getFloat("r1", 0.0F)){
            1.0F -> rbStronglyDisagree.isChecked = true
            2.0F -> rbDisagree.isChecked = true
            3.0F -> rbNeutral.isChecked = true
            4.0F -> rbAgree.isChecked = true
            5.0F -> rbStronglyAgree.isChecked = true
        }
    }

    fun saveData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        var r1 = 0.0F
        when {
            rbStronglyDisagree.isChecked -> r1 = 1.0F
            rbDisagree.isChecked -> r1 = 2.0F
            rbNeutral.isChecked -> r1 = 3.0F
            rbAgree.isChecked -> r1 = 4.0F
            rbStronglyAgree.isChecked -> r1 = 5.0F
        }

        val editor = sharedPreferences?.edit()
        editor?.apply{
            putFloat("r1", r1)
        }?.apply()

        Toast.makeText(activity, "response saved", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question2, container, false)
    }
}