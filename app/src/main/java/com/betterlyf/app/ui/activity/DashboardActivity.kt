package com.betterlyf.app.ui.activity

import android.os.Bundle
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        txt_appointments.setSolidColor("#8974e2")
        txt_sessions.setSolidColor("#f1ad42")
        txt_counsellor.setSolidColor("#e4439d")
        txt_progress.setSolidColor("#0a64f6")
        txt_payments.setSolidColor("#32CD32")
        clickListeners()
    }

    fun clickListeners() {
        txt_appointments.setOnClickListener {
            launchIntent(this, ScheduleActivity::class.java, false)
        }
        txt_sessions.setOnClickListener {
            launchIntent(this, SessionsActivity::class.java, false)
        }
        txt_progress.setOnClickListener {
            var bundle = Bundle()
            bundle!!.putString("screen", "progress")
            launchIntent(this, ProgressActivity::class.java, bundle!!, false)
        }
        txt_payments.setOnClickListener {
            var bundle = Bundle()
            bundle!!.putString("screen", "payment")
            launchIntent(this, ProgressActivity::class.java, bundle!!, false)
        }
        txt_counsellor.setOnClickListener {
            launchIntent(this, MyCounselorActivity::class.java, false)
        }
    }
}
