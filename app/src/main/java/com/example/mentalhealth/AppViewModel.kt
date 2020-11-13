package com.example.mentalhealth
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// FIGURE OUT HOW TO SORT DATABASE ITEMS IN SQL to sort by similarity

// Preferences to keep track of local questionnaire and hobby data

class AppViewModel: ViewModel() {
    val ytVideoList = MutableLiveData<Array<VideoYT>>()
    val watchedVideoList = MutableLiveData<Array<VideoWatched>>()
    val recVideoList = MutableLiveData<Array<VideoRec>>()

    // when the user selects a video from the video recycler
    val currentVideo = MutableLiveData<VideoWatched>()

    // references to each of the three databases
    val ytDatabase = MutableLiveData<YoutubeDB>()
    val recDatabase = MutableLiveData<RecDB>()
    val watchedDatabase = MutableLiveData<WatchedDB>()

    //setting viewmodel to be the information in the three databases
    init {
        ytVideoList.value = ytDatabase.value?.youtubeDAO()?.getAll()
        recVideoList.value = recDatabase.value?.recDAO()?.getAll()
        watchedVideoList.value = watchedDatabase.value?.watchedDAO()?.getAll()
    }

    // update all three video lists
    fun updateLists(){
        val list1 = ytDatabase.value?.youtubeDAO()?.getAll()
        ytVideoList.postValue(list1)

        val list2 = recDatabase.value?.recDAO()?.getAll()
        recVideoList.postValue(list2)

        val list3 = watchedDatabase.value?.watchedDAO()?.getAll()
        watchedVideoList.postValue(list3)
    }

    // add a single video to the watched list after it has been viewed
    fun addToWatched() {
        currentVideo.value?.let {
            watchedDatabase.value?.watchedDAO()?.insert(it)
        }
        updateLists()
    }

    fun addToRecommended(vid: VideoYT) {
        val addedVid = VideoRec()
        addedVid.videoId = vid.videoId
        addedVid.embedUrl = vid.embedUrl
        addedVid.videoUrl = vid.videoUrl
        addedVid.channelId = vid.channelId
        addedVid.title = vid.title
        addedVid.description = vid.description
        addedVid.tags = vid.tags
        addedVid.style = vid.style
        addedVid.provisions = vid.provisions
        addedVid.publishedAt = vid.publishedAt
        addedVid.viewCount = vid.viewCount
        addedVid.likeCount = vid.likeCount
        addedVid.dislikeCount = vid.dislikeCount
        addedVid.duration = vid.duration

        recDatabase.value?.recDAO()?.insert(addedVid)
    }

    //the code must manually select the last video to delete
    fun deleteRecVid(vid: VideoRec){
        recDatabase.value?.recDAO()?.delete(vid)
        updateLists()
    }

    //called whenever the user saves questionnaire responses
    fun deleteAllRecVids(){
        recDatabase.value?.recDAO()?.deleteAll()
        updateLists()
    }

    //used when a user clicks on a video to watch
    fun setCurrentVideo(video: VideoWatched) {
        currentVideo.value = video
        currentVideo.postValue(video)
    }

    //removes all watched videos_view from the watched list
    fun clearWatchHistory(){
        watchedDatabase.value?.watchedDAO()?.deleteAll()
        updateLists()
    }
}