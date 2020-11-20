package com.example.mentalhealth
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "videoTable")
class Video {
    @PrimaryKey
    var videoID = ""
    var title = ""
    //    var tags = emptyArray<String>()
    var tags = ""
    //    var keywords = emptyArray<String>()
    var keywords = ""
    var style = ""
    //    var provisions = emptyArray<String>()
    var provisions = ""
    var duration = 0
    //    var moods = emptyArray<String>()
    var moods = ""
    var isRec = false
    var isWatched = false
    var isLiked = false
    var isDisliked = false
}