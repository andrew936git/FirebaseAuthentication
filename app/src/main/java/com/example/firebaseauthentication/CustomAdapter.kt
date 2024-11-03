package com.example.firebaseauthentication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val list: MutableList<ContactData?>):
    RecyclerView.Adapter<CustomAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameTV: TextView = itemView.findViewById(R.id.nameTV)
        val phoneTV: TextView = itemView.findViewById(R.id.phoneTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val contact = list[position]
        holder.nameTV.text = contact?.name
        holder.phoneTV.text = contact?.phone

    }
}