package com.example.mentalhealth

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_buttons.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.buttonNavigation
import kotlin.math.pow
import kotlin.math.sqrt

class HomeFragment : Fragment(), AddLifecycleCallbackListener {


    val viewModel: AppViewModel by activityViewModels()
    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAdapter: RecRecyclerView
    lateinit var recommendedData: Array<Video>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recommendedData = getRecommendations()

        Log.e("TAG",recommendedData.size.toString())
//        val list = viewModel.database.value?.youtubeDAO()?.getAll()?.sliceArray(0..3)!!
//        list?.forEach {
//            Log.e(
//                "TAG",
//                it.videoID
//            )
//        }
//        recommendedData = list
        // RECYCLER VIEW
        viewManager = LinearLayoutManager(activity)
        viewAdapter = RecRecyclerView(recommendedData, this) { videoItem: Video ->
            videoItemClicked((videoItem))
        }

        recycler_view.adapter = viewAdapter
        recycler_view.layoutManager = viewManager
        recycler_view.adapter = viewAdapter

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


    // CODE FOR RECYCLER VIEW

    fun videoItemClicked(video: Video) {
        viewModel.setCurrentVideo(video)
        findNavController().navigate(R.id.action_homeFragment_to_videosFragment)
    }


    fun getRecommendations(): Array<Video> {
        Log.d("tests", "getting recommendations")
        // Retrieves filters for each (answered) Check-In and returns an array of filtered recommendations

        val provFilter: List<String>

        // Has user answered Provision check-in?
        var hasAnswered = true
        val sharedPreferences = activity?.getSharedPreferences("checkIns", Context.MODE_PRIVATE)
        val provisionResponses = arrayListOf<Double>(
            sharedPreferences?.getFloat("r1", 0.0F)?.toDouble()!!, //boredom
            sharedPreferences.getFloat("r2", 0.0F).toDouble(), //stress
            sharedPreferences.getFloat("r3", 0.0F).toDouble()
        ) //loneliness
        provisionResponses.forEach {
            if (it == 0.0)
                hasAnswered = false
        }
        provFilter = if (hasAnswered) {
            findProvisions(provisionResponses)
        } else {
            emptyList()
        }

        // get user hobbies
        val hobbies = sharedPreferences.getString("hobbies", "")!!.toByteArray()
        val hobbiesFilter = String(hobbies).split(",")

        // get user mood
        val moodsFilter = sharedPreferences.getString("moods", "")!!.toString()

        Log.e("pref","prov$provFilter\nhob$hobbiesFilter\nmood$moodsFilter")

        // call findVideos with all filters
        return findVideos(provFilter = provFilter, hobFilter = hobbiesFilter, moodFilter = moodsFilter)
    }


    fun findVideos(
        provFilter: List<String>, hobFilter: List<String>, moodFilter: String): Array<Video> {
        // Takes in check-in filters, retrieves filtered videos from database, and returns 20 (or less) videos
        var filteredByProv = emptyList<String>()
        var filteredByHobby = emptyList<String>()
        var filteredByMood = emptyList<String>()

        if (provFilter.isNotEmpty()) {
            filteredByProv =
                viewModel.filterVideos(byProv = true, filter = provFilter)!!
        }

        if (hobFilter.isNotEmpty()) {
            //filter SQL by hob
            filteredByHobby =
                filteredByHobby + (viewModel.filterVideos(byHob = true, filter = hobFilter)!!)
        }
        if (moodFilter != "") {
            //filter SQL by mood
            filteredByMood = viewModel.filterVideos(byMood = true, filter = listOf(moodFilter))!!
        }


        val allIntersection =  filteredByProv.intersect(filteredByHobby).intersect(filteredByMood).toList().shuffled()
        val provAndHobIntersection = filteredByProv.intersect(filteredByHobby).toList().shuffled()
        val allUnion = (filteredByProv.union(filteredByHobby).union(filteredByMood)).toList().shuffled()

        if (allIntersection.size>=10){
            Log.e("FILTER","filter 1: ${allIntersection.size}")
            return viewModel.idsToVideos(allIntersection.slice(0..9))
        }else if (provAndHobIntersection.size >= 10) {
                Log.e("FILTER","filter 2: ${provAndHobIntersection.size}")
                return viewModel.idsToVideos(provAndHobIntersection.slice(0..9))
        }
        else {
            var result = filteredByProv.intersect(filteredByHobby).intersect(filteredByMood).toMutableList()
            val left = allUnion.subtract(result)
            var toAdd = 10-result.size
            result.addAll(left.toList().slice(0..toAdd))
            Log.e("FILTER","filter 3: ${result.size}")
            return viewModel.idsToVideos(result.toList())
        }
    }

    fun findProvisions(userResults: List<Double>): List<String> {
        Log.d("tests", "entered findProvisions")
        // Matches a provision(s) with the user based on their questionnaire results.
        // Sums up scores for each pair of provisions,
        // attachment+nurturance, alliance+integration, reassurance+guidance,
        // and assigns a user two provisions based on highest score
        var provisionCoords = mutableMapOf<String, Double>(
            "reassurance guidance" to 0.0,
            "alliance integration" to 0.0,
            "attachment nurturance" to 0.0
        )

        // Sum up provisions pair scores
        for (prov in userResults) {
            if (prov == 1.0) {
                provisionCoords["reassurance guidance"] =
                    provisionCoords["reassurance guidance"]?.plus(1.0)!!
            } else if (prov == 2.0) {
                provisionCoords["alliance integration"] =
                    provisionCoords["alliance integration"]?.plus(1.0)!!
            } else if (prov == 3.0) {
                provisionCoords["attachment nurturance"] =
                    provisionCoords["attachment nurturance"]?.plus(1.0)!!
            }
        }

        // Sort distances ascending
        val map = provisionCoords.toList()
            .sortedByDescending { (key, value) -> value }
            .toMap()

        // Get and return lowest two distances
        Log.e("tests", "${map.keys.toList()[0].split(" ")}") //.slice(0..2).toList().joinToString(",")}")
        return map.keys.toList()[0].split(" ")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun addLifeCycleCallBack(youTubePlayerView: YouTubePlayerView?) {
        lifecycle.addObserver(youTubePlayerView!!)
    }
}