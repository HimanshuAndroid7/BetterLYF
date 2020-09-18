package com.betterlyf.app.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.core.view.GravityCompat
import android.view.View
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.ui.fragment.ArticlesFragment
import com.betterlyf.app.ui.fragment.HomeFragment
import com.betterlyf.app.ui.fragment.ScheduleFragment
import com.betterlyf.app.ui.fragment.SelfHelpFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragment(HomeFragment())

        clickListeners()

    }

    private fun clickListeners() {
        button_search.setOnClickListener {
            launchIntent(this, SearchActivity::class.java, false)
        }

        button_menu.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        txt_view.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            launchIntent(this, ProfileActivity::class.java, false)
        }
        img_close.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        layout_sessions.setOnClickListener {
            launchIntent(this, SessionsActivity::class.java, false)
        }
        layout_schedule.setOnClickListener {
            launchIntent(this, ScheduleActivity::class.java, false)
        }
        layout_progress.setOnClickListener {
            var bundle = Bundle()
            bundle!!.putString("screen", "progress")
            launchIntent(this, ProgressActivity::class.java, bundle, false)
        }
        layout_counselor.setOnClickListener {
            launchIntent(this, MyCounselorActivity::class.java, false)
        }
        layout_payment.setOnClickListener {
            var bundle = Bundle()
            bundle!!.putString("screen", "course")
            launchIntent(this, ProgressActivity::class.java, bundle, false)
        }
        logout_share.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "BetterLyf")
                val shareMessage =
                    "Lets try this BetterLyf application.".trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        }
        logout_happiness.setOnClickListener {
            launchIntent(this, SendHappinessActivity::class.java, false)
        }
        layout_faq.setOnClickListener {
            launchIntent(this, FaqActivity::class.java, false)
        }
    }

    private fun fragment(fragmentLaunch: androidx.fragment.app.Fragment) {
        val fragment: androidx.fragment.app.Fragment = fragmentLaunch
        val transaction: androidx.fragment.app.FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activity_content, fragment).commit()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_profile -> {

                    // launchIntent(this,ProfileActivity::class.java,false)

                    txt_toolbar.text = "Self Improvement"
                    button_search.visibility = View.INVISIBLE
                    fragment(SelfHelpFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_articles -> {
                    txt_toolbar.text = "Articles"
                    button_search.visibility = View.VISIBLE
                    fragment(ArticlesFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {
                    txt_toolbar.text = "Home"
                    button_search.visibility = View.INVISIBLE
                    fragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_counselor -> {
                    txt_toolbar.text = "Schedule"
                    button_search.visibility = View.INVISIBLE
                    fragment(ScheduleFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}