package com.example.mentalhealth

import androidx.room.*

@Dao
interface WatchedDAO {
    @Query("Select * FROM watchedTable")
    fun getAll(): Array<VideoWatched>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(video: VideoWatched)

    @Delete
    fun delete(video: VideoWatched)

    @Query("DELETE FROM watchedTable")
    fun deleteAll()

//    @Query("DELETE FROM ytTable WHERE name=:cityname")
//    fun deleteCityByName(cityname: String)

}