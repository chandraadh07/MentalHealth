package com.example.mentalhealth

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.math.BigDecimal


class RecRecyclerView(
    var videoArray: Array<Video>,
    home: HomeFragment,
    var clickListener: (Video) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val home = home
    lateinit var youTubePlayerView:YouTubePlayerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.videos_view, parent, false)
        youTubePlayerView  = viewItem.findViewById(R.id.youtube_player_view_item)
        home.addLifeCycleCallBack(youTubePlayerView)
        return ViewHolder(viewItem, youTubePlayerView)
    }

    override fun getItemCount(): Int {
        return videoArray.size
    }

    class ViewHolder(val viewItem: View, youTubePlayerView: YouTubePlayerView) : RecyclerView.ViewHolder(
        viewItem


    ){
        val youTubePlayerView = youTubePlayerView

        fun bind(video: Video, clickListener: (Video) -> Unit) {
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = video.videoID.replace(":", "")
                    Log.e("Video", videoId)
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
            viewItem.findViewById<TextView>(R.id.texttittle).text = video.title
            viewItem.findViewById<TextView>(R.id.textduration).text = splitToComponentTimes(video.duration.toBigDecimal())
            //viewItem.layoutParams.height = viewItem.layoutParams.height* 9 /16;
//            viewItem.findViewById<YouTubePlayerView>(R.id.youtube_player_view_item).// = video.videoID
            viewItem.setOnClickListener{
                Log.d("test", "before")
                clickListener(video)
                Log.d("test", "after")
            }

        }

        fun splitToComponentTimes(biggy: BigDecimal): String {
            var time = ""
            val longVal: Long = biggy.longValueExact()
            val hours = longVal.toInt() / 3600
            var remainder = longVal.toInt() - hours * 3600
            val mins = remainder / 60
            if (hours!=0){
                time = "$hours hours and $mins minutes"
            }
            else{
                time = "$mins minutes"
            }
            return time
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(videoArray[position], clickListener)
    }
}