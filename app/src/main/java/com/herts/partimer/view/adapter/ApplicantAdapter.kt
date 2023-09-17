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
import com.herts.partimer.response.Applicant
import com.herts.partimer.response.StudentProfileList
import de.hdodenhof.circleimageview.CircleImageView


class ApplicantAdapter(var listener: HomeListener) :
    RecyclerView.Adapter<ApplicantAdapter.HomeViewHolder>() {

    private var data: ArrayList<StudentProfileList>? = null

    interface HomeListener {
        fun onItemClicked(postModel: StudentProfileList, position: Int)
    }

    fun setData(list: ArrayList<StudentProfileList>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.result_list_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        holder.main.setOnClickListener {
            item?.let { it1 ->
                listener.onItemClicked(it1, position)
            }
        }
    }

    fun addData(postModel: StudentProfileList) {
        data?.add(0, postModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_brand_name = itemView.findViewById<TextView>(R.id.user_name)
        val txt_car_specification = itemView.findViewById<TextView>(R.id.available)
        val txt_ratings = itemView.findViewById<TextView>(R.id.percent)
        val user_profile_pic = itemView.findViewById<CircleImageView>(R.id.user_profile_pic)
        val main = itemView.findViewById<RelativeLayout>(R.id.main)
        val textView3 = itemView.findViewById<TextView>(R.id.textView3)

        fun bindView(item: StudentProfileList?) {

            /* val decodedString: ByteArray = Base64.decode(item?.image1, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            user_profile_pic.setImageBitmap(decodedByte)*/
            txt_brand_name.text = item?.user?.first_name+ " "+ item?.user?.last_name
            txt_car_specification.text = item?.city
            txt_ratings.text = item?.matchingObject?.percentage?.percentage.toString().substringBefore(".")+ "%"
            if (item?.immediateJoining ==true) {
                textView3.text = "Available for immediate joining"

            } else {
                textView3.text = "Not Available for immediate joining"

            }

        }

    }
}