package com.betterlyf.app.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.ArticlesAdapter
import com.betterlyf.app.adapter.CounsellorAdapter
import com.betterlyf.app.adapter.TrueStoryAdapter
import com.betterlyf.app.adapter.VideoAdapter
import com.betterlyf.app.helper.ClickListener
import kotlinx.android.synthetic.main.activity_all_counselor.*

class AllCounselorActivity : BaseActivity(), ClickListener {

    var counselorAdapter: CounsellorAdapter? = null
    var articleAdapter: ArticlesAdapter? = null
    var videoAdapter: VideoAdapter? = null
    var trueStoryAdapter: TrueStoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_counselor)

        clickListener()

        counselorAdapter = CounsellorAdapter()
        articleAdapter = ArticlesAdapter()
        videoAdapter = VideoAdapter()
        trueStoryAdapter= TrueStoryAdapter()

        counselorAdapter!!.setClickListener(this)
        trueStoryAdapter!!.setClickListener(this)
        articleAdapter!!.setClickListener(this)
        rv_counselors.setHasFixedSize(false)


        when {
            intent.getStringExtra("screen") == "counselor" -> {
                rv_counselors.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(
                        this,
                        androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                        false
                    )
                rv_counselors.adapter = counselorAdapter
            }
            intent.getStringExtra("screen") == "articles" -> {
                txt_heading.text = "Articles"
                rv_counselors.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(
                        this,
                        androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                        false
                    )
                rv_counselors.adapter = articleAdapter
            }
            intent.getStringExtra("screen") == "story" -> {
                txt_heading.text = "True Stories"
                rv_counselors.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(
                        this,
                        androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                        false
                    )
                rv_counselors.adapter = trueStoryAdapter
            }
            else -> {
                txt_heading.text = "Videos"
                rv_counselors.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(
                        this,
                        androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                        false
                    )
                rv_counselors.adapter = videoAdapter
            }
        }
    }

    override fun itemClicked(view: View, position: Int) {
        if (view.id==R.id.txt_book){
            val intent = Intent(this, BookSessionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }else {
            when {
                intent.getStringExtra("screen") == "counselor" ->
                    launchIntent(this, CounselorActivity::class.java, false)
                intent.getStringExtra("screen") == "story" ->
                    launchIntent(this, VideoPlayActivity::class.java, false)
                intent.getStringExtra("screen") == "articles" ->
                    launchIntent(this, ArticleActivity::class.java, false)

            }
        }
    }

    fun clickListener() {
        img_back.setOnClickListener {
            onBackPressed()
        }
    }
}
