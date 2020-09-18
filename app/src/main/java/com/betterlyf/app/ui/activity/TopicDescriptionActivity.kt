package com.betterlyf.app.ui.activity

import android.os.Bundle
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.activity_topic_description.*

class TopicDescriptionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_description)

       clickListeners()
    }

    fun clickListeners(){
        img_back.setOnClickListener{
            onBackPressed()
        }
        txt_begin.setOnClickListener {
            launchIntent(this,HomeActivity::class.java,false)
        }

    }
}
