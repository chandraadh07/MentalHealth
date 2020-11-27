package com.example.mentalhealth

import android.graphics.drawable.Drawable
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


class HistoryRecyclerView(


    var watchedArray: Array<Video>,
    home: HistoryFragment,
    var clickListener: (Video) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val home = home
    lateinit var youTubePlayerView:YouTubePlayerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.history_videos_view, parent, false)

        return ViewHolder(viewItem,home) //, youTubePlayerView)
    }


    override fun getItemCount(): Int {
        return watchedArray.size
    }

    class ViewHolder(val viewItem: View, home: HistoryFragment) : RecyclerView.ViewHolder(
        viewItem
    ){
        val home = home
        val apimanager = ThumbnailLoader()
        //val youTubePlayerView = youTubePlayerView

        fun bind(video: Video, clickListener: (Video) -> Unit) {
            val videoId = video.videoID.replace(":", "")

            apimanager.fetchGetThumbnail(videoId,imageView = viewItem.findViewById(R.id.youtube_thumbnailHistory))
            viewItem.findViewById<TextView>(R.id.texttittleHistory).text = video.title
            viewItem.findViewById<TextView>(R.id.textLikedHistory).text = isLikedOrDisliked(video)

            viewItem.setOnClickListener{
                clickListener(video)
            }
        }
        private fun isLikedOrDisliked(video: Video) : String {
            return if (home.isLiked(video.videoID))   {
                "You liked this video!"
            } else if (home.isDisliked(video.videoID)){
                "You disliked this video!"
            } else
                ""
        }

//        fun splitToComponentTimes(biggy: BigDecimal): String {
//            var time = ""
//            val longVal: Long = biggy.longValueExact()
//            val hours = longVal.toInt() / 3600
//            var remainder = longVal.toInt() - hours * 3600
//            val mins = remainder / 60
//            if (hours!=0){
//                time = "$hours hours and $mins minutes"
//            }
//            else{
//                time = "$mins minutes"
//            }
//            return time
//        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(watchedArray[position], clickListener)
    }
}