package com.herts.partimer.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.herts.partimer.R
import com.herts.partimer.request.AddJobRequest
import com.herts.partimer.request.AddStudentProfile
import com.herts.partimer.request.Cat
import com.herts.partimer.request.Category
import com.herts.partimer.request.Experience
import com.herts.partimer.request.Reference
import com.herts.partimer.request.Week
import com.herts.partimer.utils.Navigator
import com.herts.partimer.view.adapter.ExpAdapter
import com.herts.partimer.view.adapter.RefAdapter
import com.herts.partimer.view.adapter.WeekAdapter
import com.herts.partimer.viewmodel.StudentViewModel

class StudentMoreDetails : AppCompatActivity(), WeekAdapter.HomeListener {

    var categoryList: ArrayList<Cat>? = null
    var city: Spinner? = null
    var rvDay: RecyclerView? = null
    var shortN: Boolean = false
    private lateinit var vm: StudentViewModel

    private lateinit var adapter: ExpAdapter
    private lateinit var refAdapter: RefAdapter
    private lateinit var weekAdapter: WeekAdapter

    companion object {
        var CATEGORY: String = "CATEGORY"
        fun getCallingIntent(context: Context, updatedList: ArrayList<Cat>): Intent {
            val intent = Intent(context, StudentMoreDetails::class.java)
            intent.putExtra(CATEGORY, updatedList)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_more_details)
        vm = ViewModelProvider(this)[StudentViewModel::class.java]
        val bundle = intent.extras
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                categoryList =
                    intent.getSerializableExtra(CATEGORY, ArrayList<Cat>()::class.java)
            } else {
                categoryList = intent.getSerializableExtra(CATEGORY) as ArrayList<Cat>?
            }
        }
        updateViews()
    }

    val list = ArrayList<Experience>()
    val refList = ArrayList<Reference>()

    private fun updateViews() {
        city = findViewById<Spinner>(R.id.spinner_city)
        val add_exp = findViewById<TextView>(R.id.add_exp)
        add_exp.setOnClickListener {
            startForResult.launch(Intent(this, AddExperience::class.java))
        }

        val add_ref = findViewById<TextView>(R.id.add_ref)
        add_ref.setOnClickListener {
            startForRefResult.launch(Intent(this, AddReference::class.java))
        }

        val rv_exp = findViewById<RecyclerView>(R.id.rv_exp)
        adapter = ExpAdapter()
        rv_exp?.layoutManager = LinearLayoutManager(this)
        rv_exp?.adapter = adapter
        adapter.setData(list)

        val rv_ref = findViewById<RecyclerView>(R.id.rv_ref)
        refAdapter = RefAdapter()
        rv_ref?.layoutManager = LinearLayoutManager(this)
        rv_ref?.adapter = refAdapter
        refAdapter.setData(refList)

        rvDay = findViewById(R.id.rv_day)

        val shortNotice = findViewById<TextView>(R.id.tv_short_np)
        shortNotice.setOnClickListener {
            if (!shortN) {
                shortN = true
                shortNotice.compoundDrawableTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.color_bright_pink))

            } else {
                shortN = false
                shortNotice.compoundDrawableTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.background_grey))
            }
        }

        val day1 = Week()
        day1.dayId = 1
        day1.dayOfWeek = "Monday"
        val day2 = Week()
        day2.dayId = 2
        day2.dayOfWeek = "Tuesday"
        val day3 = Week()
        day3.dayId = 3
        day3.dayOfWeek = "Wednesday"
        val day4 = Week()
        day4.dayId = 4
        day4.dayOfWeek = "Thursday"
        val day5 = Week()
        day5.dayId = 5
        day5.dayOfWeek = "Friday"
        val day6 = Week()
        day6.dayId = 6
        day6.dayOfWeek = "Saturday"
        val day7 = Week()
        day7.dayId = 7
        day7.dayOfWeek = "Sunday"


        val list1 = ArrayList<Week>()
        list1.add(day1)
        list1.add(day2)
        list1.add(day3)
        list1.add(day4)
        list1.add(day5)
        list1.add(day6)
        list1.add(day7)

        weekAdapter = WeekAdapter(this)
        rvDay?.layoutManager = LinearLayoutManager(this)
        rvDay?.adapter = weekAdapter

        weekAdapter.setData(list1)

        val btn_save_more = findViewById<Button>(R.id.btn_save_more)
        btn_save_more.setOnClickListener {
            callApi()
//            Navigator.navigateToVerifyEmail(this)
        }

    }

    private fun callApi() {
        var monday = "0|0"
        var tuesday = "0|0"
        var wednesday = "0|0"
        var thursday = "0|0"
        var friday = "0|0"
        var saturday = "0|0"
        var sunday = "0|0"
        updatedList.forEach {
            if (it.dayOfWeek == "Monday") {
                monday = it.day.toString() + "|" + it.night
            }
            if (it.dayOfWeek == "Tuesday") {
                tuesday = it.day.toString() + "|" + it.night
            }
            if (it.dayOfWeek == "Wednesday") {
                wednesday = it.day.toString() + "|" + it.night
            }
            if (it.dayOfWeek == "Thursday") {
                thursday = it.day.toString() + "|" + it.night
            }
            if (it.dayOfWeek == "Friday") {
                friday = it.day.toString() + "|" + it.night
            }
            if (it.dayOfWeek == "Saturday") {
                saturday = it.day.toString() + "|" + it.night
            }
            if (it.dayOfWeek == "Sunday") {
                sunday = it.day.toString() + "|" + it.night
            }
        }

        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val userID = sharedPref.getInt("USER_ID", 1)
        val token = sharedPref.getString("TOKEN", "")
        val addStudentProfile = AddStudentProfile()
        addStudentProfile.userId = userID.toString()
        addStudentProfile.city = city?.selectedItem.toString()
        addStudentProfile.monday = monday
        addStudentProfile.tuesday = tuesday
        addStudentProfile.wednesday = wednesday
        addStudentProfile.thursday = thursday
        addStudentProfile.friday = friday
        addStudentProfile.saturday = saturday
        addStudentProfile.sunday = sunday
        addStudentProfile.immediateJoining = shortN
        addStudentProfile.categoryList = categoryList
        addStudentProfile.experienceList = list
        addStudentProfile.referenceList = refList
        vm.addStudentProfile(token.toString(), addStudentProfile)

        vm.addStudentProfileLiveData?.observe(this, Observer {
            if (it.id != null) {
                Navigator.navigateToVerifyEmail(this)
            } else {
                showToast("Cannot add job at the moment")
            }

        })
    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val experience = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent?.getSerializableExtra(
                        "exp", Experience::class.java
                    )
                } else {
                    intent?.extras?.getSerializable("exp") as com.herts.partimer.request.Experience
                }
                if (experience != null) {
                    list.add(experience)
                    val pos = list.size
                    adapter.notifyItemInserted(pos)
                }
                // Handle the Intent
            }
        }

    val startForRefResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val ref = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent?.getSerializableExtra(
                        "ref", Reference::class.java
                    )
                } else {
                    intent?.extras?.getSerializable("ref") as Reference
                }
                if (ref != null) {
                    refList.add(ref)
                    val pos = refList.size
                    refAdapter.notifyItemInserted(pos)
                }
                // Handle the Intent
            }
        }

    var updatedList = ArrayList<Week>()

    override fun onDayClicked(week: Week, position: Int) {
        if (week.day == 0) {
            week.day = 1
            updatedList.add(week)
        } else {
            week.day = 0
            updatedList.remove(week)
        }
        weekAdapter.notifyItemChanged(position)
    }

    override fun onNightClicked(week: Week, position: Int) {
        if (week.night == 0) {
            week.night = 1
            updatedList.add(week)
        } else {
            week.night = 0
            updatedList.remove(week)
        }
        weekAdapter.notifyItemChanged(position)
    }
}