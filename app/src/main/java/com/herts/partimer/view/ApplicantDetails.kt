package com.herts.partimer.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.herts.partimer.R
import com.herts.partimer.request.AddStudentApproachRequest
import com.herts.partimer.request.Experience
import com.herts.partimer.request.Reference
import com.herts.partimer.request.Week
import com.herts.partimer.response.StudentProfileList
import com.herts.partimer.utils.Navigator
import com.herts.partimer.view.adapter.ExpAdapter
import com.herts.partimer.view.adapter.RefAdapter
import com.herts.partimer.view.adapter.WeekAdapter
import com.herts.partimer.viewmodel.JobViewModel

class ApplicantDetails : AppCompatActivity(), WeekAdapter.HomeListener {

    var dta: StudentProfileList? = null
    private lateinit var adapter: ExpAdapter
    private lateinit var refAdapter: RefAdapter
    private lateinit var weekAdapter: WeekAdapter
    private lateinit var vm: JobViewModel


    companion object {
        var STUDENT_DETAILS: String = "STUDENT_DETAILS"
        fun getCallingIntent(context: Context, postModel: StudentProfileList): Intent {
            val intent = Intent(context, ApplicantDetails::class.java)
            intent.putExtra(STUDENT_DETAILS, postModel)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_details)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        vm = ViewModelProvider(this)[JobViewModel::class.java]
        val bundle = intent.extras
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                dta = intent.getSerializableExtra(STUDENT_DETAILS, StudentProfileList::class.java)
            } else {
                dta = intent.getSerializableExtra(STUDENT_DETAILS) as StudentProfileList
            }
        }

        setData()
        val btnApproach = findViewById<Button>(R.id.btnApproach)
        btnApproach.setOnClickListener {
            callApproachApi()
        }
    }

    private fun callApproachApi() {
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")
        val userID = sharedPref.getInt("USER_ID", 1)
        val addStudentApproachRequest = AddStudentApproachRequest()
        addStudentApproachRequest.userId = userID
        addStudentApproachRequest.studentProfileId = dta?.studentProfileId?.toInt()!!
        addStudentApproachRequest.status = 0
        vm.addStudentApproach(token.toString(), addStudentApproachRequest)
        vm.addStudentApproachLiveData?.observe(this, Observer {
            if (it != null) {
                if (it.id != null) {
                    Navigator.navigateToEmployer(this)
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
        val user_name = findViewById<TextView>(R.id.user_name)
        val email = findViewById<TextView>(R.id.email)
        val university = findViewById<TextView>(R.id.university)
        val city = findViewById<TextView>(R.id.city)
        val percent = findViewById<TextView>(R.id.percent)

        val rv_exp = findViewById<RecyclerView>(R.id.rv_exp)
        val rv_ref = findViewById<RecyclerView>(R.id.rv_ref)
        val matching_list = findViewById<RecyclerView>(R.id.matching_list)

        user_name.setText(dta?.user?.first_name.toString() + " " + dta?.user?.last_name)
        email.setText(dta?.user?.email)
        university.setText("University of Hertfordshire")
        city.setText(dta?.city)
        percent.setText(
            dta?.matchingObject?.percentage?.percentage.toString().substringBefore(".") + "%"
        )

//        availability.setText(fString.toString())

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
        pp_p.setText(
            dta?.matchingObject?.category?.percentage.toString().substringBefore(".") + "%"
        )
        ref_p.setText(
            dta?.matchingObject?.reference?.percentage.toString().substringBefore(".") + "%"
        )
        role_pp.setText(dta?.matchingObject?.role?.percentage.toString().substringBefore(".") + "%")
        city_p.setText(dta?.matchingObject?.city?.percentage.toString().substringBefore(".") + "%")
        avail_pp.setText(
            dta?.matchingObject?.availability?.percentage.toString().substringBefore(".") + "%"
        )
        exp_p.setText(
            dta?.matchingObject?.experience?.percentage.toString().substringBefore(".") + "%"
        )
        immed_p.setText(
            dta?.matchingObject?.immediateJoining?.percentage.toString().substringBefore(".") + "%"
        )
        email_p.setText(
            dta?.matchingObject?.emailVerification?.percentage.toString().substringBefore(".") + "%"
        )

        val list = ArrayList<Experience>()
        dta?.experienceList?.forEach {
            val exp = Experience()
            exp.year = it.year?.toInt()!!
            exp.role = it.role.toString()
            exp.category = it.category.toString()
            exp.jobDescription = it.jobDescription.toString()
            list.add(exp)

        }
        adapter = ExpAdapter()
        rv_exp?.layoutManager = LinearLayoutManager(this)
        rv_exp?.adapter = adapter
        adapter.setData(list)


        val refList = ArrayList<Reference>()
        dta?.referenceList?.forEach {
            val ref = Reference()
            ref.relation = it.relation?.toString().toString()
            ref.name = it.name.toString()
            ref.email = it.email.toString()
            refList.add(ref)
        }

        refAdapter = RefAdapter()
        rv_ref?.layoutManager = LinearLayoutManager(this)
        rv_ref?.adapter = refAdapter
        refAdapter.setData(refList)
    }

    override fun onDayClicked(postModel: Week, position: Int) {
        Log.e("", "")
    }

    override fun onNightClicked(postModel: Week, position: Int) {
        Log.e("", "")
    }
}