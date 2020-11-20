package com.example.mentalhealth
import android.provider.MediaStore
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

    init {
        currentVideo.value = Video()
        getRecVids()
        getWatchedVids()
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

    //making a video "recommended"
    fun markAsWatched(video: Video){
        database.value?.youtubeDAO()?.watch(video.videoID)
    }

    //getting all recommended videos in the database
    fun getRecVids(){
        recList.value = database.value?.youtubeDAO()?.getAllRec()
    }

    //getting all watched videos in the database
    fun getWatchedVids(){
        watchedList.value = database.value?.youtubeDAO()?.getAllWatched()
    }
}