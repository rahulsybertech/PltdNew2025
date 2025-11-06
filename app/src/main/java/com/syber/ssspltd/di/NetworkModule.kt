package com.app.naturalhigh.di

import android.util.Log
import com.google.gson.Gson
import com.itkacher.okprofiler.BuildConfig

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.syber.ssspltd.network.ApiService
import com.syber.ssspltd.network.AuthInterceptor
import com.syber.ssspltd.utils.MyConstant.BASE_URL
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        okHttpProfilerInterceptor: OkHttpProfilerInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val urlLoggingInterceptor = Interceptor { chain ->
            val request = chain.request()
            val url = request.url.toString()
            Log.e("url", url)
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(urlLoggingInterceptor) // ✅ add here
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(okHttpProfilerInterceptor)
                    addInterceptor(httpLoggingInterceptor)
                }
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideOkhttpProfiler(): OkHttpProfilerInterceptor {
        return OkHttpProfilerInterceptor()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}
