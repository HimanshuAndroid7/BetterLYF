package com.betterlyf.app.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.betterlyf.app.R

class CustomProgressBar{
    var mCShowProgress: CustomProgressBar? = null
    var mDialog: Dialog? = null

    fun getInstance(): CustomProgressBar? {
        if (mCShowProgress == null) {
            mCShowProgress = CustomProgressBar()
        }
        return mCShowProgress
    }

    fun showProgress(mContext: Context?) {
        mDialog = Dialog(mContext!!)
        mDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog!!.setContentView(R.layout.custom_progressbar)
        mDialog!!.findViewById<View>(R.id.progress_bar).visibility = View.VISIBLE
        mDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog!!.setCancelable(false)
        mDialog!!.setCanceledOnTouchOutside(false)
        mDialog!!.show()
    }

    fun hideProgress() {
        if (mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }
}