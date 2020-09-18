package com.betterlyf.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.betterlyf.app.R
import com.betterlyf.app.adapter.SelfHelpAdapter
import com.betterlyf.app.helper.ClickListener
import com.betterlyf.app.helper.ItemDecoration
import com.betterlyf.app.ui.activity.AllCounselorActivity
import com.betterlyf.app.ui.activity.BuyBookActivity
import com.betterlyf.app.ui.activity.WellnessTopicsActivity
import kotlinx.android.synthetic.main.fragment_self_help.view.*

class SelfHelpFragment : androidx.fragment.app.Fragment(), ClickListener {

    var selfHelpdapter: SelfHelpAdapter? = null
    var list: IntArray = intArrayOf(
        R.drawable.wellness,
        R.drawable.books_new,
        R.drawable.true_stories,
        R.drawable.articles,
        R.drawable.videos
    )

    val listType = arrayOf(
        "Wellness Program",
        "Health and Wellness Books",
        "True Stories",
        "Videos",
        "Articles"
    )


    var view1: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_self_help, container, false)

        selfHelpdapter = SelfHelpAdapter(list, listType)

        selfHelpdapter!!.setClickListener(this)
        view1!!.rv_self_help.setHasFixedSize(false)
        view1!!.rv_self_help.layoutManager =
            androidx.recyclerview.widget.GridLayoutManager(activity, 2)
        val itemDecoration = ItemDecoration(2, 10, true)
        view1!!.rv_self_help.addItemDecoration(itemDecoration)
        view1!!.rv_self_help.adapter = selfHelpdapter

        return view1
    }

    override fun itemClicked(view: View, position: Int) {
        when (position) {
            4 -> {
                val intent = Intent(activity, AllCounselorActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("screen", "articles")
                startActivity(intent)
            }
            3 -> {
                val intent = Intent(activity, AllCounselorActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("screen", "videos")
                startActivity(intent)
            }
            2 -> {
                val intent = Intent(activity, AllCounselorActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("screen", "story")
                startActivity(intent)
            }
            1 -> {
                val intent = Intent(activity, BuyBookActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            0 -> {
                val intent = Intent(activity, WellnessTopicsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }
}