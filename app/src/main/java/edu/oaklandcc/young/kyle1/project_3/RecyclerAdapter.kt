package edu.oaklandcc.young.kyle1.project_3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdapter(private val items: ArrayList<Items>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemIcon = itemView.findViewById<ImageView>(R.id.item_icon)
        val itemDate = itemView.findViewById<TextView>(R.id.item_date)
        val itemTemp = itemView.findViewById<TextView>(R.id.item_temp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false) as CardView
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.with(holder.itemView.context).load(items[position].icon).into(holder.itemIcon)
        holder.itemDate.text = items[position].date
        holder.itemTemp.text = items[position].temp
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
