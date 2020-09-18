package com.betterlyf.app.bean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserBean {
    val response: Boolean? = false
    val message: String? = null
    val access_token: String? = null
    val data: User? = null

    class User {
        val email: String? = null
        val mobile: String? = null
        val userid: Int? = null
        val pin: Int? = null
        val firstname: String? = null
        val lastname: String? = null
    }
}