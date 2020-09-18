package com.betterlyf.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.ArrayMap
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.betterlyf.app.bean.UserBean
import com.betterlyf.app.helper.CustomProgressBar
import com.betterlyf.app.helper.Utils
import com.betterlyf.app.helper.service.APIService
import com.betterlyf.app.helper.service.RetrofitFactory
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.txt_signup
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e("error", "" + p0)
    }

    val google_sign_in: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private var callbackManager: CallbackManager? = null
    private var googleApiClient: GoogleApiClient? = null
    var progressBar: CustomProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = CustomProgressBar().getInstance()
        val signupText = SpannableString(resources.getString(R.string.signup))
        signupText.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorPrimary)), 23, 35, 0)
        txt_signup.setText(signupText, TextView.BufferType.SPANNABLE)

        callbackManager = CallbackManager.Factory.create()
        login_button.setReadPermissions("email")

        googleSignIn()
        clickListeners()
    }

    private fun clickListeners() {
        txt_signup.setOnClickListener {
            launchIntent(this, SignupActivity::class.java, false)
        }
        txt_guest_user.setOnClickListener {
            launchIntent(this, HomeActivity::class.java, false)
        }

        txt_signin.setOnClickListener {
            if (edt_id_login.text.isEmpty()||edt_id_login.text.length!=10) {
                edt_id_login.error = "Required/Invalid"
                return@setOnClickListener
            }
            if (edt_pin_login.text.isEmpty()||edt_pin_login.text.length!=4) {
                edt_pin_login.error = "Required/Invalid"
                return@setOnClickListener
            }
            login()
        }

        img_fb.setOnClickListener {
            LoginManager.getInstance().logOut()
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("public_profile", "email"))
            fbSignin()
        }

        img_google.setOnClickListener {
            signIn()
        }
        txt_forgot.setOnClickListener {
            launchIntent(this, ForgotPinActivity::class.java, false)

        }
    }

    private fun googleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
        googleApiClient = GoogleApiClient.Builder(this)
            //.enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
            .build()

    }

    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, google_sign_in)
    }

    private fun fbSignin() {
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {

                    val request = GraphRequest.newMeRequest(
                        loginResult.accessToken
                    ) { `object`, response ->
                        Log.e("LoginActivity Response ", response.toString())

                        val id = `object`.optString("id")
                        val fName = `object`.optString("first_name")
                        val lName = `object`.optString("last_name")
                        val email = `object`.optString("email")
                        val mobile = `object`.optString("mobile")
                        val pictureUrl = "https://graph.facebook.com/$id/picture?type=large"
                        val name = "$fName $lName"
                        Toast.makeText(this@LoginActivity, "Success", Toast.LENGTH_LONG).show()

                    }

                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "id,first_name,last_name,email,picture.type(large)"
                    )
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {

                }

                override fun onError(e: FacebookException) {
                    Log.e("LoginActivity Response ", e.message.toString())
                }
            })
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val acct = result.signInAccount
            val personName = acct!!.displayName
            var personPhotoUrl: String? = null
            if (acct.photoUrl != null) {
                personPhotoUrl = acct.photoUrl!!.toString()
            }
            val email = acct.email
            Toast.makeText(this@LoginActivity, "Success", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_LONG).show()

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == google_sign_in) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result!!)
        } else
            callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    fun login() {
        progressBar!!.showProgress(this)
        val call: APIService = RetrofitFactory().create()!!
        val token = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
        jsonParams["pin"] = edt_pin_login.text.toString().toInt()
        jsonParams["userid"] = edt_id_login.text.toString().toLong()
        jsonParams["device"] = "android"
        jsonParams["device_token"] = token

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            JSONObject(jsonParams).toString()
        )

        call.api("auth/login", body).enqueue(object : Callback<UserBean> {

            override fun onResponse(calls: Call<UserBean>, response: Response<UserBean>) {

                progressBar!!.hideProgress()

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        response.body().message!!,
                        Toast.LENGTH_SHORT
                    ).show()
                    Utils.savePref(this@LoginActivity, Utils.token, response.body().access_token)
                    launchIntent(this@LoginActivity, HomeActivity::class.java, true)
                } else {
                    call.error(this@LoginActivity, response)
                }
            }

            override fun onFailure(call: Call<UserBean>, t: Throwable) {
                progressBar!!.hideProgress()
                Toast.makeText(
                    this@LoginActivity,
                    R.string.network_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
