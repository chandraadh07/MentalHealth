package com.example.mentalhealth


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VideoRec::class], version = 1)
abstract class RecDB: RoomDatabase() {

    abstract fun recDAO():RecDAO

    companion object{ //WeatherDB.INSTANT
        private var INSTANT:RecDB? = null

        fun getDBObject(context: Context):RecDB?{
            if(INSTANT==null){
                synchronized(RecDB::class.java){
                    INSTANT= Room.databaseBuilder(context, RecDB::class.java, "recDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}