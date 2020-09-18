package com.betterlyf.app

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    private val SELECTED_ITEM_POSITION = "ItemPosition"
    private var mPosition = 0

    fun launchIntent(activity: Activity, cls: Class<out Activity>, finish: Boolean) {
        val intent = Intent(activity, cls)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
       // overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        if (finish)
            finish()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(SELECTED_ITEM_POSITION, mPosition)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        mPosition = savedInstanceState!!.getInt(SELECTED_ITEM_POSITION)

    }

    fun launchIntent(activity: Activity, cls: Class<out Activity>, bundle: Bundle, finish: Boolean) {
        val intent = Intent(activity, cls)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtras(bundle)
        startActivity(intent)
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        if (finish)
            finish()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)

    }
}