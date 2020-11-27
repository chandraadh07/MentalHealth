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
import kotlinx.android.synthetic.main.fragment_home.*
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

        // THIS IS THE CALL THAT LEADS TO A BUG... GO TO THE VIEWMODEL METHOD "FILTER VIDEOS"
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
        // Uses xyz distance formula to calculate which provision(s) the user's emotion score
        // is closest to on a 3d plane of boredom, stress, and loneliness.
        var provisionCoords = mapOf<String, List<Double>>(
            "attachment" to listOf(15.0, 70.0, 15.0), "integration" to listOf(20.0, 40.0, 40.0),
            "alliance" to listOf(35.0, 40.0, 25.0), "reassurance" to listOf(35.0, 45.0, 30.0),
            "nurturance" to listOf(45.0, 40.0, 15.0), "guidance" to listOf(35.0, 20.0, 45.0)
        )

        // Create x,y,z coordinate for user's emotion scores
        val userCoord = listOf(
            (userResults[0] / 15) * 100,
            (userResults[1] / 15) * 100,
            (userResults[2] / 15) * 100
        )
        val userDistances: MutableMap<String, Double> =
            mutableMapOf()// list of distances between user score and each provision

        // Calculate and store distances
        for ((prov, coord) in provisionCoords) {
            val x1 = userCoord[0]
            val y1 = userCoord[1]
            val z1 = userCoord[2]

            val x2 = coord[0]
            val y2 = coord[1]
            val z2 = coord[2]

            val result = sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2) + (z2 - z1).pow(2))
            userDistances[prov] = result
        }

        // Sort distances ascending
        val map = userDistances.toList()
            .sortedByDescending { (key, value) -> value }
            .toMap()

        // Get and return lowest two distances
        Log.e("tests", "${map.keys.toList().slice(0..2).toList().joinToString(",")}")
        return map.keys.toList().slice(0..2).toList()
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