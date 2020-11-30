package com.example.mentalhealth

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_question2.*

class Question2Fragment : Fragment() {

    val viewModel: AppViewModel by activityViewModels<AppViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setBackFragTo("question2")

        // loads preference data
        loadData()

        //check in data saved when next button clicked
        next1.setOnClickListener{
            saveData()
            findNavController().navigate(R.id.action_question2Fragment_to_question3Fragment)
        }

        exit1.setOnClickListener {
            findNavController().navigate(R.id.action_global_exitFragment)
        }
    }

    fun loadData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)
        //making buttons show what they did before
        when (sharedPreferences?.getFloat("r1", 0.0F)){
            1.0F -> a_Sch.isChecked = true
            2.0F -> a_Fri.isChecked = true
            3.0F -> a_Fam.isChecked = true
        }
    }

    fun saveData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        var r1 = 0.0F
        when {
            a_Sch.isChecked -> r1 = 1.0F
            a_Fri.isChecked -> r1 = 2.0F
            a_Fam.isChecked -> r1 = 3.0F
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