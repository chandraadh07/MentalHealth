package com.example.mentalhealth

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_hobbies.*

class HobbiesFragment : Fragment(), AdapterView.OnItemClickListener  {
    var hobbyStringArray = ArrayList<String>()
    lateinit var hobbyAdapter: ArrayAdapter<String>
    lateinit var autoTextView : AppCompatAutoCompleteTextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData()
        Log.e("TAG", hobbyStringArray.slice(0..5).toString())

        val adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.select_dialog_item, hobbyStringArray) }
        autoTextView = view.findViewById(R.id.autoTextView)
        autoTextView.threshold = 2
        autoTextView.setAdapter(adapter)
        autoTextView.setTextColor(Color.BLUE)
        autoTextView.onItemClickListener = this

        btnDELETE.setOnClickListener {
            deleteHobbies()
            hobby_text.text = ""
        }

        btnBACK.setOnClickListener {
            findNavController().navigate(R.id.action_global_buttonsFragment)
        }
    }

    override fun onItemClick(adapter: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        val hobby = adapter?.getItemAtPosition(position) // The item that was clicked

        autoTextView.setText("")

        //adding to the preference string
        val hobbyString = sharedPreferences?.getString("hobbies", "") + " " +
                hobby.toString()

        // assigns the current mood to the mood preference
        val editor = sharedPreferences?.edit()
        editor?.apply{
            putString("hobbies", hobbyString)
        }?.apply()

        // updating text
        hobby_text.text = hobbyString

        //Toast.makeText(activity, "hobbies saved", Toast.LENGTH_SHORT).show()
    }

    fun loadData() {
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)
        hobby_text.text = sharedPreferences?.getString("hobbies", "no hobbies added")

        val dataString =
            resources.openRawResource(R.raw.keywords).bufferedReader()
                .use { it.readText() }// read the entire file as a string
        var lines = dataString.trim().split("\n") // split each line
        lines = lines.subList(1, lines.size) // get rid of the header line

        lines.forEach {
            val cells = it.split(";")
//            hobbyArray.add(Hobby(cells[0]))
            hobbyStringArray.add(cells[0])
        }
    }

    fun deleteHobbies(){
        // finding preferences
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)

        // removing the hobbies from the preferences
        val editor = sharedPreferences?.edit()
        editor?.apply{
            putString("hobbies", "")
        }?.apply()

        Toast.makeText(activity, "hobbies deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hobbies, container, false)
    }
}