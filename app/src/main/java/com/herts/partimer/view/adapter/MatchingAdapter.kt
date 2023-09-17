package com.herts.partimer.view.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.herts.partimer.R
import com.herts.partimer.request.Experience
import com.herts.partimer.request.Week
import com.herts.partimer.response.Applicant
import de.hdodenhof.circleimageview.CircleImageView


class MatchingAdapter() :
    RecyclerView.Adapter<MatchingAdapter.HomeViewHolder>() {

    private var data: ArrayList<Experience>? = null

    fun setData(list: ArrayList<Experience>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.match_items, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)

    }

    fun addData(postModel: Experience) {
        data?.add(0, postModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cat = itemView.findViewById<TextView>(R.id.job_title)
        val role = itemView.findViewById<TextView>(R.id.job_description)
        val years = itemView.findViewById<TextView>(R.id.interested_text)
        val desc = itemView.findViewById<TextView>(R.id.desc)

        fun bindView(item: Experience?) {
            cat.text = "Category: " + item?.category
            role.text = "Role: " + item?.role
            years.text = "Years of Experience: " + item?.year.toString()
            desc.text = "Description: " + item?.jobDescription
        }

    }

}