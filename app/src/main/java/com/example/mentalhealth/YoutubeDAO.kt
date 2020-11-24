package com.example.mentalhealth

import androidx.room.*

@Dao
interface YoutubeDAO {

    @Query("Select * FROM videoTable")
    fun getAll():Array<Video>

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insert(video: Video)

    @Query("UPDATE videoTable SET isRec = 1 WHERE videoID =:id")
    fun recommend(id: String)

    @Query("UPDATE videoTable SET isWatched = 1 WHERE videoID =:id")
    fun watch(id: String)

    @Query("SELECT * FROM videoTable WHERE isRec = 1")
    fun getAllRec():Array<Video>

    @Query("SELECT * FROM videoTable WHERE isWatched = 1")
    fun getAllWatched():Array<Video>

    @Query("DELETE FROM videoTable")
    fun deleteAll()



    @Query("SELECT * FROM videoTable WHERE videoID = :id")
    fun getVideoByID(id:String):Video
//    1. find videos with similar 2 provisions (both at once)
//    2. find videos with similar 5 hobbies (individually)
//    3. find videos with similar 1 mood

    @Query("SELECT videoId FROM videoTable WHERE provisions LIKE :prov1 AND provisions LIKE :prov2")
    fun filterByProvisions(prov1: String,prov2: String) :Array<String>

    @Query("SELECT videoId FROM videoTable WHERE keywords LIKE :hobby")
    fun filterByHobby(hobby: String) :Array<String>


    @Query("SELECT videoId FROM videoTable WHERE moods LIKE :mood")
    fun filterByMood(mood: String) :Array<String>
}