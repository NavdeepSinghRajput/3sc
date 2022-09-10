package com.task.a3sctask.network

import com.google.gson.GsonBuilder
import com.task.a3sctask.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {

        fun getClient(): Retrofit? {
            return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
//            return Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                .client(getOkHttpClient())
                .addConverterFactory(
                    GsonConverterFactory
                        .create(
                            GsonBuilder().serializeNulls()
                                .excludeFieldsWithModifiers(
                                    Modifier.FINAL,
                                    Modifier.TRANSIENT, Modifier.STATIC
                                )
                                .create()
                        )
                )
                .build()
        }

        private fun getOkHttpClient(): OkHttpClient? {
            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            val httpClient = OkHttpClient()
            val builder = httpClient.newBuilder()
            builder.addInterceptor(interceptor)
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.readTimeout(60, TimeUnit.SECONDS)
            return builder.build()
        }
    }


}