package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import android.view.View
import android.view.WindowManager
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.TutorialAdapter
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : BaseActivity() {

    var list: IntArray =
        intArrayOf(R.drawable.img_a, R.drawable.img_b, R.drawable.img_c, R.drawable.img_d)

    val listType = arrayOf(
        "Sign In if you have an account\n Else, Sign Up\n Or\n You can start as a Guest\n" +
                "With A Valid Email ID",
        "Choose a Counselling Plan\n suitable to your needs.",
        "Schedule Appointments by chatting with our representative \n Or Drop a mail on help@betterlyf.com",
        "To Start the session\n provide your 10 digit id\n over the chat on website\n &\n" +
                "Our Counsellor will\n call you shortly."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_tutorial)
        init()
        clickListener()
    }

    fun init() {
        viewPager.adapter = TutorialAdapter(list, listType, this@TutorialActivity)
        viewPager.offscreenPageLimit = 3
        indicator.setViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 3) {
                    txt_done.visibility = View.VISIBLE
                } else {
                    txt_done.visibility = View.GONE
                }
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun clickListener() {
        txt_done.setOnClickListener {
            launchIntent(this, LoginActivity::class.java, true)
        }
    }
}
