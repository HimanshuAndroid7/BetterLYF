package com.betterlyf.app.ui.activity

import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.ArrayMap
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.bean.UserBean
import com.betterlyf.app.helper.CustomProgressBar
import com.betterlyf.app.helper.Utils
import com.betterlyf.app.helper.service.APIService
import com.betterlyf.app.helper.service.RetrofitFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.txt_signup
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupActivity : BaseActivity() {

    var progressBar: CustomProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setText()
        clickListeners()

        progressBar = CustomProgressBar().getInstance()
        val signupText = SpannableString(resources.getString(R.string.signin))
        signupText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
            25,
            32,
            0
        )
        txt_signIn.setText(signupText, TextView.BufferType.SPANNABLE)


    }

    fun clickListeners() {
        txt_signIn.setOnClickListener {
            launchIntent(this, LoginActivity::class.java, false)
        }

        txt_signup.setOnClickListener {
            if (edt_id.text.isEmpty()||edt_id.text.length!=10) {
                edt_id.error = "Required/Invalid"
                return@setOnClickListener
            }
            if (edt_pin.text.isEmpty()||edt_pin.text.length!=4) {
                edt_pin.error = "Required/Invalid"
                return@setOnClickListener
            }
            if (Utils.isValidEmail(edt_email.text)) {
                edt_email.error = "Required/Invalid"
                return@setOnClickListener
            }
            if (edt_mobile.text.isEmpty()||edt_mobile.text.length!=10) {
                edt_mobile.error = "Required/Invalid"
                return@setOnClickListener
            }
            signUp()
        }

    }

    private fun setText() {
        val spanString = SpannableString(
            "By signing up,you agree to our Terms of Services and Privacy Policy"
        )

        val termsAndCondition = object : ClickableSpan() {
            override fun onClick(textView: View) {

/*
                val mIntent = Intent(this@SignupActivity, PrivacyPolicyActivity::class.java)
                mIntent.putExtra("policy", "terms")
                startActivity(mIntent)*/

            }
        }

        val privacy = object : ClickableSpan() {
            override fun onClick(textView: View) {

                /* val mIntent = Intent(this@SignupActivity, PrivacyPolicyActivity::class.java)
                 mIntent.putExtra("policy", "privacy")
                 startActivity(mIntent)*/

            }
        }

        spanString.setSpan(termsAndCondition, 31, 48, 0)
        spanString.setSpan(privacy, 53, 67, 0)
        spanString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.privacy_color)),
            31,
            48,
            0
        )
        spanString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.privacy_color)),
            53,
            67,
            0
        )

        txt_privacy.movementMethod = LinkMovementMethod.getInstance()
        txt_privacy.setText(spanString, TextView.BufferType.SPANNABLE)
        txt_privacy.isSelected = true
    }


    fun signUp() {
        progressBar!!.showProgress(this)
        val call: APIService = RetrofitFactory().create()!!
        val token = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
        jsonParams["email"] = edt_email.text.toString()
        jsonParams["pin"] = edt_pin.text.toString().toInt()
        jsonParams["userid"] = edt_id.text.toString().toLong()
        jsonParams["device"] = "android"
        jsonParams["mobile"] = edt_mobile.text.toString()
        jsonParams["device_token"] = token

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            JSONObject(jsonParams).toString()
        )

        call.api("auth/signup", body).enqueue(object : Callback<UserBean> {

            override fun onResponse(calls: Call<UserBean>, response: Response<UserBean>) {

                progressBar!!.hideProgress()

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@SignupActivity,
                        response.body().message!!,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    call.error(this@SignupActivity, response)
                }
            }

            override fun onFailure(call: Call<UserBean>, t: Throwable) {
                progressBar!!.hideProgress()
                Toast.makeText(
                    this@SignupActivity,
                    R.string.network_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
