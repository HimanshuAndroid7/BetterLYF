package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.betterlyf.app.R
import com.betterlyf.app.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    var adapter:CategoryAdapter?=null
    var list:IntArray= intArrayOf(R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        adapter = CategoryAdapter(list)
        rv_category.setHasFixedSize(true)
        rv_category.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        rv_category.adapter = adapter

        clickListeners()
    }

    fun clickListeners(){
        img_back.setOnClickListener {
            onBackPressed()
        }
    }
}
