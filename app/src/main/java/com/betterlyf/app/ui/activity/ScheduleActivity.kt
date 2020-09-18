package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.SessionTypeAdapter
import com.betterlyf.app.adapter.SingleCounsellorAdapter
import com.betterlyf.app.adapter.SlotsAdapter
import com.betterlyf.app.helper.ClickListener
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.util.*

class ScheduleActivity : BaseActivity(), ClickListener {

    var sessionType: SessionTypeAdapter? = null
    var counselorAdapter: SingleCounsellorAdapter? = null
    var slotsAdapter: SlotsAdapter? = null
    val listType = arrayOf("Chat", "Call", "Video", "Face to Face")
    val timeSlots = arrayOf("12:00 PM", "2:30 PM", "7:00 PM")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)


        counselorAdapter = SingleCounsellorAdapter()
        clickListeners()

        rv_counsellor.setHasFixedSize(false)
        rv_counsellor.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        rv_counsellor.adapter = counselorAdapter

        sessionType = SessionTypeAdapter(listType)

        slotsAdapter = SlotsAdapter(timeSlots)

        rv_session_types.setHasFixedSize(false)
        rv_session_types.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        rv_session_types.adapter = sessionType

        rv_slots.setHasFixedSize(false)
        rv_slots.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        rv_slots.adapter = slotsAdapter

        val startDate: Calendar = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        val endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)


        val horizontalCalendar: HorizontalCalendar =
            HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {}
            override fun onCalendarScroll(
                calendarView: HorizontalCalendarView,
                dx: Int, dy: Int
            ) {
            }

            override fun onDateLongClicked(date: Calendar?, position: Int): Boolean {
                return true
            }
        }
    }

    override fun itemClicked(view: View, position: Int) {

    }

    fun clickListeners() {
        img_back.setOnClickListener {
            onBackPressed()
        }
    }
}
