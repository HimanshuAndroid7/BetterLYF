package com.betterlyf.app.ui.activity

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.FaqAdapter
import kotlinx.android.synthetic.main.activity_faq.*
import kotlinx.android.synthetic.main.activity_send_happiness.img_back
import java.util.*

class FaqActivity : BaseActivity() {

    var category:String?=null
    var faqAdapter:FaqAdapter?=null
    private val categories=arrayOf("Counselling", "About Us", "Couple Counselling", "Abroad Clients","Account","Payment",
    "Counselor","Interactive Exercises")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        clickListener()

        faqAdapter= FaqAdapter()
        rv_faqs.setHasFixedSize(false)
        rv_faqs.layoutManager= androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        rv_faqs.adapter=faqAdapter

    }

    fun clickListener(){
        img_back.setOnClickListener {
            onBackPressed()
        }
        layout_spin.setOnClickListener {
            showCategoryDialog(this,categories)
        }
    }

    fun showCategoryDialog(
        activity: Activity?,
        mList: Array<String>?
    ) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.view_faq_category)
        val listView =
            dialog.findViewById<View>(R.id.listView) as ListView
        val mAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mList
        )
        listView.adapter = mAdapter
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                txt_category.text = (view as TextView).text
                category = view.text.toString()
                dialog.dismiss()
            }
        dialog.show()
    }
}