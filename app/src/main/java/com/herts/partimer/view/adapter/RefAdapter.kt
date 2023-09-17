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
import com.herts.partimer.request.Reference
import com.herts.partimer.request.Week
import com.herts.partimer.response.Applicant
import de.hdodenhof.circleimageview.CircleImageView


class RefAdapter() :
    RecyclerView.Adapter<RefAdapter.HomeViewHolder>() {

    private var data: ArrayList<Reference>? = null

    fun setData(list: ArrayList<Reference>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.exp_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)

    }

    fun addData(postModel: Reference) {
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

        fun bindView(item: Reference?) {
            cat.text = "Reference Name: " + item?.name
            role.text = "Reference Contact Email: " + item?.email
            years.text = "Relationship with student: " + item?.relation
        }

    }

}