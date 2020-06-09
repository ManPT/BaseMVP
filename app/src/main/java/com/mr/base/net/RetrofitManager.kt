package com.lib.net

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {
    var retrofit: Retrofit? = null
    var httpClient: OkHttpClient? = null

    fun getMyRetrofit(): Retrofit? {
        if (retrofit == null) {
            httpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .connectTimeout(HttpConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
        }

        return retrofit
    }


}