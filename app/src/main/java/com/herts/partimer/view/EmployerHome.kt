package com.herts.partimer.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.herts.partimer.R
import com.herts.partimer.response.StudentProfileList
import com.herts.partimer.utils.Navigator
import com.herts.partimer.view.adapter.ApplicantAdapter
import com.herts.partimer.viewmodel.JobViewModel

class EmployerHome : AppCompatActivity(), ApplicantAdapter.HomeListener {

    private lateinit var adapter: ApplicantAdapter
    var rv_applicant: RecyclerView? = null
    private lateinit var vm: JobViewModel
    private var listRecommended = ArrayList<StudentProfileList>()
    private var listNotRecommended = ArrayList<StudentProfileList>()

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, EmployerHome::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_home)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        vm = ViewModelProvider(this)[JobViewModel::class.java]

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            Navigator.navigateToAddJobPost(this)
        }
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val userID = sharedPref.getInt("USER_ID", 1)
        initAdapter()
        callApi(userID)
        val simpleSwitch = findViewById<SwitchCompat>(R.id.simpleSwitch)
        simpleSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                adapter.setData(listRecommended)
            } else {
                adapter.setData(listNotRecommended)
            }
        }
    }

    private fun initAdapter() {
        adapter = ApplicantAdapter(this)
        rv_applicant = findViewById<RecyclerView>(R.id.rv_applicant)
        rv_applicant?.layoutManager = LinearLayoutManager(this)
        rv_applicant?.adapter = adapter

//        adapter.setData(list)
    }

    private fun callApi(userID: Int) {

        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")
        vm.fetchStudentProfiles(token.toString(), userID)
        vm.fetchStudentsLiveData?.observe(this, Observer {
            if (it != null) {
                if (it.studentProfileList != null && it.allStudentProfileList != null) {
                    adapter.setData(it.studentProfileList as ArrayList<StudentProfileList>)
                    listRecommended = it.studentProfileList as ArrayList<StudentProfileList>
                    listNotRecommended = it.allStudentProfileList as ArrayList<StudentProfileList>

                } else
                    showToast("No Data found")
            } else {
                showToast("Something went wrong")
            }
        })

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(postModel: StudentProfileList, position: Int) {
        Navigator.navigateToApplicantDetails(this, postModel)
    }
}