package com.betterlyf.app.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        YoYo.with(Techniques.FadeIn).duration(2000).playOn(img_logo)

        Handler().postDelayed({
           launchIntent(this, TutorialActivity::class.java,true)
        }, 3000)
    }
}
