package com.example.mentalhealth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_current_mood.*
import kotlinx.android.synthetic.main.fragment_question4.*

class CurrentMoodFragment : Fragment() {

    var mood = ""

    val viewModel: AppViewModel by activityViewModels<AppViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setBackFragTo("moods")

        loadData()

        btngoBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_exitFragment)
        }

        btnExcited.setOnClickListener{
            mood = "excited"
            text_emotion.text = "You feel " + mood + " right now"
        }

        btnHappy.setOnClickListener{
            mood = "happy"
            text_emotion.text = "You feel " + mood + " right now"
        }

        btnCalm.setOnClickListener{
            mood = "calm"
            text_emotion.text = "You feel " + mood + " right now"
        }

        btnAngry.setOnClickListener{
            mood = "angry"
            text_emotion.text = "You feel " + mood + " right now"
        }

        btnSurprised.setOnClickListener{
            mood = "surprised"
            text_emotion.text = "You feel " + mood + " right now"
        }

        btnSad.setOnClickListener{
            mood = "sad"
            text_emotion.text = "You feel " + mood + " right now"
        }

        btnGrateful.setOnClickListener{
            mood = "grateful"
            text_emotion.text = "You feel " + mood + " right now"
        }

        btnConfused.setOnClickListener{
            mood = "confused"
            text_emotion.text = "You feel " + mood + " right now"
        }

        btnSleepy.setOnClickListener{
            mood = "tired"
            text_emotion.text = "You feel " + mood + " right now"
        }

        //check in data saved when next button clicked
        btnMoodCheckIns.setOnClickListener{
            saveData()
            findNavController().navigate(R.id.action_global_buttonsFragment)
        }
    }

    fun loadData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        //getting the emotion from preferences and setting the text to have it
        when (sharedPreferences?.getString("mood", "")){
            "excited" -> mood = "excited"
            "happy" -> mood = "happy"
            "calm" -> mood = "calm"
            "angry" -> mood = "angry"
            "surprised" -> mood = "surprised"
            "sad" -> mood = "sad"
            "grateful" -> mood = "grateful"
            "confused" -> mood = "confused"
            "tired" -> mood = "tired"
        }
        if (mood != ""){
            text_emotion.text = "You feel " + mood + " right now"
        } else {
            text_emotion.text = "Click an emoji which best represents your current mood."
        }
    }

    fun saveData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        // assigns the current mood to the mood preference
        val editor = sharedPreferences?.edit()
        editor?.apply{
            putString("mood", mood)
        }?.apply()

        Toast.makeText(activity, "current mood saved", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_mood, container, false)
    }
}