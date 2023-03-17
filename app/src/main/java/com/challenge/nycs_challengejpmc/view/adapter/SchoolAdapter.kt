package com.challenge.nycs_challengejpmc.view

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.challenge.nycs_challengejpmc.databinding.SchoolItemViewBinding
import com.challenge.nycs_challengejpmc.model.domain.SchoolsItemDomain

class SchoolAdapter(
    private val itemSet: MutableList<SchoolsItemDomain> = mutableListOf(),
    private val onItemClick: (item: SchoolsItemDomain) -> Unit
): RecyclerView.Adapter<SchoolsViewHolder>() {

    fun updateItems(newItems: List<SchoolsItemDomain>){
        if (itemSet!=newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)

            notifyDataSetChanged()
            setData(newItems)
        }
    }

    // Create a separate list to store the original items
    private val originalItems: MutableList<SchoolsItemDomain> = mutableListOf()

    fun setData(items: List<SchoolsItemDomain>) {
        originalItems.clear()
        originalItems.addAll(items)
        itemSet.clear()
        itemSet.addAll(items)
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        val filteredItems = if (query.isBlank()) {
            // If the query is blank, restore the original items
            originalItems.toMutableList()
        } else {
            // Otherwise, filter the items based on the query
            originalItems.filter {
                it.school_name!!.contains(query, ignoreCase = true)

            }.toMutableList()
        }

        // Update your list with filtered items
        itemSet.clear()
        itemSet.addAll(filteredItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolsViewHolder =
        SchoolsViewHolder(
            SchoolItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itemSet.size

    override fun onBindViewHolder(holder: SchoolsViewHolder, position: Int) =
        holder.bind(itemSet[position], onItemClick)

}

class SchoolsViewHolder(
    private val binding: SchoolItemViewBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: SchoolsItemDomain, onItemClick: (SchoolsItemDomain) -> Unit) {
        binding.schoolName.text = item.school_name
        binding.schoolLocation.text = item.location?.substringBefore("(")

        itemView.setOnClickListener {
            item.let(onItemClick)
        }
    }
}