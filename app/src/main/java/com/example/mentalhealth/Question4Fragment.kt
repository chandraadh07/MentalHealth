package com.example.mentalhealth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_question2.*
import kotlinx.android.synthetic.main.fragment_question4.*

class Question4Fragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        //check in data saved when next button clicked
        btnNext_Question3.setOnClickListener{
            saveData()
            findNavController().navigate(R.id.action_global_buttonsFragment)
        }
    }

    fun loadData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        //making buttons show what they did before
        when (sharedPreferences?.getFloat("r3", 0.0F)){
            1.0F -> rbStronglyDisagree3.isChecked = true
            2.0F -> rbDisagree3.isChecked = true
            3.0F -> rbNeutral3.isChecked = true
            4.0F -> rbAgree3.isChecked = true
            5.0F -> rbStronglyAgree3.isChecked = true
        }
    }

    fun saveData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        var r3 = 0.0F
        when {
            rbStronglyDisagree3.isChecked -> r3 = 1.0F
            rbDisagree3.isChecked -> r3 = 2.0F
            rbNeutral3.isChecked -> r3 = 3.0F
            rbAgree3.isChecked -> r3 = 4.0F
            rbStronglyAgree3.isChecked -> r3 = 5.0F
        }

        val editor = sharedPreferences?.edit()
        editor?.apply{
            putFloat("r3", r3)
        }?.apply()

        Toast.makeText(activity, "response saved", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question4, container, false)
    }
}