package com.betterlyf.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.betterlyf.app.R
import com.betterlyf.app.helper.ClickListener
import kotlinx.android.synthetic.main.view_session.view.*

class SessionAdapter(var value: String) : androidx.recyclerview.widget.RecyclerView.Adapter<SessionAdapter.ViewHolder>() {

    private var clickListener: ClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        return if (value == "1") {
            val itemView = LayoutInflater.from(p0.context).inflate(R.layout.view_session, p0, false)
            ViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(p0.context).inflate(R.layout.view_past_session, p0, false)
            ViewHolder(itemView)
        }

    }

    override fun getItemCount(): Int {
        return 4
    }

    fun setClickListener(clicklistener: ClickListener) {
        this.clickListener = clicklistener
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            if (value == "1") {
                itemView.txt_book.setOnClickListener(this)
                itemView.txt_cancel.setOnClickListener(this)
            }
        }

        override fun onClick(v: View) {
            if (clickListener != null) {
                clickListener!!.itemClicked(v, adapterPosition)
            }
        }
    }
}