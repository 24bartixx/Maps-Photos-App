package com.example.android.marsphotos

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.network.MarsPhoto
import com.example.android.marsphotos.overview.MarsApiStatus
import com.example.android.marsphotos.overview.PhotoGridAdapter

// BINDING ADAPTERS
// data binding library manages data adapters

// top level function
// annotation of binding adapter
// annotation takes name attribute (xml file) as parameter
@BindingAdapter("imageUrl")
fun bindImage (imageView: ImageView, imageURL: String?) {
    // let {} function lets execute code within the context of an object
    // Scope functions - execute a block of code within the context of an object
    // when you call such a function on an object with a lambda expression, it forms temporary scope
    // in this scope you can access the object without its name
    // Scope functions: let, run, with, apply, also
    // ? - safe call operator -> to perform a null safe operation on the object

    imageURL?.let {

        // convert URL string to Uri object
        // to use HTTPS scheme append "buildUpon().scheme("https") to the toUri builder
        val imageURI = imageURL.toUri().buildUpon().scheme("https").build()

        // load image from the imageURI into the ImageView
        imageView.load(imageURI) {
            // trailing lambda
            // set placeholder loading image to use while loading
            placeholder(R.drawable.loading_animation)
            // set an image to use if image loading fails
            error(R.drawable.ic_broken_image)
        }
    }
}

// BindingAdapter setting the RecyclerView data causes data binding to automatically observe the LiveData for the list of MarsPhoto objects
// when the MarsPhoto list changes binding adapter is called automatically
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsPhoto>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: MarsApiStatus?) {
    when(status){
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}