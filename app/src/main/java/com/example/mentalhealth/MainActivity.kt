package com.example.mentalhealth

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val viewModel:AppViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.database.value = YoutubeDB.getDBObject(this)

        //checking if the database is empty
        val list = viewModel.database.value?.youtubeDAO()?.getAll()

        if (list.isNullOrEmpty()){
            loadData()
        }

        Log.e("TAG", "MainActivity was created")

//        list?.forEach {
//            Log.e(
//                "TAG",
//                it.videoID
//            )//arrayOf(viewModel.database.value?.youtubeDAO()?.getVideoByID("-1io3Ofg8sE")!!)//getRecommendations()
//        }
    }

    //creates a YT vid for everything in the csv
    fun makeVideo(line: String): Video {
        val cells = line.split(",")
        val vid = Video()
        //passing restaurant values from data csv file
        vid.videoID = cells[0]
        vid.title = cells[1]
        vid.keywords = cells[3]
        vid.style = cells[4]
        vid.provisions = cells[5]
        vid.duration = cells[6].toInt()
        vid.isLiked = false
        vid.isDisliked = false
        vid.moods = cells[7]

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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Questionnaire_menu -> {
                NavHostFragment.findNavController(nav_host_frag)
                    .navigate(R.id.action_global_buttonsFragment) //change this later
                true
            }
            R.id.home_menu -> {
                NavHostFragment.findNavController(nav_host_frag)
                    .navigate(R.id.action_global_homeFragment) //change this later
                true
            }
            R.id.history_menu -> {
                NavHostFragment.findNavController(nav_host_frag)
                    .navigate(R.id.action_global_historyFragment) //change this later
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }


}