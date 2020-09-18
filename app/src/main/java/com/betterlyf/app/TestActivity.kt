package com.betterlyf.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import com.betterlyf.app.adapter.TestAdapter
import com.betterlyf.app.helper.ClickListener
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*

class TestActivity : AppCompatActivity(), ClickListener {

    var adapter: TestAdapter? = null
    var timer = Timer()
    var listType = arrayOf("#0a64f6", "#c341b2", "#f1ad42", "#e53935")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        adapter = TestAdapter(listType)
        rv_colors.layoutManager =
            androidx.recyclerview.widget.GridLayoutManager(this, 2)
        rv_colors.adapter = adapter

        startingUp()

    }

    private fun startingUp() {
        val timer: Thread = object : Thread() {
            //new thread
            override fun run() {
                val b = true
                try {
                    do {
                        sleep(1000)
                        runOnUiThread {
                            random()
                            resetAdapterState()
                        }
                    } while (b == true)
                } finally {
                }
            }
        }
        timer.start()
    }

    fun random(): Int {
        val r = Random()
        val i1: Int = r.nextInt(4 - 1) + 1
        listType[i1] = "#80827f"
        return i1
    }

    fun resetAdapterState() {
        adapter = TestAdapter(listType)
        rv_colors.layoutManager =
            androidx.recyclerview.widget.GridLayoutManager(this, 2)
        rv_colors.adapter = adapter
        listType = arrayOf("#0a64f6", "#c341b2", "#f1ad42", "#e53935")
    }


    override fun itemClicked(view: View, position: Int) {

    }
}