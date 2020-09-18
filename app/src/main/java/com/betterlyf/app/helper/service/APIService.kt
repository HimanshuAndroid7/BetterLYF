package com.betterlyf.app.helper.service

import android.content.Context
import android.widget.Toast
import com.betterlyf.app.bean.UserBean
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {

    @POST()
    fun api(
        @Url url: String,
        @Body params: RequestBody
    ): Call<UserBean>

    @GET()
    fun apiGet(@Url url:String): Call<UserBean>

    fun error(context: Context, response: Response<UserBean>) =
        try {
            val jObjError = JSONObject(response.errorBody().string())
            Toast.makeText(
                context,
                jObjError.getString("errors"),
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }

}