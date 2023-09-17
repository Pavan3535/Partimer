package com.herts.partimer.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.partimer.network.ApiClient
import com.herts.partimer.network.ApiInterface
import com.herts.partimer.request.AddJobApproachRequest
import com.herts.partimer.request.AddStudentApproachRequest
import com.herts.partimer.request.AddStudentProfile
import com.herts.partimer.request.SendCodeRequest
import com.herts.partimer.response.JobsByCategoryResponse
import com.herts.partimer.response.SendCodeResponse
import com.herts.partimer.response.SignupResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentRepository {

    private var apiInterface: ApiInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }


    fun sendCode(token: String, sendCodeRequest: SendCodeRequest): LiveData<SendCodeResponse> {
        val data = MutableLiveData<SendCodeResponse>()

        apiInterface?.sendCode(token, sendCodeRequest.userId)
            ?.enqueue(object : Callback<SendCodeResponse> {
                override fun onFailure(call: Call<SendCodeResponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<SendCodeResponse>,
                    response: Response<SendCodeResponse>
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

    fun verifyCode(token: String, userID: Int, code: String): LiveData<SendCodeResponse> {
        val data = MutableLiveData<SendCodeResponse>()

        apiInterface?.verifyCode(token, userID, code)?.enqueue(object : Callback<SendCodeResponse> {
            override fun onFailure(call: Call<SendCodeResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<SendCodeResponse>,
                response: Response<SendCodeResponse>
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

    fun addStudentProfile(
        token: String,
        addStudentProfile: AddStudentProfile
    ): LiveData<SendCodeResponse> {
        val data = MutableLiveData<SendCodeResponse>()

        apiInterface?.addStudentProfile(token, addStudentProfile)
            ?.enqueue(object : Callback<SendCodeResponse> {
                override fun onFailure(call: Call<SendCodeResponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<SendCodeResponse>,
                    response: Response<SendCodeResponse>
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

    fun fetchJobs(token:String,userID: Int): LiveData<JobsByCategoryResponse> {
        val data = MutableLiveData<JobsByCategoryResponse>()

        apiInterface?.fetchJobs(token,userID)?.enqueue(object : Callback<JobsByCategoryResponse> {
            override fun onFailure(call: Call<JobsByCategoryResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<JobsByCategoryResponse>,
                response: Response<JobsByCategoryResponse>
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

    fun addPhotos(
        token:String,
        i: Int,
        body1: MultipartBody.Part
    ): LiveData<SignupResponse> {
        val data = MutableLiveData<SignupResponse>()

        apiInterface?.addPhotos(token,i, body1)
            ?.enqueue(object : Callback<SignupResponse> {
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

    fun addJobApproach(token: String,addJobApproachRequest: AddJobApproachRequest): LiveData<SignupResponse> {
        val data = MutableLiveData<SignupResponse>()

        apiInterface?.addApproachJob(token,addJobApproachRequest)?.enqueue(object : Callback<SignupResponse> {
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