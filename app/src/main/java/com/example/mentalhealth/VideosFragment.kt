package com.example.mentalhealth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_videos.*


class VideosFragment : Fragment() {

    val viewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = viewModel.currentVideo.value?.videoID!!

        val provWhys = mapOf<String,String>("attachment" to "make you feel at home","integration" to "add some fun to your day",
                            "alliance" to "help you de-stress", "guidance" to "teach you something new",
                            "nurturance" to "remind you that you're not alone", "reassurance" to "help you feel at ease")

        val provisions = viewModel.currentVideo.value?.provisions?.split(" ")!!
        var whyText = "This video is meant to "
        provisions.forEachIndexed { index, s ->
            if (index == provisions.size - 1) {
                whyText += "and " + provWhys.getValue(s) + "."
            } else {
                whyText += provWhys.getValue(s) + ", "
            }
        }

        textView3.text = whyText
        // update title text
        text_video.text = viewModel.currentVideo.value?.title
        //say the video has been watched
        viewModel.database.value?.youtubeDAO()?.watch(id)

        // makes the video liked in the database
        like_button.setOnClickListener {
            viewModel.database.value?.youtubeDAO()?.like(id)
            //Toast.makeText(activity, "you liked this video", Toast.LENGTH_SHORT).show()
            val toast = Toast(context)
            toast.duration = Toast.LENGTH_SHORT

            val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.thumbs_up_toast, null)
            toast.setView(view)
            toast.show()
        }

        //makes the video disliked in the database
        dislike_button.setOnClickListener {
            viewModel.database.value?.youtubeDAO()?.dislike(id)
//            Toast.makeText(activity, "you disliked this video", Toast.LENGTH_SHORT).show()
            viewModel.database.value?.youtubeDAO()?.like(id)
            //Toast.makeText(activity, "you liked this video", Toast.LENGTH_SHORT).show()
            val toast = Toast(context)
            toast.duration = Toast.LENGTH_SHORT

            val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.thumbs_down_toast, null)
            toast.setView(view)
            toast.show()
        }

        var video: Video? = null
        viewModel.currentVideo.observe(viewLifecycleOwner) {
            video = it
        }
        val youTubePlayerView : YouTubePlayerView = youtube_player_view
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = video?.videoID?.replace(":", "")
                Log.e("Video", videoId!!)
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }
}