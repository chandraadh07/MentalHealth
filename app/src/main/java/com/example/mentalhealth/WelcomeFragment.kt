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
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        //making buttons show what they did before
        when (sharedPreferences?.getBoolean("isFirstTime", true)){
            false -> {
                //findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
                findNavController().navigate(R.id.action_global_buttonsFragment)
            }
        }

        val editor = sharedPreferences?.edit()
        editor?.apply{
            putBoolean("isFirstTime", false)
        }?.apply()

        btnToProceed.setOnClickListener {
            findNavController().navigate(R.id.action_global_buttonsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }
}