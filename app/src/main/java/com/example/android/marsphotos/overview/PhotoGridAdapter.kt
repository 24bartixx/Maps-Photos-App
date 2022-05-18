package com.example.android.marsphotos.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.MarsPhoto

// declaration of PhotoGridAdapter without DiffUtil implementation
/**
class PhotoGridAdapter(private val context: Context, private val dataset: List<MarsPhoto>):
    RecyclerView.Adapter<PhotoGridAdapter.ItemViewHolder()>
*/
// declaration of PhotoGridAdapter with DiffUtil
// with DiffUtil every time some item is added removed or changed, the whole list doesn't get refreshed
// extend PhotoGridAdapter class from ListAdapter
// constructor of ListAdapter needs as parameters the list item type, the view holder, and a DiffUtil.ItemCallback implementation
class PhotoGridAdapter: ListAdapter <MarsPhoto, PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {

    // view holder declaration with view parameter
    /** class MarsPhotoViewHolder(private val view: View): RecyclerView.ViewHolder(view) {} */

    // view holder declaration with binding
    class MarsPhotoViewHolder (private var binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(MarsPhoto: MarsPhoto) {
            binding.photo = MarsPhoto
            // this causes the update to execute immediately
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.MarsPhotoViewHolder {
        return MarsPhotoViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }

    //Member of the companion object can be called by using the cass name as qualifier: DiffCallback.-
    companion object DiffCallback: DiffUtil.ItemCallback<MarsPhoto>() {

        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
            return oldItem.imgSrcURL == newItem.imgSrcURL
        }

    }
}