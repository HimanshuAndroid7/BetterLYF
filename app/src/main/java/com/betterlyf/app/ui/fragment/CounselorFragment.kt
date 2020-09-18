package com.betterlyf.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.betterlyf.app.R
import com.betterlyf.app.adapter.CounsellorAdapter
import com.betterlyf.app.adapter.ReviewsAdapter
import com.betterlyf.app.helper.ClickListener
import com.betterlyf.app.ui.activity.CounselorActivity
import kotlinx.android.synthetic.main.fragment_counselor.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class CounselorFragment : androidx.fragment.app.Fragment(),ClickListener {

    var counselorAdapter: CounsellorAdapter? = null

    var view1: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_counselor, container, false)

        counselorAdapter = CounsellorAdapter()

        counselorAdapter!!.setClickListener(this)
        view1!!.rv_counsellors.setHasFixedSize(false)
        view1!!.rv_counsellors.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                activity,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        view1!!.rv_counsellors.adapter = counselorAdapter

        return view1
    }

    override fun itemClicked(view: View, position: Int) {
        val intent = Intent(activity, CounselorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}