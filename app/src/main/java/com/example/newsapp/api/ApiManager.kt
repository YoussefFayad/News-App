package com.example.newsapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiManager {
    private var retrofit : Retrofit? =null

    // this method just to build object from NewService interface,
    // and used in it LoggingInterceptor to show response-body comeback from api in Logcat

     fun getNewService(): NewService {
        if (retrofit == null){
            // HttpLoggingInterceptor is library must define in gradle before call here
            val loggingInterceptor = HttpLoggingInterceptor{
                Log.e("ApiManager","api-body -> $it")
            }
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            // okHttpClient convert loggingInterceptor -> client can passing to retrofit-client
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient) //link interceptor with retrofit-client
                .build()
        }
        return retrofit!!.create(NewService::class.java)

    }

}