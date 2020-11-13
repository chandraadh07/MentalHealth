package com.example.mentalhealth

import androidx.room.*

@Dao
interface RecDAO {
    @Query("Select * FROM recommendedTable")
    fun getAll(): Array<VideoRec>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(video: VideoRec)

    @Delete
    fun delete(video: VideoRec)

    @Query("DELETE FROM recommendedTable")
    fun deleteAll()

//    @Query("DELETE FROM ytTable WHERE name=:cityname")
//    fun deleteCityByName(cityname: String)

}