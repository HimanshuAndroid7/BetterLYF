package com.betterlyf.app.ui.activity

import android.content.Context
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.betterlyf.app.R
import com.betterlyf.app.ui.fragment.PastSessionsFragment
import com.betterlyf.app.ui.fragment.SessionsFragment
import kotlinx.android.synthetic.main.activity_sessions.*

class SessionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessions)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Scheduled Sessions"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Past Sessions"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        clickListeners()
    }

    fun clickListeners(){
        img_back.setOnClickListener {
            onBackPressed()
        }
    }

    class MyAdapter(private val myContext: Context, fm: androidx.fragment.app.FragmentManager, private var totalTabs: Int) : androidx.fragment.app.FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): androidx.fragment.app.Fragment? {
            return when (position) {
                0 -> {
                    SessionsFragment()
                }
                1 -> {
                    PastSessionsFragment()
                }

                else -> null
            }
        }

        override fun getCount(): Int {
            return totalTabs
        }
    }


}
