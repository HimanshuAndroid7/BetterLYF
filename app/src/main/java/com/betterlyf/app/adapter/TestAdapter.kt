package com.betterlyf.app.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.betterlyf.app.R
import com.betterlyf.app.helper.ClickListener
import kotlinx.android.synthetic.main.view_test.view.*
import java.util.*

class TestAdapter(var mList: Array<String>) : androidx.recyclerview.widget.RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private var clickListener: ClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.view_test, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 4
    }

    fun setClickListener(clicklistener: ClickListener) {
        this.clickListener = clicklistener
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.itemView.img_test.setBackgroundColor(Color.parseColor(mList[p1]))

    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.img_test.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (clickListener != null) {
                clickListener!!.itemClicked(v, adapterPosition)
            }
        }
    }

    fun random(): Int {
        val r = Random()
        val i1: Int = r.nextInt(4 - 1) + 1
        return i1
    }
}