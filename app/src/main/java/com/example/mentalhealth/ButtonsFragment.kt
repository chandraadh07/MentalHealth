package com.example.mentalhealth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_buttons.*

class ButtonsFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_buttonsFragment_to_question2Fragment)
        }

        btnCurrentMood.setOnClickListener{
            findNavController().navigate(R.id.action_buttonsFragment_to_currentMoodFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buttons, container, false)
    }
}