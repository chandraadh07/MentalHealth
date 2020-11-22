package com.example.mentalhealth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.pow
import kotlin.math.sqrt

class HomeFragment : Fragment() {


    val viewModel: AppViewModel by viewModels<AppViewModel>()
    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAdapter: RecRecyclerView
    lateinit var recommendedData : Array<Video>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recommendedData = getRecommendations()

        // RECYCLER VIEW
        viewManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewAdapter = RecRecyclerView(recommendedData) { movieItem: Video ->
            videoItemClicked((movieItem))
        }

        recycler_view.adapter = viewAdapter
        recycler_view.layoutManager = viewManager
        recycler_view.adapter = viewAdapter
    }


    // CODE FOR RECYCLER VIEW

    fun videoItemClicked(videoItem: Video) {
//        viewModel.setCurrentVideo(videoItem)
//        findNavController().navigate(R.id.action_navigation_home_to_movieDetailFragment)
    }




    fun getRecommendations(): Array<Video> {
        // Retrieves filters for each (answered) Check-In and returns an array of filtered recommendations

        var provFilter: Array<String>
        var hobFilter:Array<String>
        var moodFilter:String

        // Has user answered Provision check-in?
        var hasAnswered = true
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)
        val provisionResponses = arrayListOf<Double>(
            sharedPreferences?.getFloat("r1", 0.0F)?.toDouble()!!, //boredom
            sharedPreferences.getFloat("r2", 0.0F).toDouble(), //stress
            sharedPreferences.getFloat("r3", 0.0F).toDouble()) //loneliness
        provisionResponses.forEach {
            if (it == 0.0)
                hasAnswered = false
        }
        provFilter = if (hasAnswered){
            findProvisions(provisionResponses)
        } else{
            emptyArray()}

        // Has user answered Hobby check-in?
        hobFilter = emptyArray()

        // Has user answered Mood check-in?
        moodFilter = ""

        // call findVideos with all filters
        return findVideos(provFilter = provFilter,hobFilter = hobFilter, moodFilter=moodFilter)
    }



    fun findVideos(provFilter:Array<String>,hobFilter:Array<String>,moodFilter:String): Array<Video> {
        // Takes in check-in filters, retrieves filtered videos from database, and returns 20 (or less) videos
        var filteredByProv = emptyList<String>()
        var filteredByHobby = emptyList<String>()
        var filteredByMood = emptyList<String>()

        if (provFilter.isNotEmpty()){
            filteredByProv = viewModel.filterVideos(byProv=true,filter=provFilter.joinToString {" "})!!
        }
        if (hobFilter.isNotEmpty()){
            //filter SQL by hob`
            hobFilter.forEach {
                filteredByHobby = filteredByHobby+(viewModel.filterVideos(byProv=true,filter=it)!!)
            }
        }
        if (moodFilter.isNotEmpty()){
            //filter SQL by mood
            filteredByMood = viewModel.filterVideos(byMood=true,filter = moodFilter)!!
        }

        var result = filteredByProv.intersect(filteredByHobby).intersect(filteredByMood).toList()
        if (result.size >= 20){
            return viewModel.idsToVideos(result)
        }
        result = (filteredByProv.intersect(filteredByHobby)).toList()
        if (result.size >= 20){
            return viewModel.idsToVideos(result)
        }
        result = (filteredByProv.union(filteredByHobby).union(filteredByMood)).toList()
        return if (result.size > 20) {
            viewModel.idsToVideos(result.slice(0..20).toList())
        } else{
            viewModel.idsToVideos(result)
        }
    }

    fun findProvisions(userResults: List<Double>): Array<String> {
        // Matches a provision(s) with the user based on their questionnaire results.
        // Uses xyz distance formula to calculate which provision(s) the user's emotion score
        // is closest to on a 3d plane of boredom, stress, and loneliness.
        var provisionCoords = mapOf<String,List<Double>>("attachment" to listOf(15.0,70.0,15.0),"integration" to listOf(20.0,40.0,40.0),
            "alliance" to listOf(35.0,35.0,30.0),"reassurance" to listOf(35.0,45.0,30.0),
            "nurturance" to listOf(45.0,40.0,15.0),"guidance" to listOf(35.0,20.0,45.0))

        // Create x,y,z coordinate for user's emotion scores
        val userCoord = listOf((userResults[0]/15)*100,(userResults[1]/15)*100,(userResults[2]/15)*100)
        val userDistances: MutableMap<String, Double> = mutableMapOf()// list of distances between user score and each provision

        // Calculate and store distances
        for ((prov, coord) in provisionCoords) {
            val x1 = userCoord[0]
            val y1 = userCoord[1]
            val z1 = userCoord[2]

            val x2 = coord[0]
            val y2 = coord[1]
            val z2 = coord[2]

            val result = sqrt((x2 - x1).pow(2)+(y2 - y1).pow(2)+(z2 - z1).pow(2))
            userDistances[prov] = result
        }

        // Sort distances ascending
        userDistances.toList()
            .sortedBy { (key,value)->value }
            .toMap()

        // Get and return lowest two distances
        return userDistances.keys.toList().slice(0..1) as Array<String>
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}