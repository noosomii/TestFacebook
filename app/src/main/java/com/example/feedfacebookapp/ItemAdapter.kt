package com.example.feedfacebookapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feedfacebookapp.model.Item
import kotlinx.android.synthetic.main.item.view.*

class ItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList by lazy { mutableListOf<Item>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.setViewData(itemList[position])
    }

    fun addAll(listMenus: List<Item>) {
        if(itemList.isNotEmpty()) itemList.clear()
        itemList.addAll(listMenus)
        notifyDataSetChanged()
    }
}

class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    fun setViewData(itemModel: Item) {
        view.txtData.text = itemModel.name
    }

}