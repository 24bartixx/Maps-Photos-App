package com.example.android.marsphotos.network

import com.squareup.moshi.Json

data class MarsPhoto (
    // "id" and "img_src" are key names in the received JSON object
    // Moshi library matches the keys by name and fills the data objects with values
    val id: String,
    @Json(name="img_src") val imgSrcURL: String
)
