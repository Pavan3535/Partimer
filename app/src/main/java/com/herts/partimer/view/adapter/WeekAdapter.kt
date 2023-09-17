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
import com.herts.partimer.request.Week
import com.herts.partimer.response.Applicant
import de.hdodenhof.circleimageview.CircleImageView


class WeekAdapter(var listener: HomeListener) :
    RecyclerView.Adapter<WeekAdapter.HomeViewHolder>() {

    private var data: ArrayList<Week>? = null

    interface HomeListener {
        fun onDayClicked(postModel: Week, position: Int)
        fun onNightClicked(postModel: Week, position: Int)
    }

    fun setData(list: ArrayList<Week>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        holder.day.setOnClickListener {
            item?.let { it1 ->
                listener.onDayClicked(it1, position)
            }
        }
        holder.night.setOnClickListener {
            item?.let { it1 ->
                listener.onNightClicked(it1, position)
            }
        }
    }

    fun addData(postModel: Week) {
        data?.add(0, postModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weekDay = itemView.findViewById<TextView>(R.id.tag)
        val day = itemView.findViewById<TextView>(R.id.day)
        val night = itemView.findViewById<TextView>(R.id.night)

        fun bindView(item: Week?) {
            weekDay.text = item?.dayOfWeek
            if (item?.day == 0) {
                day.setBackgroundResource(R.drawable.textview)
            } else {
                day.setBackgroundResource(R.drawable.textview_clicked)
            }

            if (item?.night == 0) {
                night.setBackgroundResource(R.drawable.textview)
            } else {
                night.setBackgroundResource(R.drawable.textview_clicked)
            }
        }

    }

}