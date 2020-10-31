package com.example.doglife.adopt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.doglife.Model.Dog
import com.example.doglife.R
import kotlinx.android.synthetic.main.adopt_overview_item.view.*

class PetOverviewAdapter(dummyDogList: MutableList<Dog>, context: Context, itemListener: ItemListener) : RecyclerView.Adapter<PetOverviewAdapter.ViewHolder>() {
    private var mDataList = dummyDogList
    private var mContext = context
    private var mListener = itemListener

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.adopt_overview_item, parent, false)
        return ViewHolder(binding, mListener, mContext)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(mDataList[position])
    }

    class ViewHolder(val binding: ViewDataBinding, val listener: ItemListener, val context: Context): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        lateinit var mDog: Dog
        fun bind(dog: Dog) {
            mDog = dog
            binding.root.imageView.setImageDrawable(context.resources.getDrawable(R.drawable.dummy_dog_aladdin))
            binding.root.textView.text = dog.name
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            listener.onItemClick(mDog)
        }
    }

    interface ItemListener {
        fun onItemClick(item: Dog)
    }

}