package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// NETWORK LAYER

// URL
// https://l.messenger.com/l.php?u=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FUniform_Resource_Identifier&h=AT1uRpRDGFKwoJ-NoHWXRP1rTnoJ8vdydNkUQ_jcljdeO8lz9Qfm_4KxcdRWOs01WlhmIAiPKh1vbyPRg9m4o9BmW4Z17_0w2BIrQlez_0RCizN421Zhqder01xbIEbon8j9sw
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

// Moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit object
// converter fetches a JSON response and returns it as a string
private val retrofit = Retrofit.Builder()
    // (CODE) .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// this interface defines how Retrofit talks to the web server using HTTP request
interface MarsApiService {
    // telling Retrofit that this is GET request
    // endpoint "photos" - path
    @GET ("photos")
    // abstract method, suspend to be able to call this method from within a coroutine
    suspend fun getPhotos(): List<MarsPhoto>
}

// initialize the Retrofit service and expose the instance of the api
// API - connection between computers or computer programs
object MarsApi {
    // adding retrofit object - lazy initialization to make sure it is initialized at its first usage
    // "lazy" initialization can be delayed until that object is necessary
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}