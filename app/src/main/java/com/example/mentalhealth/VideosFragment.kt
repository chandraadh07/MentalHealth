package com.example.mentalhealth

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_videos.*



@Suppress("DEPRECATED_IDENTITY_EQUALS")
class VideosFragment : Fragment() {

    val viewModel: AppViewModel by activityViewModels()
    lateinit var youTubePlayerView : YouTubePlayerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = viewModel.currentVideo.value?.videoID!!

        val provWhys = mapOf<String, String>(
            "attachment" to "make you feel at home",
            "integration" to "add some fun to your day",
            "alliance" to "help you de-stress",
            "guidance" to "teach you something new",
            "nurturance" to "remind you that you're not alone",
            "reassurance" to "help you feel at ease"
        )

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

        youTubePlayerView = youtube_player_view
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = video?.videoID?.replace(":", "")
                Log.e("Video", videoId!!)
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        youTubePlayerView.addFullScreenListener(object: YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                video_view_container.foreground = null
                video_view_container.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
                youTubePlayerView.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT
                text_video.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
                text_video.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
//                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onYouTubePlayerExitFullScreen() {
                video_view_container.foreground = resources.getDrawable(R.drawable.bg_rounde2) //findViewById(R.id.youtube_thumbnail))
                video_view_container.layoutParams.height =
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 235f, resources.displayMetrics).toInt()
                youTubePlayerView.layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                text_video.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33f)
//                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === ORIENTATION_LANDSCAPE) {
            youTubePlayerView.enterFullScreen()
        } else if (newConfig.orientation === ORIENTATION_PORTRAIT) {
            youTubePlayerView.exitFullScreen()
        }
    }
}