package com.syber.ssspltd.network

import com.syber.ssspltd.utils.AppSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val sharedPreferences: AppSharedPreferences) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val mainRequest = chain.request()
        val builder = mainRequest.newBuilder()
            .method(mainRequest.method, mainRequest.body)

        if(sharedPreferences.token!!.isNotEmpty()) {
            sharedPreferences.token?.let {
                builder.addHeader("Authorization", "Bearer $it")
       /*         builder.addHeader("Authorization",
       "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiREwxNTcxNSBLSVJBTiBHQVJNRU5UUyBLQVRJSEFSIiwiQ29tcGFueUlkIjoiNDMwMjk2MjQtZWE0YS00MzRjLTlhMTQtZDdkYTI0ODQwYmFkIiwiQWNjb3VudElkIjoiNjVmYjJmNDktYTZiMy00MzRkLWFlZGEtMDI0MDBjNzJjY2VjIiwiTW9iaWxlTm8iOiI3MjkwMDg3NjQyIiwiRmluWWVhcklkIjoiZDgyOWFjN2ItMTk4Zi00NDEzLWIwNDAtZjZiYmExN2ZmYWZhIiwiTWFya2V0ZXJDb2RlIjoiMTIzIiwiVXNlcklkIjoiNzViOTM5NDktZmYzZC00M2I5LThhYmYtM2ViZTA1OTU3MzI4IiwiQnJhbmNoQ29tcGFueUlkIjoiNDMwMjk2MjQtZWE0YS00MzRjLTlhMTQtZDdkYTI0ODQwYmFkIiwiQXBwTmFtZSI6IlNTU1BMVEQiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiJOb3YgVHVlIDA0IDIwMjUgMTA6NDU6MTQgQU0iLCJuYmYiOjE3NDY3MDExMTQsImV4cCI6MTc2MjIzMzMxNCwiaXNzIjoiaHR0cHM6Ly9sb2NhbGhvc3Q6NDIwMCIsImF1ZCI6Imh0dHBzOi8vbG9jYWxob3N0OjUwMDAifQ.tlV1SyaU7QMCQcwONbj8v5LqgRIpz2HSlpHf2sTbuug" )
       */     }
        }

        return chain.proceed(builder.build())
    }
}