package com.betterlyf.app.ui.activity

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.CourseAdapter
import com.betterlyf.app.adapter.PaymentAdapter
import com.betterlyf.app.adapter.ProgressAdapter
import com.betterlyf.app.helper.ClickListener
import com.betterlyf.app.helper.ItemDecoration
import kotlinx.android.synthetic.main.activity_progress.*
import kotlinx.android.synthetic.main.fragment_self_help.view.*

class ProgressActivity : BaseActivity(), ClickListener {

    var adapter: ProgressAdapter? = null
    var courseAdapter: CourseAdapter? = null
    var list: IntArray = intArrayOf(
        R.drawable.course_happiness, R.drawable.course_workstress, R.drawable.course_anxiety,
        R.drawable.course_depression
    )

    val listType = arrayOf("Happiness", "Work Stress", "Anxiety", "Depression")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        adapter = ProgressAdapter()
        courseAdapter = CourseAdapter(list, listType)

        clickListeners()

        adapter!!.setClickListener(this)
        rv_progress.setHasFixedSize(true)

        when {
            intent.extras.getString("screen") == "progress" -> {
                rv_progress.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(
                        this,
                        androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                        false
                    )
                txt_heading.text = "Progress"
                rv_progress.adapter = adapter
            }
            intent.extras.getString("screen") == "course" -> {
                rv_progress.layoutManager =
                    androidx.recyclerview.widget.GridLayoutManager(this, 2)
                val itemDecoration = ItemDecoration(2, 10, true)
                rv_progress.addItemDecoration(itemDecoration)
                txt_heading.text = "My Courses"
                rv_progress.adapter = courseAdapter
            }
        }
    }

    fun showDialog(activity: Activity?) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.view_reflection_points)
        dialog.show()
    }

    fun clickListeners() {
        img_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun itemClicked(view: View, position: Int) {
        if (view.id == R.id.txt_assign) {
            showDialog(this)
        }
    }
}

