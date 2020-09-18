package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.SlotsAdapter
import kotlinx.android.synthetic.main.activity_send_happiness.*


class SendHappinessActivity : BaseActivity() {

    var slotsAdapter: SlotsAdapter? = null
    val planSlots = arrayOf("₹ 850", "₹ 1700", "₹ 3500", "₹ 5250", "₹ 8750")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_happiness)

        clickListeners()
        slotsAdapter = SlotsAdapter(planSlots)

        rv_plans.setHasFixedSize(false)
        rv_plans.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
        rv_plans.adapter = slotsAdapter

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                inp_name.visibility = View.VISIBLE
                view_name.visibility = View.VISIBLE
            } else {
                inp_name.visibility = View.GONE
                view_name.visibility=View.GONE
            }
        }
    }

    fun clickListeners() {

        img_back.setOnClickListener {
            onBackPressed()
        }
        txt_select_card.setOnClickListener { }
    }

}