package com.herts.partimer.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.herts.partimer.R
import com.herts.partimer.request.Category
import com.herts.partimer.request.Roles


class SkillAdapter(
    var context1: Context, // ArrayList
    var cat1: ArrayList<Category>,
    var listener: SkillAdapter.HomeListener
) :
    RecyclerView.Adapter<SkillAdapter.ViewHolder>() {
    var cat: ArrayList<Category> = cat1
    var context: Context? = context1

    interface HomeListener {
        fun onItemClicked(postModel: Roles?, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.skill_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        // setting image resource
        val item = cat.get(i)
        viewHolder.tvCat.setText(item.category)
        viewHolder.role1.setText(item.role?.get(0)?.role)
        viewHolder.role2.setText(item.role?.get(1)?.role)
        if (!item.role?.get(0)?.isSelected!!) {
            viewHolder.role1.setBackgroundResource(R.drawable.textview)
        } else {
            viewHolder.role1.setBackgroundResource(R.drawable.textview_clicked)
        }
        if (!item.role?.get(1)?.isSelected!!) {
            viewHolder.role2.setBackgroundResource(R.drawable.textview)
        } else {
            viewHolder.role2.setBackgroundResource(R.drawable.textview_clicked)
        }


        viewHolder.role1.setOnClickListener {
            listener.onItemClicked(item.role?.get(0), 0)
        }
        viewHolder.role2.setOnClickListener {
            listener.onItemClicked(item.role?.get(1), 1)
        }

        if (item.role?.size == 2) {
            viewHolder.role3.visibility = View.GONE
            viewHolder.role4.visibility = View.GONE
        } else if (item.role?.size == 3) {
            if (!item.role?.get(2)?.isSelected!!) {
                viewHolder.role3.setBackgroundResource(R.drawable.textview)
            } else {
                viewHolder.role3.setBackgroundResource(R.drawable.textview_clicked)
            }
            viewHolder.role3.setText(item.role?.get(2)?.role)
            viewHolder.role3.visibility = View.VISIBLE
            viewHolder.role4.visibility = View.GONE

            viewHolder.role3.setOnClickListener {
                listener.onItemClicked(item.role?.get(2), 2)
            }
        } else {
            if (!item.role?.get(3)?.isSelected!!) {
                viewHolder.role4.setBackgroundResource(R.drawable.textview)
            } else {
                viewHolder.role4.setBackgroundResource(R.drawable.textview_clicked)
            }
            viewHolder.role4.setText(item.role?.get(3)?.role)
            viewHolder.role3.visibility = View.VISIBLE
            viewHolder.role4.visibility = View.VISIBLE

            viewHolder.role4.setOnClickListener {
                listener.onItemClicked(item.role?.get(3), 3)
            }
        }
    }

    override fun getItemCount(): Int {
        return cat.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCat: TextView
        var role1: TextView
        var role2: TextView
        var role3: TextView
        var role4: TextView

        init {
            // getting ImageView reference
            tvCat = itemView.findViewById<View>(R.id.categ) as TextView
            role1 = itemView.findViewById<View>(R.id.role1) as TextView
            role2 = itemView.findViewById<View>(R.id.role2) as TextView
            role3 = itemView.findViewById<View>(R.id.role3) as TextView
            role4 = itemView.findViewById<View>(R.id.role4) as TextView
        }
    }
}