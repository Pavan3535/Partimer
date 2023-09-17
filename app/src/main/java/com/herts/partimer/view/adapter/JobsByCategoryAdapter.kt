package com.herts.partimer.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.herts.partimer.R
import com.herts.partimer.response.JobProfileList


class JobsByCategoryAdapter(var listener: HomeListener) :
    RecyclerView.Adapter<JobsByCategoryAdapter.HomeViewHolder>() {

    private var data: ArrayList<JobProfileList>? = null

    interface HomeListener {
        fun onItemView(postModel: JobProfileList, position: Int)
    }

    fun setData(list: ArrayList<JobProfileList>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.jobs_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        holder.card.setOnClickListener {
            item?.let { it1 ->
                listener.onItemView(it1, position)
            }
        }
    }

    fun addData(postModel: JobProfileList) {
        data?.add(0, postModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val job_title = itemView.findViewById<TextView>(R.id.job_title)
        val job_description = itemView.findViewById<TextView>(R.id.job_description)
        val percent = itemView.findViewById<TextView>(R.id.percent)
        val city = itemView.findViewById<TextView>(R.id.city)
        val card = itemView.findViewById<CardView>(R.id.card)
        val car_thumbnail = itemView.findViewById<ImageView>(R.id.job_image)
        fun bindView(item: JobProfileList?) {

            /*  val decodedString: ByteArray = Base64.decode(item?., Base64.DEFAULT)
              val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
              car_thumbnail.setImageBitmap(decodedByte)*/

            job_title.text = item?.jobTitle
            job_description.text = "Looking for a " + item?.role
            percent.text =
                item?.matchingObject?.percentage?.percentage.toString().substringBefore(".") + "%"
            city.text = "Location: " + item?.city

        }

    }

}