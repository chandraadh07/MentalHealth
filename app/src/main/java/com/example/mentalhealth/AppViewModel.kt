package com.example.mentalhealth
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

    // for finding recommended video ids
    fun filterVideos(
        byProv:Boolean=false,
        byHob:Boolean=false,
        byMood:Boolean=false,
        filter: List<String>
    ): List<String>? {
        return if (byProv){
            val result =  database.value?.youtubeDAO()?.filterByProvisions(filter[0], filter[1])?.toList()!!
            Log.e("VIEW","$filter results:\n${result.size}")
            result
        } else {
            val result = mutableListOf<String>()
            val videos = database.value?.youtubeDAO()?.filter()!!
                videos.forEach{tuple->
                    val keywords = tuple.keywords.toString().split(" ")
                    filter.forEach {
                        val item = it
                        if (item in keywords){
                            result.add(tuple.videoID!!)
                    }
                }
            }
            Log.e("VIEW",result.size.toString())
            result
        }
    }

    // for translating list of video ids to array of videos
    // also stores each video as a recommended video
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