package com.betterlyf.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.betterlyf.app.R
import com.betterlyf.app.adapter.SessionTypeAdapter
import com.betterlyf.app.adapter.SingleCounsellorAdapter
import com.betterlyf.app.adapter.SlotsAdapter
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarView
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.android.synthetic.main.fragment_schedule.view.rv_slots
import java.util.*


class ScheduleFragment : androidx.fragment.app.Fragment() {

    var view1: View? = null
    var sessionType:SessionTypeAdapter?=null
    var counselorAdapter: SingleCounsellorAdapter? = null
    var slotsAdapter:SlotsAdapter?=null
    val timeSlots = arrayOf("12:00 PM", "2:30 PM","7:00 PM")
    val listType = arrayOf("Chat", "Call", "Video","Face to Face")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_schedule, container, false)

        counselorAdapter = SingleCounsellorAdapter()

        view1!!.rv_counsellor.setHasFixedSize(false)
        view1!!.rv_counsellor.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        view1!!.rv_counsellor.adapter = counselorAdapter

        sessionType = SessionTypeAdapter(listType)

        slotsAdapter= SlotsAdapter(timeSlots)

        view1!!.rv_session_types.setHasFixedSize(false)
        view1!!.rv_session_types.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        view1!!.rv_session_types.adapter = sessionType

        view1!!.rv_slots.setHasFixedSize(false)
        view1!!.rv_slots.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        view1!!.rv_slots.adapter = slotsAdapter

        val startDate: Calendar = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        val endDate: Calendar = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)


        val horizontalCalendar: HorizontalCalendar =
            HorizontalCalendar.Builder(view1, R.id.calendarView)
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

        return view1

    }
}