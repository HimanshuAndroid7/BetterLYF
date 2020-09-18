package com.betterlyf.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.betterlyf.app.R
import com.betterlyf.app.helper.ClickListener

class PaymentAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {

    private var clickListener: ClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.view_payment, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 4
    }

    fun setClickListener(clicklistener: ClickListener) {
        this.clickListener = clicklistener
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (clickListener != null) {
                clickListener!!.itemClicked(v, adapterPosition)
            }
        }
    }
}