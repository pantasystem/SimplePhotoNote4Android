package ie2a_2200078.eventwork05.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ie2a_2200078.eventwork05.databinding.ItemGalleryNoteBinding
import ie2a_2200078.eventwork05.entities.GalleryNoteWithFile
import ie2a_2200078.eventwork05.viewmodels.GalleryPostsViewModel

fun interface OnFavoriteClickListener {
    fun onClick(galleryNoteWithFile: GalleryNoteWithFile)
}

class GalleryNoteWithFileListAdapter(
    val onFavoriteClickListener: OnFavoriteClickListener,
    private val lifecycleOwner: LifecycleOwner,
    private val galleryPostsViewModel: GalleryPostsViewModel
) : ListAdapter<GalleryNoteWithFile, GalleryNoteWithFileListAdapter.GalleryViewHolder>(GalleryDiffItemCallback){

    inner class GalleryViewHolder(private val binding: ItemGalleryNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(galleryNoteWithFile: GalleryNoteWithFile) {
            binding.galleryNoteWithFile = galleryNoteWithFile
            binding.lifecycleOwner = lifecycleOwner

            binding.onFavoriteClickListener = onFavoriteClickListener
            binding.galleryImagePager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            val adapter = PhotoListAdapter(lifecycleOwner)
            binding.galleryImagePager.adapter = adapter
            adapter.submitList(galleryNoteWithFile.files)

            TabLayoutMediator(binding.imagesTab, binding.galleryImagePager) { _, _ ->

            }.attach()
            binding.galleryImagePager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    galleryPostsViewModel.setCurrentPhotoIndex(galleryNoteWithFile.galleryNote.id, position)
                }
            })
            if(galleryNoteWithFile.files.isNotEmpty()) {
                binding.galleryImagePager.setCurrentItem(galleryPostsViewModel.getCurrentPhotoIndex(galleryNoteWithFile.galleryNote.id), false)
            }

            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(ItemGalleryNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}

object GalleryDiffItemCallback : DiffUtil.ItemCallback<GalleryNoteWithFile>() {
    override fun areContentsTheSame(
        oldItem: GalleryNoteWithFile,
        newItem: GalleryNoteWithFile
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: GalleryNoteWithFile,
        newItem: GalleryNoteWithFile
    ): Boolean {
        return oldItem.galleryNote.id == newItem.galleryNote.id
    }

}