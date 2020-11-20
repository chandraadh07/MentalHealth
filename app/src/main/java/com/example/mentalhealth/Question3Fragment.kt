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
import kotlinx.android.synthetic.main.fragment_question3.*

class Question3Fragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        //check in data saved when next button clicked
        btnNext_Question2.setOnClickListener{
            saveData()
            findNavController().navigate(R.id.action_question3Fragment_to_question4Fragment)
        }
    }

    fun loadData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        //making buttons show what they did before
        when (sharedPreferences?.getFloat("r2", 0.0F)){
            1.0F -> rbStronglyDisagree2.isChecked = true
            2.0F -> rbDisagree2.isChecked = true
            3.0F -> rbNeutral2.isChecked = true
            4.0F -> rbAgree2.isChecked = true
            5.0F -> rbStronglyAgree2.isChecked = true
        }
    }

    fun saveData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        var r2 = 0.0F
        when {
            rbStronglyDisagree2.isChecked -> r2 = 1.0F
            rbDisagree2.isChecked -> r2 = 2.0F
            rbNeutral2.isChecked -> r2 = 3.0F
            rbAgree2.isChecked -> r2 = 4.0F
            rbStronglyAgree2.isChecked -> r2 = 5.0F
        }

        val editor = sharedPreferences?.edit()
        editor?.apply{
            putFloat("r2", r2)
        }?.apply()

        Toast.makeText(activity, "response saved", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question3, container, false)
    }

}