package com.example.mentalhealth
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VideoWatched::class], version = 1)
abstract class WatchedDB: RoomDatabase() {

    abstract fun watchedDAO():WatchedDAO

    companion object{ //WeatherDB.INSTANT
        private var INSTANT:WatchedDB? = null

        fun getDBObject(context: Context):WatchedDB?{
            if(INSTANT==null){
                synchronized(WatchedDB::class.java){
                    INSTANT= Room.databaseBuilder(context, WatchedDB::class.java, "watchedDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}