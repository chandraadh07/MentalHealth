package com.example.mentalhealth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class RecyclerViewAdapter(var videoeArray: Array<Video>) :
            RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
                val viewItem =
                    LayoutInflater.from(parent.context).inflate(R.layout.videos_view, parent, false)
                return RecyclerViewHolder(viewItem, clickLambda)
            }

            override fun getItemCount(): Int {
                return videoeArray.size
            }

            override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
                holder.bind(videoeArray[position])
            }

            lateinit var clickLambda: (Video) -> Unit

            class RecyclerViewHolder(val viewItem: View, val clickLambda: (Video) -> Unit) :
                RecyclerView.ViewHolder(viewItem) {

                fun bind(video: Video) {

                    viewItem.findViewById<TextView>(R.id.texttittle).text = video.title
                    viewItem.findViewById<TextView>(R.id.textduration).text = video.duration.toString()

                    //viewItem.findViewById<YouTubePlayerView>(R.id.youtube_player_view_item).

                    viewItem.setOnClickListener{
                        clickLambda(Video())
                    }


        }
    }
}