package com.betterlyf.app.ui.activity

import android.os.Bundle
import android.view.View
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.activity_forgot_pin.*

class ForgotPinActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pin)
        clickListeners()
    }



    fun clickListeners(){
        txt_recover.setOnClickListener {
            layout_reset.visibility=View.INVISIBLE
            layout_recover.visibility=View.VISIBLE
        }
        txt_cancel.setOnClickListener {
            layout_reset.visibility=View.VISIBLE
            layout_recover.visibility=View.INVISIBLE
        }
        txt_next.setOnClickListener {

            var bundle=Bundle()
            bundle.putString("screen","reset")
            launchIntent(this,ResetPinActivity::class.java,bundle,false)
        }
        txt_nexts.setOnClickListener {
            var bundle=Bundle()
            bundle.putString("screen","recovery")
            launchIntent(this,ResetPinActivity::class.java,bundle,false)
        }
    }
}
