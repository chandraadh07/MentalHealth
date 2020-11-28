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

            btnExcited.alpha = 1F
            btnHappy.alpha = 0.2F
            btnCalm.alpha = 0.2F
            btnAngry.alpha = 0.2F
            btnSurprised.alpha = 0.2F
            btnSad.alpha = 0.2F
            btnConfused.alpha = 0.2F
            btnGrateful.alpha = 0.2F
            btnSleepy.alpha = 0.2F

            saveData()
        }

        btnHappy.setOnClickListener{
            mood = "happy"
            text_emotion.text = "You feel " + mood + " right now"

            btnExcited.alpha = 0.2F
            btnHappy.alpha = 1F
            btnCalm.alpha = 0.2F
            btnAngry.alpha = 0.2F
            btnSurprised.alpha = 0.2F
            btnSad.alpha = 0.2F
            btnConfused.alpha = 0.2F
            btnGrateful.alpha = 0.2F
            btnSleepy.alpha = 0.2F

            saveData()
        }

        btnCalm.setOnClickListener{
            mood = "calm"
            text_emotion.text = "You feel " + mood + " right now"

            btnExcited.alpha = 0.2F
            btnHappy.alpha = 0.2F
            btnCalm.alpha = 1F
            btnAngry.alpha = 0.2F
            btnSurprised.alpha = 0.2F
            btnSad.alpha = 0.2F
            btnConfused.alpha = 0.2F
            btnGrateful.alpha = 0.2F
            btnSleepy.alpha = 0.2F

            saveData()
        }

        btnAngry.setOnClickListener{
            mood = "angry"
            text_emotion.text = "You feel " + mood + " right now"

            btnExcited.alpha = 0.2F
            btnHappy.alpha = 0.2F
            btnCalm.alpha = 0.2F
            btnAngry.alpha = 1F
            btnSurprised.alpha = 0.2F
            btnSad.alpha = 0.2F
            btnConfused.alpha = 0.2F
            btnGrateful.alpha = 0.2F
            btnSleepy.alpha = 0.2F

            saveData()
        }

        btnSurprised.setOnClickListener{
            mood = "surprised"
            text_emotion.text = "You feel " + mood + " right now"

            btnExcited.alpha = 0.2F
            btnHappy.alpha = 0.2F
            btnCalm.alpha = 0.2F
            btnAngry.alpha = 0.2F
            btnSurprised.alpha = 1F
            btnSad.alpha = 0.2F
            btnConfused.alpha = 0.2F
            btnGrateful.alpha = 0.2F
            btnSleepy.alpha = 0.2F

            saveData()
        }

        btnSad.setOnClickListener{
            mood = "sad"
            text_emotion.text = "You feel " + mood + " right now"

            btnExcited.alpha = 0.2F
            btnHappy.alpha = 0.2F
            btnCalm.alpha = 0.2F
            btnAngry.alpha = 0.2F
            btnSurprised.alpha = 0.2F
            btnSad.alpha = 1F
            btnConfused.alpha = 0.2F
            btnGrateful.alpha = 0.2F
            btnSleepy.alpha = 0.2F

            saveData()
        }

        btnGrateful.setOnClickListener{
            mood = "grateful"
            text_emotion.text = "You feel " + mood + " right now"

            btnExcited.alpha = 0.2F
            btnHappy.alpha = 0.2F
            btnCalm.alpha = 0.2F
            btnAngry.alpha = 0.2F
            btnSurprised.alpha = 0.2F
            btnSad.alpha = 0.2F
            btnConfused.alpha = 0.2F
            btnGrateful.alpha = 1F
            btnSleepy.alpha = 0.2F

            saveData()
        }

        btnConfused.setOnClickListener{
            mood = "confused"
            text_emotion.text = "You feel " + mood + " right now"

            btnExcited.alpha = 0.2F
            btnHappy.alpha = 0.2F
            btnCalm.alpha = 0.2F
            btnAngry.alpha = 0.2F
            btnSurprised.alpha = 0.2F
            btnSad.alpha = 0.2F
            btnConfused.alpha = 1F
            btnGrateful.alpha = 0.2F
            btnSleepy.alpha = 0.2F

            saveData()
        }

        btnSleepy.setOnClickListener{
            mood = "tired"
            text_emotion.text = "You feel " + mood + " right now"

            btnExcited.alpha = 0.2F
            btnHappy.alpha = 0.2F
            btnCalm.alpha = 0.2F
            btnAngry.alpha = 0.2F
            btnSurprised.alpha = 0.2F
            btnSad.alpha = 0.2F
            btnConfused.alpha = 0.2F
            btnGrateful.alpha = 0.2F
            btnSleepy.alpha = 1F

            saveData()
        }

        //check in data saved when next button clicked
        btnMoodCheckIns.setOnClickListener{
            findNavController().navigate(R.id.action_global_buttonsFragment)
        }
    }

    fun loadData(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        //getting the emotion from preferences and setting the text to have it
        when (sharedPreferences?.getString("mood", "")){
            "excited" -> {
                mood = "excited"

                btnExcited.alpha = 1F
                btnHappy.alpha = 0.2F
                btnCalm.alpha = 0.2F
                btnAngry.alpha = 0.2F
                btnSurprised.alpha = 0.2F
                btnSad.alpha = 0.2F
                btnConfused.alpha = 0.2F
                btnGrateful.alpha = 0.2F
                btnSleepy.alpha = 0.2F
            }
            "happy" -> {
                mood = "happy"

                btnExcited.alpha = 0.2F
                btnHappy.alpha = 1F
                btnCalm.alpha = 0.2F
                btnAngry.alpha = 0.2F
                btnSurprised.alpha = 0.2F
                btnSad.alpha = 0.2F
                btnConfused.alpha = 0.2F
                btnGrateful.alpha = 0.2F
                btnSleepy.alpha = 0.2F
            }
            "calm" -> {
                mood = "calm"

                btnExcited.alpha = 0.2F
                btnHappy.alpha = 0.2F
                btnCalm.alpha = 1F
                btnAngry.alpha = 0.2F
                btnSurprised.alpha = 0.2F
                btnSad.alpha = 0.2F
                btnConfused.alpha = 0.2F
                btnGrateful.alpha = 0.2F
                btnSleepy.alpha = 0.2F
            }
            "angry" -> {
                mood = "angry"

                btnExcited.alpha = 0.2F
                btnHappy.alpha = 0.2F
                btnCalm.alpha = 0.2F
                btnAngry.alpha = 1F
                btnSurprised.alpha = 0.2F
                btnSad.alpha = 0.2F
                btnConfused.alpha = 0.2F
                btnGrateful.alpha = 0.2F
                btnSleepy.alpha = 0.2F
            }
            "surprised" -> {
                mood = "surprised"

                btnExcited.alpha = 0.2F
                btnHappy.alpha = 0.2F
                btnCalm.alpha = 0.2F
                btnAngry.alpha = 0.2F
                btnSurprised.alpha = 1F
                btnSad.alpha = 0.2F
                btnConfused.alpha = 0.2F
                btnGrateful.alpha = 0.2F
                btnSleepy.alpha = 0.2F
            }
            "sad" -> {
                mood = "sad"

                btnExcited.alpha = 0.2F
                btnHappy.alpha = 0.2F
                btnCalm.alpha = 0.2F
                btnAngry.alpha = 0.2F
                btnSurprised.alpha = 0.2F
                btnSad.alpha = 1F
                btnConfused.alpha = 0.2F
                btnGrateful.alpha = 0.2F
                btnSleepy.alpha = 0.2F
            }
            "grateful" -> {
                mood = "grateful"

                btnExcited.alpha = 0.2F
                btnHappy.alpha = 0.2F
                btnCalm.alpha = 0.2F
                btnAngry.alpha = 0.2F
                btnSurprised.alpha = 0.2F
                btnSad.alpha = 0.2F
                btnConfused.alpha = 0.2F
                btnGrateful.alpha = 1F
                btnSleepy.alpha = 0.2F
            }
            "confused" -> {
                mood = "confused"

                btnExcited.alpha = 0.2F
                btnHappy.alpha = 0.2F
                btnCalm.alpha = 0.2F
                btnAngry.alpha = 0.2F
                btnSurprised.alpha = 0.2F
                btnSad.alpha = 0.2F
                btnConfused.alpha = 1F
                btnGrateful.alpha = 0.2F
                btnSleepy.alpha = 0.2F
            }
            "tired" -> {
                mood = "tired"

                btnExcited.alpha = 0.2F
                btnHappy.alpha = 0.2F
                btnCalm.alpha = 0.2F
                btnAngry.alpha = 0.2F
                btnSurprised.alpha = 0.2F
                btnSad.alpha = 0.2F
                btnConfused.alpha = 0.2F
                btnGrateful.alpha = 0.2F
                btnSleepy.alpha = 1F
            }
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