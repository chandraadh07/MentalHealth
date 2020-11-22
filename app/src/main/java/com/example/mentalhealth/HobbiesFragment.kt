package com.example.mentalhealth

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import kotlinx.android.synthetic.main.fragment_hobbies.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
var hobbyStringArray = ArrayList<String>()
lateinit var hobbyAdapter: ArrayAdapter<String>
lateinit var autoTextView : AppCompatAutoCompleteTextView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [HobbiesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

    }

    override fun onItemClick(adapter: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        val hobby = adapter?.getItemAtPosition(position) // The item that was clicked
        //val intent = Intent(this, BookDetailActivity::class.java)
        //startActivity(intent)
        hobby_text.text = hobby.toString()
        autoTextView.setText("")
    }

    fun loadData() {
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

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hobbies, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HobbiesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HobbiesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}