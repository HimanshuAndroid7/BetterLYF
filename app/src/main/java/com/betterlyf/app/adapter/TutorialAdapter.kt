package com.betterlyf.app.adapter

import android.content.Context
import android.os.Parcelable
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.view_tutorial.view.*

class TutorialAdapter(var mList:IntArray,var mListText:Array<String>,var contexts:Context): androidx.viewpager.widget.PagerAdapter() {

    private var inflater: LayoutInflater? = null


    override fun getCount(): Int {
        return 4
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        inflater = LayoutInflater.from(contexts)
        val imageLayout: View =
            inflater!!.inflate(R.layout.view_tutorial, view, false)!!
        view.addView(imageLayout, 0)
        view.img_set.setImageResource(mList[position])
        view.txt_plan.text=mListText[position]
        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

}
