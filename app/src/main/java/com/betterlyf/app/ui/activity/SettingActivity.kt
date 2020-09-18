package com.betterlyf.app.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.bean.UserBean
import com.betterlyf.app.helper.CustomProgressBar
import com.betterlyf.app.helper.Utils
import com.betterlyf.app.helper.service.APIService
import com.betterlyf.app.helper.service.RetrofitFactory
import kotlinx.android.synthetic.main.activity_profile.img_back
import kotlinx.android.synthetic.main.activity_setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity : BaseActivity() {

    var progressBar: CustomProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        progressBar = CustomProgressBar().getInstance()
        clickListeners()
    }

    private fun logOut() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.view_logout)

        val dialogButton = dialog.findViewById<TextView>(R.id.txt_yes)
        dialogButton.setOnClickListener { v: View? ->
            logoutAPI()
            dialog.dismiss()
        }

        val dialogNo = dialog.findViewById<TextView>(R.id.txt_no)
        dialogNo.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

    private fun clickListeners() {
        img_back.setOnClickListener {
            onBackPressed()
        }

        txt_subscription.setOnClickListener {
            launchIntent(this, SubscriptionActivity::class.java, false)
        }

        txt_change_password.setOnClickListener {
            launchIntent(this, ChangePinActivity::class.java, false)
        }

        txt_policy.setOnClickListener {
            launchIntent(this, PolicyActivity::class.java, false)
        }

        txt_logout.setOnClickListener {
            logOut()
        }
    }

    fun logoutAPI() {
        progressBar!!.showProgress(this)

        val token = Utils.getPref(this, Utils.token, "")

        val call: APIService = RetrofitFactory().getRetrofitInstance(token!!)!!
        call.apiGet("auth/logout").enqueue(object : Callback<UserBean> {

            override fun onResponse(calls: Call<UserBean>, response: Response<UserBean>) {

                progressBar!!.hideProgress()

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@SettingActivity,
                        response.body().message!!,
                        Toast.LENGTH_SHORT
                    ).show()
                    //Utils.savePref(this@SettingActivity, Utils.token, response.body().access_token)
                    launchIntent(this@SettingActivity, LoginActivity::class.java, true)
                } else {
                    call.error(this@SettingActivity, response)
                }
            }

            override fun onFailure(call: Call<UserBean>, t: Throwable) {
                progressBar!!.hideProgress()
                Toast.makeText(
                    this@SettingActivity,
                    R.string.network_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
