package com.assessment.weather.module

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.assessment.weather.R
import com.assessment.weather.module.model.ForecastdayItem
import com.bumptech.glide.Glide


class ForecastListAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0

    private val VIEW_TYPE_LOADING = 1
    private val VIEW_TYPE_AD = 2
    private var loading = false
    private var data: ArrayList<ForecastdayItem>? = null

    private var mContext: Context = context

    init {
        data = ArrayList()
    }

    fun addAllCategory(categoryList: ArrayList<ForecastdayItem>) {
        for (member in categoryList) {
            addItem(member)
        }
        notifyDataSetChanged()
    }

    private fun addItem(item: ForecastdayItem) {
        data?.add(item)
        //mMainList = data
        notifyItemInserted(data?.size!!)
    }


    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_forecast,
                    parent,
                    false
                )
             return CategoryViewHolder(view)
        }
        return null!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is CategoryViewHolder) {
            val snippet = data!![position]
            holder.temp.text = "${snippet.day!!.avgtempC.toString().split(".")[0]}°"
            Glide.with(mContext).load("https:${snippet.day.condition!!.icon}")
                .placeholder(R.drawable.placeholder).error(
                    R.drawable.ic_warning_black_24dp
                ).into(holder.imageView)
            holder.dateTv.text=snippet.date
            holder.descTv.text=snippet.day.condition.text
        }
    }

    fun filterList(filteredList: ArrayList<ForecastdayItem>?) {
        this.data = filteredList
        notifyDataSetChanged()
    }

    fun getDataList(): MutableList<ForecastdayItem>? {
        return this.data
    }

    fun clearArrayList(){
        this.data!!.clear()
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    private fun isPositionFooter(position: Int): Boolean {
        return position == data!!.size
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    inner class CategoryViewHolder
    internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var temp: TextView
        lateinit var imageView: ImageView
        lateinit var dateTv: TextView
        lateinit var descTv: TextView

        init {
            try {
                temp = itemView.findViewById(R.id.forecastTempTv)
                imageView = itemView.findViewById(R.id.forecastImage)
                dateTv = itemView.findViewById(R.id.forecastDateTv)
                descTv = itemView.findViewById(R.id.forecastDescTv)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}