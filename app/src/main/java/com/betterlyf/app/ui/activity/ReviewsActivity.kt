package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.AllReviewsAdapter
import kotlinx.android.synthetic.main.activity_reviews.*


class ReviewsActivity : BaseActivity() {

    private var adapter: AllReviewsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        adapter = AllReviewsAdapter()
        rv_reviews_all.setHasFixedSize(true)
        rv_reviews_all.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        rv_reviews_all.adapter = adapter

        clickListeners()

    }

    fun clickListeners(){
        img_back.setOnClickListener {
            onBackPressed()
        }
    }
}
