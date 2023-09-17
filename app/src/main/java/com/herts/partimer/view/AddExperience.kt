package com.herts.partimer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.herts.partimer.R
import com.herts.partimer.request.Experience
import com.herts.partimer.view.adapter.ExpAdapter
import com.herts.partimer.view.adapter.WeekAdapter

class AddExperience : AppCompatActivity() {

    var experienceTv: TextView? = null
    var jobtitle: EditText? = null
    var category: Spinner? = null
    var role: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience)
        initViews()
    }

    private fun initViews() {
        var experienceCount: Int = 0
        jobtitle = findViewById<EditText>(R.id.et_job_desc)
        category = findViewById<Spinner>(R.id.spinner_category)
        role = findViewById<Spinner>(R.id.spinner_role)
        experienceTv = findViewById(R.id.exp_year)

        category?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val cat = parent?.getItemAtPosition(position).toString()
                updateRole(cat)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val plusBtn = findViewById<ImageButton>(R.id.button_plus)
        val minusBtn = findViewById<ImageButton>(R.id.button_minus)

        plusBtn.setOnClickListener {
            if (experienceCount < 40)
                experienceCount++
            experienceTv?.text = experienceCount.toString()
        }

        minusBtn.setOnClickListener {
            if (experienceCount > 0)
                experienceCount--
            experienceTv?.text = experienceCount.toString()
        }

        val btn_exp = findViewById<Button>(R.id.btn_exp)
        btn_exp.setOnClickListener {

            val experience = Experience()
            experience.category = category?.selectedItem.toString()
            experience.role = role?.selectedItem.toString()
            experience.year = experienceTv?.text.toString().toInt()
            experience.jobDescription = jobtitle?.text.toString()

            val intent = Intent()
            intent.putExtra("exp", experience)
            this.setResult(RESULT_OK, intent)
            finish()

        }

    }

    private fun updateRole(cat: String) {
        val list: MutableList<String> = ArrayList()
        list.clear()

        when (cat) {
            "Retail and Customer Service" -> {
                list.clear()
                list.add("Store helper")
                list.add("Cashier")
                list.add("Customer service representative")
            }

            "Office Support" -> {
                list.clear()
                list.add("Data entry")
                list.add("Receptionist")
                list.add("Office assistant")
            }

            "Fitness" -> {
                list.clear()
                list.add("Fitness assistant")
                list.add("Yoga instructor")
                list.add("Personal trainer")
            }

            "Event Management" -> {
                list.clear()
                list.add("Helper")
                list.add("setup and teardown")
                list.add("coordinator assistant")
            }

            "Pet Care" -> {
                list.clear()
                list.add("Dog walker")
                list.add("Pet Trainer")
                list.add("Pet groomer assistant")
            }

            "Household Chores" -> {
                list.clear()
                list.add("House cleaner")
                list.add("Laundry helper")
                list.add("Organizing assistant")
            }

            "Creative Services" -> {
                list.clear()
                list.add("Photography assistant")
                list.add("Videography assistant")
            }

            "Gardening" -> {
                list.clear()
                list.add("Gardening helper")
                list.add("Lawn care assistant")
            }

            "Healthcare Support" -> {
                list.clear()
                list.add("Medical office assistant")
                list.add("Healthcare facility support")
            }

            "Language and Translation" -> {
                list.clear()
                list.add("Language tutor")
                list.add("Translator assistant")
            }

            "Hospitality and Food Service" -> {
                list.clear()
                list.add("Waitstaff")
                list.add("Bartender")
                list.add("Barista")
                list.add("Host/hostess")
            }

            "Babysitting" -> {
                list.clear()
                list.add("Babysitter")
                list.add("Nanny")
                list.add("After-school caregiver")
            }

        }


        val adp1: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, list
        )
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        role?.adapter = adp1

    }
}