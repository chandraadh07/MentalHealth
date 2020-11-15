package com.example.mentalhealth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.math.pow
import kotlin.math.sqrt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

        // read in data about provisions
        // read in user responses to "check-ins"
        // findProvisions(provision_data, userResults)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun findProvisions(provisionCoords: Map<String, List<Double>> , userResults: List<Double>): List<String> {
        // Matches a provision(s) with the user based on their questionnaire results.
        // Uses xyz distance formula to calculate which provision(s) the user's emotion score
        // is closest to on a 3d plane of boredom, stress, and loneliness.

        // Create x,y,z coordinate for user's emotion scores
        val userCoord = listOf((userResults[0]/21)*100,(userResults[1]/21)*100,(userResults[2]/21)*100)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}