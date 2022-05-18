/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsphotos.network.MarsApi
import com.example.android.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch

/** ENUM */
// enum is short for enumeration
// enumeration - ordered listing of all the items in a collection
// each enum constant is an object of the enum class
// enum is a data type that can hold a set of constants
enum class MarsApiStatus { LOADING, DONE, ERROR }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()
    // The external immutable LiveData for the request status
    val status: LiveData<MarsApiStatus> = _status

    private val _photos = MutableLiveData<List<MarsPhoto>>()
    // public backing field
    val photos: LiveData<List<MarsPhoto>> = _photos

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getMarsPhotos() {
        viewModelScope.launch {

            _status.value = MarsApiStatus.LOADING

            try {
                _photos.value = MarsApi.retrofitService.getPhotos()
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _photos.value = listOf()
            }

        // Code displaying a photo URL
            /**
                try {
                    _photo.value = MarsApi.retrofitService.getPhotos()[6]
                    _status.value = "7th Mars image URL : ${_photo.value!!.imgSrcURL}"
                } catch (e: Exception) {
                    _status.value = "Failure: ${e.message}"
                }
            */

            // Code displaying how many photos was retrieved
            /**
                try {
                    val listResult = MarsApi.retrofitService.getPhotos()
                    _status.value = "Success: ${listResult.size} Mars photos retrieved!"
                } catch(e: Exception) {
                    _status.value = "Failure: ${e.message}"
                }
            */

        }
    }
}
