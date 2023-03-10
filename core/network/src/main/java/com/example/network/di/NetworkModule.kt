package com.example.network.di

import com.example.common.Constants
import com.example.common.Constants.ECHO_URL
import com.example.network.retrofit.CometClient
import com.example.network.retrofit.HelloWorldApi
import com.example.network.retrofit.MyInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS )
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(MyInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun providesHelloWorldApi(okHttpClient: OkHttpClient): HelloWorldApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(HelloWorldApi::class.java)
    }

    @Singleton
    @Provides
    fun providesRequest() : Request{
        return Request.Builder()
            .url(ECHO_URL)
            .build()
    }

    @Provides
    fun getHttpClient(httpClient: CometClient): HttpClient = httpClient.getHttpClient()
}