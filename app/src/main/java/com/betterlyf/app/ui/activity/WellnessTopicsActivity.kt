package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.TopicsAdapter
import com.betterlyf.app.helper.ClickListener
import kotlinx.android.synthetic.main.activity_wellness_topics.*

class WellnessTopicsActivity : BaseActivity(), ClickListener {

    private var adapter: TopicsAdapter? = null

    override fun itemClicked(view: View, position: Int) {
        launchIntent(this, TopicDescriptionActivity::class.java, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wellness_topics)

        adapter = TopicsAdapter()
        adapter!!.setClickListener(this)
        rv_topics.setHasFixedSize(true)
        rv_topics.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        rv_topics.adapter = adapter

        clickListeners()

    }

    fun clickListeners(){
        img_back.setOnClickListener {
            onBackPressed()
        }
    }


}
