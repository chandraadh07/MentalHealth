package com.example.mentalhealth
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

// Preferences to keep track of local questionnaire and hobby data

class AppViewModel: ViewModel() {

    val watchedList = MutableLiveData<Array<Video>>()
    val recList = MutableLiveData<Array<Video>>()

    // when the user selects a video from the video recycler
    val currentVideo = MutableLiveData<Video>()

    // reference to the database
    val database = MutableLiveData<YoutubeDB>()

    // which question fragment has most recently been completed
    val currentQuestionFrag = MutableLiveData<String>()


    init {
        currentQuestionFrag.value = ""
        currentVideo.value = Video()
        getRecVids()
        getWatchedVids()
    }

    fun setBackFragTo(fragName: String){
        currentQuestionFrag.value = fragName
        currentQuestionFrag.postValue(fragName)
    }

    //used when a user clicks on a video to watch
    fun setCurrentVideo(video: Video) {
        currentVideo.value = video
        currentVideo.postValue(video)
    }

    fun isLikedOrDisliked(videoId: String,askIsLiked: Boolean =false) : Boolean{
        return if (askIsLiked) {
            database.value?.youtubeDAO()?.isLiked(videoId) ==1
        } else {
            database.value?.youtubeDAO()?.isDisliked(videoId) ==1
        }
    }

    //making a video "recommended"
    fun markAsRecommended(video: Video){
        database.value?.youtubeDAO()?.recommend(video.videoID)
    }

    //getting all recommended videos in the database
    fun getRecVids(){
        recList.value = database.value?.youtubeDAO()?.getAllRec()
    }

    //getting all watched videos in the database
    fun getWatchedVids(){
        watchedList.value = database.value?.youtubeDAO()?.getAllWatched()
    }

    fun filterVideos(
        byProv:Boolean=false,
        byHob:Boolean=false,
        byMood:Boolean=false,
        filter: List<String>
    ): List<String>? {
        // if we're filtering by provision...
        return if (byProv){
            // call the filter by provision query in DAO
            val result =  database.value?.youtubeDAO()?.filterByProvisions(filter[0], filter[1])?.toList()!!
            Log.e("VIEW","$filter results:\n${result.size}")
            // return resulted list of video IDS
            result
        } else {
            val result = mutableListOf<String>()
            filter.forEach{ // was originally = filter.forEach
                val hobby:String = it
                val IDs = database.value?.youtubeDAO()?.filterByHobby(hobby)?.toList()!!
                Log.e("VIEW","$hobby results:\n${IDs.size}")
                result.addAll(IDs)
            }
            result
        }
    }

    fun idsToVideos(videoIds:List<String>):Array<Video>{
        val result = emptyList<Video>().toMutableList()
        videoIds.forEach{
            val video = database.value?.youtubeDAO()?.getVideoByID(it)
            markAsRecommended(video!!)
            result+=(video)
        }
        return result.toTypedArray()
    }
}