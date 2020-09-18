package com.betterlyf.app

import android.provider.Settings
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FirebaseIDService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() { // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        // Log.e(TAG, "Refreshed token: $refreshedToken")
        /* if (refreshedToken != null)
            sendToken(refreshedToken);*/
    }


  /*  private fun sendToken(token: String) {
        val retrofit: Retrofit = RetrofitFactory().getInstance()
        val service = retrofit.create(APIService::class.java)
        val deviceToken = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        val call = service.sendToken(deviceToken, token)
        call.enqueue(object : Callback<LoginBean?> {
            override fun onResponse(
                call: Call<LoginBean?>,
                response: Response<LoginBean?>
            ) {
            }

            override fun onFailure(
                call: Call<LoginBean?>,
                t: Throwable
            ) {
            }
        })
    }*/

   /* companion object {
        private const val TAG = "FirebaseIDService"
    }*/
}