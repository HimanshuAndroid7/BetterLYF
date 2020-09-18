package com.betterlyf.app.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.betterlyf.app.R
import com.betterlyf.app.adapter.SessionAdapter
import com.betterlyf.app.helper.ClickListener
import com.betterlyf.app.ui.activity.ScheduleActivity
import kotlinx.android.synthetic.main.fragment_counselor.view.*

class SessionsFragment : androidx.fragment.app.Fragment(), ClickListener {

    var sessionAdapter: SessionAdapter? = null

    var view1: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_counselor, container, false)


        sessionAdapter = SessionAdapter("1")

        sessionAdapter!!.setClickListener(this)
        view1!!.rv_counsellors.setHasFixedSize(false)
        view1!!.rv_counsellors.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        view1!!.rv_counsellors.adapter = sessionAdapter

        return view1
    }

    fun showDialog(
        activity: Activity?,
        msg: String?,
        position: Int
    ) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.view_dialog)
        val text = dialog.findViewById<TextView>(R.id.txt_msg)
        text.text = msg
        val dialogButton = dialog.findViewById<TextView>(R.id.txt_yes)
        dialogButton.setOnClickListener {
            dialog.dismiss()
        }
        val dialogNo = dialog.findViewById<TextView>(R.id.txt_no)
        dialogNo.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    override fun itemClicked(view: View, position: Int) {
        if (view.id == R.id.txt_book) {
            val intent = Intent(activity, ScheduleActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        } else if (view.id == R.id.txt_cancel) {

            showDialog(activity, "Are you sure you want to cancel this session?", 1)
        }
    }
}