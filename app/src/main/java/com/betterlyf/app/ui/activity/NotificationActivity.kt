package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.NotificationAdapter
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : BaseActivity() {
    private var adapter: NotificationAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        adapter = NotificationAdapter()
        rv_notification.setHasFixedSize(true)
        rv_notification.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        rv_notification.adapter = adapter

        clickListeners()
    }

    fun clickListeners(){
        img_back.setOnClickListener {
            onBackPressed()
        }
    }
}
