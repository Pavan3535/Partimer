package com.herts.partimer.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.herts.partimer.R
import com.herts.partimer.request.Category


class CategoryAdapter(
    var context1: Context, // ArrayList
    var cat1: ArrayList<Category>,
    var listener: CategoryAdapter.HomeListener
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var cat: ArrayList<Category> = cat1
    var context: Context? = context1

    interface HomeListener {
        fun onItemClicked(postModel: Category?, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cat_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        // setting image resource
        viewHolder.tvCat.setText(cat.get(i).category)
        if (!cat.get(i).isSelected) {
            viewHolder.tvCat.setBackgroundResource(R.drawable.textview)
        } else {
            viewHolder.tvCat.setBackgroundResource(R.drawable.textview_clicked)
        }
        viewHolder.tvCat.setOnClickListener {

            cat?.get(i).let { it1 ->
                listener.onItemClicked(it1, i)
            }
        }
    }

    override fun getItemCount(): Int {
        return cat.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCat: TextView

        init {
            // getting ImageView reference
            tvCat = itemView.findViewById<View>(R.id.cat) as TextView
        }
    }
}