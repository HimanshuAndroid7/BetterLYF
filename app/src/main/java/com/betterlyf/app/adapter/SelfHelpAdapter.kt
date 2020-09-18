package com.betterlyf.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.betterlyf.app.R
import com.betterlyf.app.helper.ClickListener
import kotlinx.android.synthetic.main.view_self_help.view.*

class SelfHelpAdapter(var mList:IntArray,var mListText:Array<String>): androidx.recyclerview.widget.RecyclerView.Adapter<SelfHelpAdapter.ViewHolder>() {

    private var clickListener: ClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.view_self_help, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setClickListener(clicklistener: ClickListener) {
        this.clickListener = clicklistener
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.itemView.img_self.setImageResource(mList[p1])
        p0.itemView.txt_self.text=mListText[p1]
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.layout_selfHelp.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (clickListener != null) {
                clickListener!!.itemClicked(v, adapterPosition)
            }
        }
    }
}