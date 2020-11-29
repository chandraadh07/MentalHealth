package com.example.mentalhealth

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_buttons.*

class ButtonsFragment : Fragment() {

    lateinit var dialogBuilder: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    lateinit var cardView: CardView
    lateinit var textView: TextView
    lateinit var cancel_button: Button


    fun seeMoreInfoDialog(){
        dialogBuilder = AlertDialog.Builder(activity)
        val infoPopupView: View = layoutInflater.inflate(R.layout.popup,null)
        cardView = infoPopupView.findViewById<CardView>(R.id.card_view)
        textView = infoPopupView.findViewById<TextView>(R.id.text)
        cancel_button = infoPopupView.findViewById<Button>(R.id.cancel_butt)

        dialogBuilder.setView(infoPopupView)
        dialog = dialogBuilder.create()
        dialog.show()
        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)

        cancel_button.setOnClickListener{
            dialog.dismiss()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_buttonsFragment_to_question2Fragment)
        }

        btnCurrentMood.setOnClickListener{
            findNavController().navigate(R.id.action_buttonsFragment_to_currentMoodFragment)
        }

        btnHobbies.setOnClickListener{
            findNavController().navigate(R.id.action_buttonsFragment_to_hobbiesFragment)
        }

        btnHome.setOnClickListener{
            findNavController().navigate(R.id.action_global_homeFragment)
        }

        more_info.setOnClickListener { seeMoreInfoDialog() }

        buttonNavigation.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.home_menu){
                findNavController().navigate(R.id.action_global_homeFragment)
            }
            if (it.itemId == R.id.history_menu){
                findNavController().navigate(R.id.action_global_historyFragment)
            }
            if (it.itemId == R.id.Questionnaire_menu){
                findNavController().navigate(R.id.action_global_buttonsFragment)
            }

            true
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