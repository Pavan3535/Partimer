package com.herts.partimer.view


import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.herts.partimer.R
import com.herts.partimer.request.AddJobRequest
import com.herts.partimer.request.Week
import com.herts.partimer.utils.Navigator
import com.herts.partimer.view.adapter.WeekAdapter
import com.herts.partimer.viewmodel.JobViewModel


class AddJob : AppCompatActivity(), WeekAdapter.HomeListener {

    private lateinit var adapter: WeekAdapter
    private lateinit var vm: JobViewModel

    var jobtitle: EditText? = null
    var category: Spinner? = null
    var role: Spinner? = null
    var city: Spinner? = null
    var wage: EditText? = null
    var reference: RadioGroup? = null
    var rvDay: RecyclerView? = null
    var experienceTv: TextView? = null
    var shortN: Boolean = false
    var referenceB = false

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, AddJob::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job)
        vm = ViewModelProvider(this)[JobViewModel::class.java]
        initViews()
        val postJob = findViewById<Button>(R.id.btn_post)
        postJob.setOnClickListener {
            if (isCredentialsValid()) {
                callApi()
            } else
                showToast("Enter correct details!")
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


        /* val selectedId: Int? = reference?.checkedRadioButtonId
         val radioButton = findViewById<RadioButton>(selectedId!!)
         referenceB = radioButton.text.toString() == "Yes"*/

        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val userID = sharedPref.getInt("USER_ID", 1)
        val token = sharedPref.getString("TOKEN", "")
        val addJobRequest = AddJobRequest()
        addJobRequest.userId = userID
        addJobRequest.jobTitle = jobtitle?.text.toString()
        addJobRequest.category = category?.selectedItem.toString()
        addJobRequest.role = role?.selectedItem.toString()
        addJobRequest.city = city?.selectedItem.toString()
        addJobRequest.experience = experienceTv?.text.toString().toInt()
        addJobRequest.payPerHour = wage?.text.toString().toDouble()
        addJobRequest.monday = monday
        addJobRequest.tuesday = tuesday
        addJobRequest.wednesday = wednesday
        addJobRequest.thursday = thursday
        addJobRequest.friday = friday
        addJobRequest.saturday = saturday
        addJobRequest.sunday = sunday
        addJobRequest.jobReferences = referenceB
        addJobRequest.immediateJoining = shortN
        addJobRequest.verifiedEmail = true
        addJobRequest.profilePicture = true


        vm.addJob(token.toString(), addJobRequest)

        vm.createPostLiveData?.observe(this, Observer {
            if (it.id != null) {
                Navigator.navigateToEmployer(this)
                showToast("Job Post added successfully!")
            } else {
                showToast("Cannot add job at the moment")
            }

        })

    }

    private fun isCredentialsValid(): Boolean {
        var flag: Boolean = true

        if (jobtitle?.text.isNullOrEmpty()) {
            showToast("Enter Job title")
            flag = false
        } else if (category?.selectedItem.toString() == "Select Job Category") {
            showToast("Select Job Category")
            flag = false
        } else if (role?.selectedItem.toString() == "Select Role") {
            showToast("Select Job Role")
            flag = false
        } else if (city?.selectedItem == null) {
            showToast("Select City")
            flag = false
        } else if (wage?.text.isNullOrEmpty()) {
            showToast("Enter Wages per hour")
            flag = false
        } else if (updatedList.size < 1) {
            showToast("Select at least one availability")
            flag = false
        }

        return flag

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    private fun initViews() {
        var experienceCount: Int = 0
        jobtitle = findViewById<EditText>(R.id.et_job_title)
        category = findViewById<Spinner>(R.id.spinner_cat)
        role = findViewById<Spinner>(R.id.spinner_role)
        city = findViewById<Spinner>(R.id.spinner_city)
        wage = findViewById(R.id.et_price)
        reference = findViewById(R.id.rg_ref)
        rvDay = findViewById(R.id.rv_day)
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

        val yourArray = resources.getStringArray(R.array.job_category)

        val dataAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yourArray)
        role?.adapter = dataAdapter

        reference?.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group.findViewById<View>(checkedId) as RadioButton
            val isChecked = checkedRadioButton.isChecked
            if (isChecked) {
                // Changes the textview's text to "Checked: example radiobutton text"
                referenceB = checkedRadioButton.text.toString() == "Yes"
//                showToast(referenceB.toString())
            }
        }

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


        val list = ArrayList<Week>()
        list.add(day1)
        list.add(day2)
        list.add(day3)
        list.add(day4)
        list.add(day5)
        list.add(day6)
        list.add(day7)

        adapter = WeekAdapter(this)
        rvDay?.layoutManager = LinearLayoutManager(this)
        rvDay?.adapter = adapter

        adapter.setData(list)

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

    var updatedList = ArrayList<Week>()

    override fun onDayClicked(week: Week, position: Int) {
        if (week.day == 0) {
            week.day = 1
            updatedList.add(week)
        } else {
            week.day = 0
            updatedList.remove(week)
        }
        adapter.notifyItemChanged(position)

    }

    override fun onNightClicked(week: Week, position: Int) {
        if (week.night == 0) {
            week.night = 1
            updatedList.add(week)
        } else {
            week.night = 0
            updatedList.remove(week)
        }
        adapter.notifyItemChanged(position)
    }
}