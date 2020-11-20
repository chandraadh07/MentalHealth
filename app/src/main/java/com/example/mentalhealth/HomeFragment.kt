package com.example.mentalhealth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.math.pow
import kotlin.math.sqrt

class HomeFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // read in user responses to "check-ins"
        val results = getCheckIns()
        var hasAnswered = true


        //checking to see if the user has answered all 3 questions
        results.forEach{
            if (it == 0.0){
                hasAnswered = false
            }
        }

        if (hasAnswered){
            // BLANK MAP PLEASE GET REAL MAP VALUES HERE
            val map = emptyMap<String, List<Double>>()

            // findProvisions(provision_data, userResults)
            findProvisions(map,results)
        }
    }

    //returns a list of question responses, each is a double 1.0 to 5.0
    fun getCheckIns(): List<Double>{
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)
        val answerList = arrayListOf<Double>()

        // getting responses from preferences
        answerList.add(sharedPreferences?.getFloat("r1", 0.0F)?.toDouble()!!)
        answerList.add(sharedPreferences.getFloat("r2", 0.0F).toDouble())
        answerList.add(sharedPreferences.getFloat("r3", 0.0F).toDouble())

        return answerList
    }

    fun findProvisions(provisionCoords: Map<String, List<Double>> , userResults: List<Double>): List<String> {
        // Matches a provision(s) with the user based on their questionnaire results.
        // Uses xyz distance formula to calculate which provision(s) the user's emotion score
        // is closest to on a 3d plane of boredom, stress, and loneliness.

        // Create x,y,z coordinate for user's emotion scores
        val userCoord = listOf((userResults[0]/15)*100,(userResults[1]/15)*100,(userResults[2]/15)*100)
        val userDistances: MutableMap<String, Double> = mutableMapOf()// list of distances between user score and each provision

        // Calculate and store distances
        for ((prov, coord) in provisionCoords) {
            var x1 = userCoord[0]
            var y1 = userCoord[1]
            var z1 = userCoord[2]

            var x2 = coord[0]
            var y2 = coord[1]
            var z2 = coord[2]

            var result = sqrt((x2 - x1).pow(2)+(y2 - y1).pow(2)+(z2 - z1).pow(2))
            userDistances[prov] = result
        }

        // Sort distances ascending
        userDistances.toList()
            .sortedBy { (key,value)->value }
            .toMap()

        // Get and return lowest two distances
        return userDistances.keys.toList().slice(0..1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}