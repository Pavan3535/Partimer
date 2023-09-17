package com.herts.partimer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.partimer.repository.JobRepository
import com.herts.partimer.request.AddJobRequest
import com.herts.partimer.request.AddStudentApproachRequest
import com.herts.partimer.response.AddJobResponse
import com.herts.partimer.response.SignupResponse
import com.herts.partimer.response.StudentsByCategoryResponse

class JobViewModel(application: Application) : AndroidViewModel(application) {

    private var jobRepository: JobRepository? = null
    var createPostLiveData: LiveData<AddJobResponse>? = null
    var fetchStudentsLiveData: LiveData<StudentsByCategoryResponse>? = null
    var addStudentApproachLiveData: LiveData<SignupResponse>? = null

    init {
        jobRepository = JobRepository()
        createPostLiveData = MutableLiveData()
        fetchStudentsLiveData =MutableLiveData()

    }

    fun addJob(token:String, addJobRequest: AddJobRequest) {
        createPostLiveData = jobRepository?.addJob(token,addJobRequest)
    }

    fun fetchStudentProfiles(token:String,userID: Int) {
        fetchStudentsLiveData = jobRepository?.fetchStudents(token,userID)
    }

    fun addStudentApproach(token: String, addStudentApproachRequest: AddStudentApproachRequest) {
        addStudentApproachLiveData = jobRepository?.addStudentApproach(token,addStudentApproachRequest)
    }
}