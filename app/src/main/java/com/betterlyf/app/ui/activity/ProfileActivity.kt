package com.betterlyf.app.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.NotificationAdapter
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {

    private var adapter: NotificationAdapter? = null
    var counsellor = arrayOf("Select Counselor", "Nandita", "Shreya", "Anam", "Liza")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        adapter = NotificationAdapter()
        rv_notification.setHasFixedSize(true)
        rv_notification.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        rv_notification.adapter = adapter

        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, counsellor)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_counselor.adapter=aa

        clickListener()

    }

    private fun clickListener() {
        txt_activity.setOnClickListener {
            txt_activity.setTextColor(ContextCompat.getColor(this, R.color.signup_color))
            txt_friends.setTextColor(ContextCompat.getColor(this, R.color.light_grey))
            txt_notification.setTextColor(ContextCompat.getColor(this, R.color.light_grey))
            view1.setBackgroundColor(ContextCompat.getColor(this, R.color.signup_color))
            view2.setBackgroundColor(ContextCompat.getColor(this, R.color.light_grey))
            view3.setBackgroundColor(ContextCompat.getColor(this, R.color.light_grey))
            layout_activity.visibility = View.VISIBLE
            layout_share.visibility = View.INVISIBLE
            layout_notification.visibility = View.INVISIBLE
        }
        txt_friends.setOnClickListener {
            txt_friends.setTextColor(ContextCompat.getColor(this, R.color.signup_color))
            txt_activity.setTextColor(ContextCompat.getColor(this, R.color.light_grey))
            txt_notification.setTextColor(ContextCompat.getColor(this, R.color.light_grey))
            view1.setBackgroundColor(ContextCompat.getColor(this, R.color.light_grey))
            view2.setBackgroundColor(ContextCompat.getColor(this, R.color.signup_color))
            view3.setBackgroundColor(ContextCompat.getColor(this, R.color.light_grey))
            layout_activity.visibility = View.INVISIBLE
            layout_share.visibility = View.VISIBLE
            layout_notification.visibility = View.INVISIBLE
        }
        txt_notification.setOnClickListener {
            txt_activity.setTextColor(ContextCompat.getColor(this, R.color.light_grey))
            txt_friends.setTextColor(ContextCompat.getColor(this, R.color.light_grey))
            txt_notification.setTextColor(ContextCompat.getColor(this, R.color.signup_color))
            view1.setBackgroundColor(ContextCompat.getColor(this, R.color.light_grey))
            view2.setBackgroundColor(ContextCompat.getColor(this, R.color.light_grey))
            view3.setBackgroundColor(ContextCompat.getColor(this, R.color.signup_color))
            layout_activity.visibility = View.INVISIBLE
            layout_share.visibility = View.INVISIBLE
            layout_notification.visibility = View.VISIBLE
        }
        txt_change_counselor.setOnClickListener {
            layout_change_counselor.visibility = View.VISIBLE
            txt_change_counselor.visibility = View.GONE
        }
        txt_cancel.setOnClickListener {
            layout_change_counselor.visibility = View.GONE
            txt_change_counselor.visibility = View.VISIBLE
        }
        img_back.setOnClickListener {
            onBackPressed()
        }
        img_setting.setOnClickListener {
            launchIntent(this, SettingActivity::class.java, false)
        }
        img_edit.setOnClickListener {
            launchIntent(this, EditProfileActivity::class.java, false)
        }
        txt_book.setOnClickListener {
            launchIntent(this, BookSessionActivity::class.java, false)
        }
        txt_view_all.setOnClickListener {
            launchIntent(this, NotificationActivity::class.java, false)

        }
        txt_know.setOnClickListener {
            launchIntent(this, CounselorActivity::class.java, false)
        }
        layout_share_friends.setOnClickListener {
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

    }
}
