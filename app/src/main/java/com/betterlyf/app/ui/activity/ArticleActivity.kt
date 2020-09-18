package com.betterlyf.app.ui.activity

import android.os.Bundle
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        clickListeners()
    }

    private fun clickListeners(){
        img_back.setOnClickListener {
            onBackPressed()
        }

        txt_start.setOnClickListener {
            launchIntent(this,BookSessionActivity::class.java,false)
        }

    }


}
