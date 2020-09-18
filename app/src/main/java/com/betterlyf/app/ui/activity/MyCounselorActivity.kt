package com.betterlyf.app.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.activity_my_counselor.*

class MyCounselorActivity : BaseActivity() {

    var languages = arrayOf("Select Counselor", "Nandita", "Shreya", "Anam", "Liza")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_counselor)

        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_counselor.adapter=aa

        clickListeners()
    }

    fun clickListeners() {
        img_back.setOnClickListener {
            onBackPressed()
        }
        txt_know.setOnClickListener {
            launchIntent(this, CounselorActivity::class.java, false)
        }
    }
}
