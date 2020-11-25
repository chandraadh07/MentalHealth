package com.example.mentalhealth

import androidx.room.ColumnInfo

data class NameTuple(
    @ColumnInfo(name = "videoID") val videoID: String?,
    @ColumnInfo(name = "keywords") val keywords: String?
)