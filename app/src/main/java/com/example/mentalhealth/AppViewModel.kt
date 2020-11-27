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


    // FUNCTION WHICH CURRENTLY HAS A BUG!?
    // takes in a filter (either by provision, hobby or mood) and returns a list of videos
    // filtered by that condition

    // METHODS I have tried:
        // Does the hobby exist in a list of keywords?
        // Is the hobby inside a string of all keywords
        // Does the hobby.equals(each word in keywords)
    // I HAVE ALSO:
        // Tried translating preference strings to bytes and then to strings again
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

        // otherwise (mood or hobbies)
        // This should use a SQL query to return a list of videos that contain one of the User Hobbies
        // However it doesn't work (I suspect it's because preference Strings are wonky and incomparable
        // to Kotlin strings for whatever reason
        } else {
            val result = mutableListOf<String>()
            // here I'm adding a new filter list to test if the DAO query will recognize these strings
            val filter2 = listOf<String>("cat","animal","dog","pet","kitten")



            filter.forEach{ // was originally = filter.forEach
                val hobby:String = it
                val IDs = database.value?.youtubeDAO()?.filterByHobby(hobby)?.toList()!!
                Log.e("VIEW","$hobby results:\n${IDs.size}")
                result.addAll(IDs)
            }
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




//// get a list of (videoID, keywords) tuples for all videos in DB
//val videos = database.value?.youtubeDAO()?.filter()!!
//// for each video, check if one of our hobbies/mood exists in the keywords
//// if one does, add that video to our list
//videos.forEach{tuple->
//    // split keyword string into list of strings
//    val keywords = tuple.keywords.toString().split(" ")
//    filter.forEach {
//        val item = it
//
//        // HERE IS THE ISSUE :^(
//        // this checks if item (a hobby or mood) exists in the list of keywords
//        // HOWEVER, no matter how I frame this (whether I see if item exists in the
//        // list of keywords, or the item exists in a string of all keywords, or
//        // the item.equals(some keyword in the list), it doesn't work how it should
//        if (item in keywords){
//            Log.e("VIEW",tuple.videoID.toString())
//            result.add(tuple.videoID!!)
//        }
//    }
//}
//val b:Boolean = "zebra" == "zebra"
//Log.e("VIEW",b.toString())
//Log.e("VIEW",result.size.toString())
//result