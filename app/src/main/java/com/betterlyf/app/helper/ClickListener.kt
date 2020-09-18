package com.betterlyf.app.helper

import android.view.View

interface ClickListener {
    fun itemClicked(view: View, position: Int)
}