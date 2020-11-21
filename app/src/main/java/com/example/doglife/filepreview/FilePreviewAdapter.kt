package com.example.doglife.filepreview

import android.app.Activity
import android.content.Context
import android.text.format.Formatter.formatShortFileSize
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.doglife.R
import com.example.doglife.utils.GlideImageLoader
import com.lzy.imagepicker.bean.ImageItem
import kotlinx.android.synthetic.main.file_preview_item.view.*
import kotlinx.android.synthetic.main.file_preview_item.view.iv_file_preview

typealias FileClickCallback = (view: View, path: String) -> Unit

class FilePreviewAdapter(
    private val context: Context,
    private val clickCallback: FileClickCallback
) : RecyclerView.Adapter<FilePreviewAdapter.ItemViewHolder>() {

    private var dataList: List<ImageItem> = emptyList()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.file_preview_item, parent, false)
        return ItemViewHolder(binding, clickCallback)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)
    }

    fun setData(files: List<ImageItem>) {
        dataList = files
        notifyDataSetChanged()
    }

    fun resetData() {
        dataList = emptyList()
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ViewDataBinding, val clickCallback: FileClickCallback) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ImageItem, context: Context) {
            binding.root.checkbox_remove_file_item.setOnClickListener { view ->
                clickCallback(view, data.path)
            }
            binding.root.tv_video_duration.visibility = View.GONE
            GlideImageLoader(context).loadProfileCover(data.path, binding.root.iv_file_preview, 0)
            binding.root.iv_file_preview.visibility = View.VISIBLE

//            if (data.mimeType.startsWith("video/")) {
//                binding.root.tv_video_duration.text = SimpleDateFormat("mm:ss").format(
//                    data.getDuration()?.let { Date(it) })
//                binding.root.tv_video_duration.visibility = View.VISIBLE
//            }

        }
    }
}