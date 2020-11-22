package com.example.mentalhealth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class RecRecyclerView(var videoArray: Array<Video>, var clickListener: (Video) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.videos_view, parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return videoArray.size
    }

    lateinit var clickLambda: (Video) -> Unit



    class ViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {

//                fun getScreenWidth(c: Context): Int {
//                    var screenWidth = 0 // this is part of the class not the method
//                    if (screenWidth == 0) {
//                        val wm = c.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//                        val display = wm.defaultDisplay
//                        val size = Point()
//                        display.getRealSize(size)
//                        screenWidth = size.x
//                    }
//                    return screenWidth
//                }
        fun bind(video: Video, clickListener: (Video) -> Unit) {
            val youTubePlayerView : YouTubePlayerView = viewItem.findViewById(R.id.youtube_player_view_item)
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = video.videoID
//                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
            viewItem.findViewById<TextView>(R.id.texttittle).text = video.title
            viewItem.findViewById<TextView>(R.id.textduration).text = video.duration.toString()
            viewItem.layoutParams.height = viewItem.layoutParams.height* 9 /16;
//            viewItem.findViewById<YouTubePlayerView>(R.id.youtube_player_view_item).// = video.videoID
            viewItem.setOnClickListener{
                clickListener(video)
            }


}
}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(videoArray[position], clickListener)
    }
}