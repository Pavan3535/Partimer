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
import com.herts.partimer.response.JobProfileList
import com.herts.partimer.response.StudentProfileList
import com.herts.partimer.utils.Navigator
import com.herts.partimer.view.adapter.JobsByCategoryAdapter
import com.herts.partimer.viewmodel.StudentViewModel

class StudentHome : AppCompatActivity(), JobsByCategoryAdapter.HomeListener {

    private lateinit var vm: StudentViewModel
    private lateinit var adapter: JobsByCategoryAdapter
    private var listRecommended = ArrayList<JobProfileList>()
    private var listNotRecommended = ArrayList<JobProfileList>()

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, StudentHome::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        vm = ViewModelProvider(this)[StudentViewModel::class.java]

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            Navigator.navigateToStudentProfile(this)
        }
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val userID = sharedPref.getInt("USER_ID", 1)
        val token = sharedPref.getString("TOKEN", "")
        initAdapter()
        callApi(token, userID)
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
        var jobList = findViewById<RecyclerView>(R.id.jobList)
        adapter = JobsByCategoryAdapter(this)
        jobList?.layoutManager = LinearLayoutManager(this)
        jobList?.adapter = adapter
    }

    private fun callApi(token: String?, userID: Int) {

        vm.fetchJobs(token.toString(), userID)
        vm.fetchJobsLiveData?.observe(this, Observer {
            if (it != null) {
                if (it.jobProfileList != null && it.allJobProfileList != null) {
                    adapter.setData(it.jobProfileList as ArrayList<JobProfileList>)
                    listRecommended = it.jobProfileList as ArrayList<JobProfileList>
                    listNotRecommended = it.allJobProfileList as ArrayList<JobProfileList>
                } else
                    showToast("No Cars found")
            } else {
                showToast("Something went wrong")
            }
        })

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemView(postModel: JobProfileList, position: Int) {
        Navigator.navigateToJobDetails(this, postModel)
    }
}