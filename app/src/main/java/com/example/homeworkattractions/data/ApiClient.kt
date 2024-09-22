package com.example.homeworkattractions.data

import com.example.homeworkattractions.model.AttractionResponse
import com.example.homeworkattractions.model.NewsResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor() {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    fun testApiCall() {
        val service = createService()
        GlobalScope.launch {
//            try {
//                val response = service.fetchNews("en")
//                Log.d("ApiClient", "Response: $response")
//            } catch (e: Exception) {
//                Log.e("ApiClient", "Error: ${e.message}")
//            }
        }
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    fun createService(): ServicesApiInterface {
        return retrofit.create(ServicesApiInterface::class.java)
    }

    interface ServicesApiInterface {
        @GET("{lang}/Events/News")
        suspend fun fetchNews(@Path("lang") lang: String, @Query("page") page: Int): NewsResponse

        @GET("{lang}/Attractions/All")
        suspend fun fetchAttractions(
            @Path("lang") lang: String,
            @Query("page") page: Int
        ): AttractionResponse
    }

    companion object {
        private const val API_BASE_URL = "https://www.travel.taipei/open-api/"
    }
}
