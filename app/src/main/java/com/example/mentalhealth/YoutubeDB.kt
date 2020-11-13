package com.example.mentalhealth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VideoYT::class], version = 1)
abstract class YoutubeDB: RoomDatabase() {

    abstract fun youtubeDAO():YoutubeDAO

    companion object{ //WeatherDB.INSTANT
        private var INSTANT:YoutubeDB? = null

        fun getDBObject(context: Context):YoutubeDB?{
            if(INSTANT==null){
                synchronized(YoutubeDB::class.java){
                    INSTANT= Room.databaseBuilder(context, YoutubeDB::class.java, "youtubeDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}