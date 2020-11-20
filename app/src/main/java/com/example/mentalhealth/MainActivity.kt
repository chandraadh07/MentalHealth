package com.example.mentalhealth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {
    val viewModel:AppViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val youTubePlayerView : YouTubePlayerView = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)

        viewModel.database.value = YoutubeDB.getDBObject(this)

        //checking if the database is empty
        val list = viewModel.database.value?.youtubeDAO()?.getAll()

        if (list.isNullOrEmpty()){
            loadData()
        }
    }

    //creates a YT vid for everything in the csv
    fun makeVideo(line: String): Video {
        val cells = line.split(",")
        val vid = Video()
        //passing restaurant values from data csv file
        vid.videoID = cells[0]
        vid.title = cells[1]
        vid.tags = cells[3].split(" ").toTypedArray()
        vid.style = cells[4]
        vid.provisions = cells[5].split(" ").toTypedArray()
        vid.duration = cells[6].toInt()
        vid.isLiked = false
        vid.isDisliked = false
        vid.moods = cells[7].split(" ").toTypedArray()

        return vid
    }

    fun loadData() {
        //DATA STRING REPRESENt the entire data of csv file
        val dataString =
            resources.openRawResource(R.raw.video_data).bufferedReader()
                .use { it.readText() }// read the entire file as a string

        var lines = dataString.trim().split("\n") // split each line
        lines = lines.subList(1, lines.size) // get rid of the header line
        //Add to the stock Array.
        lines.forEach {
            viewModel.database.value?.youtubeDAO()?.insert(makeVideo(it))
        }
    }
}