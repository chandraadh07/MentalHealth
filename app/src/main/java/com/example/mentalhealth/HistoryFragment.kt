package com.example.mentalhealth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_buttons.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_history.buttonNavigation


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {


    val viewModel: AppViewModel by activityViewModels()
    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAdapter: HistoryRecyclerView
    lateinit var watchedVideos: Array<Video>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        watchedVideos=viewModel.database.value?.youtubeDAO()?.getAllWatched()!!
        Log.d("watched ", watchedVideos.toString())
    // RECYCLER VIEW
        viewManager = LinearLayoutManager(activity)
        viewAdapter = HistoryRecyclerView(watchedVideos, this ) { videoItem: Video ->
            videoItemClicked((videoItem))
        }

    recycler_view_history.adapter = viewAdapter
    recycler_view_history.layoutManager = viewManager
    recycler_view_history.adapter = viewAdapter

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



    fun videoItemClicked(video: Video) {
        viewModel.setCurrentVideo(video)
        findNavController().navigate(R.id.action_global_videosFragment)
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_history, container, false)
        }

    fun isLiked(videoId:String): Boolean {
        return viewModel.isLikedOrDisliked(videoId,askIsLiked = true)
    }

    fun isDisliked(videoId:String): Boolean {
        return viewModel.isLikedOrDisliked(videoId,askIsLiked = false)
    }
}