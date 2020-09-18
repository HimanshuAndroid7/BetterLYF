package com.betterlyf.app.helper.service

import android.content.Context
import com.betterlyf.app.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RetrofitFactory {
    private val Base_Url: String? = "http://595b1a1f6ef4.ngrok.io/betterlyf/public/api/v1/"
    var mInstance: Retrofit? = null


    fun getInstance(): Retrofit {
        if (mInstance == null) {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                val logging = HttpLoggingInterceptor()
                // Set your desired log level. Use Level.BODY for debugging errors.
                logging.level = HttpLoggingInterceptor.Level.BODY

                val builder = OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)

               // builder.addInterceptor(logging)
                builder.sslSocketFactory(sslSocketFactory)
                builder.hostnameVerifier { hostname, session -> true }
                builder.addInterceptor { chain ->
                    val originalRequest = chain.request()

                    val request = originalRequest.newBuilder()

                        //.header("Authorization", "Bearer $token")
                        .header("Content-Type", "application/json")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .method(originalRequest.method(), originalRequest.body())
                        .build()

                    chain.proceed(request)
                }
                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                mInstance = Retrofit.Builder()
                    //                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(Base_Url)
                    .client(builder.build())
                    .build()

            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
        return this.mInstance!!
    }

    fun create(): APIService? {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.connectTimeout(5, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(interceptor)
        }
        builder.addInterceptor { chain: Interceptor.Chain ->
            val request: Request = chain.request().newBuilder().addHeader("Content-Type", "application/json")
                .addHeader("X-Requested-With","XMLHttpRequest").build()
            chain.proceed(request)
        }
        val client = builder.build()
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(Base_Url).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build()
        return retrofit.create(APIService::class.java)
    }


    fun getRetrofitInstance(token: String):  APIService? {

        //OkHttpClient client = getUnsafeOkHttpClient();

        val client = getUnsafeOkHttpClientToken(token)

        mInstance = Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return this!!.mInstance!!.create(APIService::class.java)
    }

    private fun getUnsafeOkHttpClientToken(token: String): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

            builder.addInterceptor { chain ->
                val originalRequest = chain.request()

                val request = originalRequest.newBuilder()

                    .header("Authorization", "Bearer $token")
                    .header("Content-Type", "application/json")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .method(originalRequest.method(), originalRequest.body())
                    .build()

                chain.proceed(request)
            }
           // builder.sslSocketFactory(sslSocketFactory)
            builder.hostnameVerifier { hostname, session -> true }

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}