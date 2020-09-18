package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.adapter.PlansNewAdapter
import com.betterlyf.app.helper.ClickListener
import kotlinx.android.synthetic.main.activity_book_session.*

class BookSessionActivity : BaseActivity(), ClickListener {

    var adapter: PlansNewAdapter? = null
    var languages = arrayOf("Call", "Chat", "Video", "Email")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_session)

        clickListener()

        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_plan.adapter = aa


        adapter = PlansNewAdapter()
        adapter!!.setClickListener(this)
        rv_plans.setHasFixedSize(true)
        rv_plans.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
        rv_plans.adapter = adapter

        /* val displayMetrics = DisplayMetrics()
         windowManager.defaultDisplay.getMetrics(displayMetrics)

         adapter = PlansAdapter(this)
         val x: Int = displayMetrics.widthPixels / 5
         rv_plans.setPadding(x, 0, x, 0)
         rv_plans.clipToPadding = false
         rv_plans.pageMargin = x / 2

         rv_plans.setPageTransformer(false
         ) { page, _ ->
             val pageWidth: Int = rv_plans.measuredWidth -
                     rv_plans.paddingLeft - rv_plans.paddingRight
             val pageHeight: Int = rv_plans.height
             val paddingLeft: Int = rv_plans.paddingLeft
             val transformPos = (page.left -
                     (rv_plans.scrollX + paddingLeft)) / pageWidth
             val max = pageHeight / 10
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                 page.foreground = null
             }
             when {
                 transformPos < -1 -> {
                     // [-Infinity,-1)
                     // This page is way off-screen to the left.
                     //page.setAlpha(0.5f);// to make left transparent
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                         page.foreground = resources.getDrawable(R.drawable.blur_image)
                     }
                     page.scaleX = 0.7f
                     page.scaleY = 0.7f
                 }
                 transformPos <= 1 -> {
                     // [-1,1]
                     page.scaleX = 1f
                     page.scaleY = 1f
                 }
                 else -> {
                     // (1,+Infinity]
                     // This page is way off-screen to the right.
                     //'page.setAlpha(0.5f);// to make right transparent
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                         page.foreground = resources.getDrawable(R.drawable.blur_image)
                     }
                     page.scaleX = 0.7f
                     page.scaleY = 0.7f
                 }
             }
         }*/

    }

    fun clickListener() {
        img_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun itemClicked(view: View, position: Int) {
        if (view.id == R.id.txt_begin) {
            launchIntent(this, CheckoutActivity::class.java, false)
        }
    }
}
