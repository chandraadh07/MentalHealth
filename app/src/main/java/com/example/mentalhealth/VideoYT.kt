package com.example.mentalhealth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ytTable")
class VideoYT {
    @PrimaryKey
    var videoId = "id undefined"

    var embedUrl = "e_url undefined"
    var videoUrl = "v_url undefined"
    var channelId = "chID undefined"
    var title = "title undefined"
    var description = "description undefined"
    var tags = emptyList<String>()
    var style = "style undefined"
    var provisions = emptyList<String>()
    var publishedAt = "pub date undefined"
    var viewCount = 0
    var likeCount = 0
    var dislikeCount = 0
    var duration = 0
}