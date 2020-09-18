package com.betterlyf.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.betterlyf.app.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_video_play.*


class VideoPlayActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val RECOVERY_DIALOG_REQUEST = 1

    var DEVELOPER_KEY:String= "AIzaSyCtmR6lF5e54AMuuiHuhpVy9n33ETOKyv0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_video_play)

        youTubeView.initialize(DEVELOPER_KEY, this)

        /* Uri videoUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.abc);

        videoView.setVideoURI(videoUri);*/

    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1!!.loadVideo("-mLpfGnAPCw")
        p1!!.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider()!!.initialize(DEVELOPER_KEY, this)
        }
    }

    private fun getYouTubePlayerProvider(): YouTubePlayer.Provider? {
        return youTubeView
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        if (p1!!.isUserRecoverableError) {
            p1.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
        } else {
            val errorMessage = getString(R.string.error_player)

            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
