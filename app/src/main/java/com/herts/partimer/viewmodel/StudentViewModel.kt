package com.herts.partimer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.partimer.repository.StudentRepository
import com.herts.partimer.request.AddJobApproachRequest
import com.herts.partimer.request.AddStudentApproachRequest
import com.herts.partimer.request.AddStudentProfile
import com.herts.partimer.request.SendCodeRequest
import com.herts.partimer.response.JobsByCategoryResponse
import com.herts.partimer.response.SendCodeResponse
import com.herts.partimer.response.SignupResponse
import okhttp3.MultipartBody

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private var studentRepository: StudentRepository? = null
    var createPostLiveData: LiveData<SendCodeResponse>? = null
    var verifyCodeLiveData: LiveData<SendCodeResponse>? = null
    var addStudentProfileLiveData: LiveData<SendCodeResponse>? = null
    var fetchJobsLiveData: LiveData<JobsByCategoryResponse>? = null
    var addPhotoLiveData: LiveData<SignupResponse>? = null
    var addJobApproachLiveData: LiveData<SignupResponse>? = null

    init {
        studentRepository = StudentRepository()
        createPostLiveData = MutableLiveData()
        verifyCodeLiveData = MutableLiveData()
        addStudentProfileLiveData = MutableLiveData()
        fetchJobsLiveData = MutableLiveData()
        addPhotoLiveData = MutableLiveData()
        addJobApproachLiveData = MutableLiveData()

    }

    fun sendCode(token: String, sendCodeRequest: SendCodeRequest) {
        createPostLiveData = studentRepository?.sendCode(token, sendCodeRequest)
    }

    fun verifyCode(token: String, userID: Int, toString: String) {
        verifyCodeLiveData = studentRepository?.verifyCode(token, userID, toString)
    }

    fun addStudentProfile(token: String, addStudentProfile: AddStudentProfile) {
        addStudentProfileLiveData = studentRepository?.addStudentProfile(token, addStudentProfile)
    }

    fun fetchJobs(token: String, userID: Int) {
        fetchJobsLiveData = studentRepository?.fetchJobs(token, userID)
    }

    fun addPhotos(
        token: String,
        i: Int,
        body1: MultipartBody.Part

    ) {
        addPhotoLiveData = studentRepository?.addPhotos(token, i, body1)
    }

    fun addJobApproach(token: String, addJobApproachRequest: AddJobApproachRequest) {
        addJobApproachLiveData = studentRepository?.addJobApproach(token, addJobApproachRequest)
    }
}