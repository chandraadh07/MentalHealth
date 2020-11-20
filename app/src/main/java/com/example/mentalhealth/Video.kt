package com.example.mentalhealth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videoTable")
class Video {
    @PrimaryKey
    var videoID = ""

    var title = ""
    var tags = emptyArray<String>()
    var keywords = emptyArray<String>()
    var style = ""
    var provisions = emptyArray<String>()
    var duration = 0
    var moods = emptyArray<String>()
    var isRec = false
    var isWatched = false
    var isLiked = false
    var isDisliked = false
}