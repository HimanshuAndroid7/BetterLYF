package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.activity_subscription.*

class SubscriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)
        clickListener()

    }

    private fun clickListener(){
        img_back.setOnClickListener {
            onBackPressed()
        }
    }
}
