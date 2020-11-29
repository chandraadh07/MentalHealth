package com.example.mentalhealth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_exit.*

class ExitFragment : Fragment() {

    val viewModel: AppViewModel by activityViewModels<AppViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Log.d("exit", )

        btnCONTINUE.setOnClickListener {

            viewModel.currentQuestionFrag.observe(viewLifecycleOwner, {
                Log.d("exit", "should take me to the " + it + " fragment")

                when (it) {
                    //"question2" -> findNavController().navigate(R.id.action_exitFragment_to_question2Fragment)
                    "question2" -> findNavController().navigate(R.id.action_global_question2Fragment)
                    "question3" -> findNavController().navigate(R.id.action_global_question3Fragment)
                    "moods" -> findNavController().navigate(R.id.action_global_currentMoodFragment)
                }
            })
        }

        btnVIDEOS.setOnClickListener {
            findNavController().navigate(R.id.action_global_buttonsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exit, container, false)
    }

}