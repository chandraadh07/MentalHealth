package com.example.mentalhealth

import androidx.room.*

@Dao
interface YoutubeDAO {
    @Query("Select * FROM ytTable")
    fun getAll():Array<VideoYT>

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insert(video: VideoYT)

    @Delete
    fun delete(video: VideoYT)

    @Query("DELETE FROM ytTable")
    fun deleteAll()

//    @Query("DELETE FROM ytTable WHERE name=:cityname")
//    fun deleteCityByName(cityname: String)

}