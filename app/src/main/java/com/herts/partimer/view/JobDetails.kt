package com.herts.partimer.view

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.herts.partimer.R
import com.herts.partimer.request.AddJobApproachRequest
import com.herts.partimer.request.AddStudentApproachRequest
import com.herts.partimer.request.Week
import com.herts.partimer.response.JobProfileList
import com.herts.partimer.utils.Navigator
import com.herts.partimer.view.adapter.WeekAdapter
import com.herts.partimer.viewmodel.StudentViewModel

class JobDetails : AppCompatActivity(), WeekAdapter.HomeListener {
    private lateinit var vm: StudentViewModel
    var dta: JobProfileList? = null
    private lateinit var weekAdapter: WeekAdapter

    companion object {
        var JOB_DETAILS: String = "JOB_DETAILS"
        fun getCallingIntent(context: Context, postModel: JobProfileList): Intent {
            val intent = Intent(context, JobDetails::class.java)
            intent.putExtra(JOB_DETAILS, postModel)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_details)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        val bundle = intent.extras
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                dta = intent.getSerializableExtra(JOB_DETAILS, JobProfileList::class.java)
            } else {
                dta = intent.getSerializableExtra(JOB_DETAILS) as JobProfileList
            }
        }

        val btn_apply = findViewById<Button>(R.id.btn_apply)
        btn_apply.setOnClickListener {
            callApproachApi()
        }

        setData()
    }

    private fun callApproachApi() {
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")
        val userID = sharedPref.getInt("USER_ID", 1)
        val addJobApproachRequest = AddJobApproachRequest()
        addJobApproachRequest.userId = userID
        addJobApproachRequest.jobProfileId = dta?.jobProfileId?.toInt()!!
        addJobApproachRequest.status = 0
        vm.addJobApproach(token.toString(), addJobApproachRequest)
        vm.addJobApproachLiveData?.observe(this, Observer {
            if (it != null) {
                if (it.id != null) {
                    Navigator.navigateToStudentHome(this)
                    showToast("Student approached successfully!")
                }
            } else {
                showToast("Something went wrong")
            }
        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun setData() {
        val jobTitle = findViewById<TextView>(R.id.user_name)
        val category = findViewById<TextView>(R.id.email)
        val role = findViewById<TextView>(R.id.university)
        val city = findViewById<TextView>(R.id.city)
        val percentage = findViewById<TextView>(R.id.percent)
        val wage = findViewById<TextView>(R.id.wage)
        val exp = findViewById<TextView>(R.id.rv_exp)
        val ref = findViewById<TextView>(R.id.ref_tv)
        val joining = findViewById<TextView>(R.id.joining)

        jobTitle.setText("Job Title: " + dta?.jobTitle)
        category.setText("Category: " + dta?.category)
        role.setText("Role: " + dta?.role)
        city.setText("City: " + dta?.city)
        percentage.setText(
            dta?.matchingObject?.percentage?.percentage.toString().substringBefore(".") + "%"
        )
        wage.setText("£ " + dta?.payPerHour.toString())
        exp.setText(dta?.experience.toString())
        if (dta?.jobReferences == true) {
            ref.setText("References required : Yes")
        } else {
            ref.setText("References required : No")
        }

        if (dta?.immediateJoining == true) {
            joining.setText("Immediate joining required: Yes")
        } else {
            joining.setText("Immediate joining not required: No")
        }




        val rv_day = findViewById<RecyclerView>(R.id.rv_day)
        weekAdapter = WeekAdapter(this)
        rv_day?.layoutManager = LinearLayoutManager(this)
        rv_day?.adapter = weekAdapter

        val day1 = Week()
        day1.dayId = 1
        day1.dayOfWeek = "Monday"
        day1.day = dta?.monday?.substringBefore("|").toString().toInt()
        day1.night = dta?.monday?.substringAfter("|").toString().toInt()
        val day2 = Week()
        day2.dayId = 2
        day2.dayOfWeek = "Tuesday"
        day2.day = dta?.tuesday?.substringBefore("|").toString().toInt()
        day2.night = dta?.tuesday?.substringAfter("|").toString().toInt()
        val day3 = Week()
        day3.dayId = 3
        day3.dayOfWeek = "Wednesday"
        day3.day = dta?.wednesday?.substringBefore("|").toString().toInt()
        day3.night = dta?.wednesday?.substringAfter("|").toString().toInt()
        val day4 = Week()
        day4.dayId = 4
        day4.dayOfWeek = "Thursday"
        day4.day = dta?.thursday?.substringBefore("|").toString().toInt()
        day4.night = dta?.thursday?.substringAfter("|").toString().toInt()
        val day5 = Week()
        day5.dayId = 5
        day5.dayOfWeek = "Friday"
        day5.day = dta?.friday?.substringBefore("|").toString().toInt()
        day5.night = dta?.friday?.substringAfter("|").toString().toInt()
        val day6 = Week()
        day6.dayId = 6
        day6.dayOfWeek = "Saturday"
        day6.day = dta?.saturday?.substringBefore("|").toString().toInt()
        day6.night = dta?.saturday?.substringAfter("|").toString().toInt()
        val day7 = Week()
        day7.dayId = 7
        day7.dayOfWeek = "Sunday"
        day7.day = dta?.sunday?.substringBefore("|").toString().toInt()
        day7.night = dta?.sunday?.substringAfter("|").toString().toInt()


        val list1 = ArrayList<Week>()
        list1.add(day1)
        list1.add(day2)
        list1.add(day3)
        list1.add(day4)
        list1.add(day5)
        list1.add(day6)
        list1.add(day7)

        weekAdapter.setData(list1)

        val pp_p = findViewById<TextView>(R.id.pp_p)
        val ref_p = findViewById<TextView>(R.id.ref_p)
        val role_pp = findViewById<TextView>(R.id.role_pp)
        val city_p = findViewById<TextView>(R.id.city_p)
        val avail_pp = findViewById<TextView>(R.id.avail_pp)
        val exp_p = findViewById<TextView>(R.id.exp_p)
        val immed_p = findViewById<TextView>(R.id.immed_p)
        val email_p = findViewById<TextView>(R.id.email_p)

        pp_p.setText(dta?.matchingObject?.category?.percentage.toString().substringBefore(".") + "%")
        ref_p.setText(dta?.matchingObject?.reference?.percentage.toString().substringBefore(".") + "%")
        role_pp.setText(dta?.matchingObject?.role?.percentage.toString().substringBefore(".") + "%")
        city_p.setText(dta?.matchingObject?.city?.percentage.toString().substringBefore(".") + "%")
        avail_pp.setText(dta?.matchingObject?.availability?.percentage.toString().substringBefore(".") + "%")
        exp_p.setText(dta?.matchingObject?.experience?.percentage.toString().substringBefore(".") + "%")
        immed_p.setText(dta?.matchingObject?.immediateJoining?.percentage.toString().substringBefore(".") + "%")
        email_p.setText(dta?.matchingObject?.emailVerification?.percentage.toString().substringBefore(".") + "%")
    }

    override fun onDayClicked(postModel: Week, position: Int) {
        Log.e("", "")
    }

    override fun onNightClicked(postModel: Week, position: Int) {
        Log.e("", "")
    }
}