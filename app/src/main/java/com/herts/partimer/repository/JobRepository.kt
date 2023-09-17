package com.herts.partimer.repository

import android.util.JsonToken
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.partimer.network.ApiClient
import com.herts.partimer.network.ApiInterface
import com.herts.partimer.request.AddJobRequest
import com.herts.partimer.request.AddStudentApproachRequest
import com.herts.partimer.response.AddJobResponse
import com.herts.partimer.response.JobsByCategoryResponse
import com.herts.partimer.response.SignupResponse
import com.herts.partimer.response.StudentsByCategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobRepository {

    private var apiInterface: ApiInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }


    fun addJob(token: String,addJobRequest: AddJobRequest): LiveData<AddJobResponse> {
        val data = MutableLiveData<AddJobResponse>()

        apiInterface?.addJob(token, addJobRequest)?.enqueue(object : Callback<AddJobResponse> {
            override fun onFailure(call: Call<AddJobResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<AddJobResponse>,
                response: Response<AddJobResponse>
            ) {
                val res = response.body()
                if (response.code() == 200 && res != null) {
                    data.value = res
                    Log.d("res", res.id.toString())
                } else {
                    data.value = null
                }
            }
        })

        return data

    }

    fun fetchStudents(token: String,userID: Int): LiveData<StudentsByCategoryResponse> {
        val data = MutableLiveData<StudentsByCategoryResponse>()

        apiInterface?.fetchStudents(token,userID)?.enqueue(object : Callback<StudentsByCategoryResponse> {
            override fun onFailure(call: Call<StudentsByCategoryResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<StudentsByCategoryResponse>,
                response: Response<StudentsByCategoryResponse>
            ) {
                val res = response.body()
                if (response.code() == 200 && res != null) {
                    data.value = res
                    Log.d("res", res.id.toString())
                } else {
                    data.value = null
                }
            }
        })

        return data

    }

    fun addStudentApproach(token: String,addStudentApproachRequest: AddStudentApproachRequest): LiveData<SignupResponse> {
        val data = MutableLiveData<SignupResponse>()

        apiInterface?.addStudentApproach(token,addStudentApproachRequest)?.enqueue(object : Callback<SignupResponse> {
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                val res = response.body()
                if (response.code() == 200 && res != null) {
                    data.value = res
                    Log.d("res", res.id.toString())
                } else {
                    data.value = null
                }
            }
        })

        return data

    }

}