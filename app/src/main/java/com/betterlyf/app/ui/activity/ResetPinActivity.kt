package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.activity_reset_pin.*

class ResetPinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pin)

        var bundle: Bundle = intent.extras

        if (bundle.getString("screen") == "recovery") {
            mobile.visibility = View.VISIBLE
            view_mobile.visibility = View.VISIBLE
        }
        clickListener()

    }

    fun clickListener() {
        txt_cancel.setOnClickListener {
            onBackPressed()
        }

    }
}
