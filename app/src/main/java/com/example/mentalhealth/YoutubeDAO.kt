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

    /*
    1. find videos with similar 2 provisions (both at once)
    2. find videos with similar 5 hobbies (individually)
    3. find videos with similar 1 mood


    1.
    We need to find out how to write a command that looks at SQL value and sees if it has some
    combination of two provisions we want (e.g. if we wanted attachment&guidance but it said
    "attachment reassurance guidance", we want that video still
    SELECT videoID FROM videoTable WHERE provisions (is like) provision1 provision2

    // 2.
    // SELECT videoID FROM videoTable WHERE hobbies (is like) :hobby
    */
}