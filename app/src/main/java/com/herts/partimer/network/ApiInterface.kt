package com.herts.partimer.network


import com.herts.partimer.request.AddJobApproachRequest
import com.herts.partimer.request.AddJobRequest
import com.herts.partimer.request.AddStudentApproachRequest
import com.herts.partimer.request.AddStudentProfile
import com.herts.partimer.request.SendCodeRequest
import com.herts.partimer.request.SignInRequest
import com.herts.partimer.request.SignupRequest
import com.herts.partimer.response.AddJobResponse
import com.herts.partimer.response.JobsByCategoryResponse
import com.herts.partimer.response.SendCodeResponse
import com.herts.partimer.response.SignInResponse
import com.herts.partimer.response.SignupResponse
import com.herts.partimer.response.StudentsByCategoryResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("login")
    fun login(@Body signInRequest: SignInRequest): Call<SignInResponse>

    @POST("addUser")
    fun doSignUp(@Body signUpRequest: SignupRequest): Call<SignupResponse>

    @POST("addJobProfile")
    fun addJob(
        @Header("Authorization") token: String,
        @Body addJobRequest: AddJobRequest
    ): Call<AddJobResponse>

    @POST("sendVerificationCode")
    fun sendCode(
        @Header("Authorization") token: String,
        @Query("userId") userId: Int): Call<SendCodeResponse>

    @POST("verifyCode")
    fun verifyCode(
        @Header("Authorization") token: String,
        @Query("userId") userId: Int,
        @Query("code") code: String
    ): Call<SendCodeResponse>

    @POST("addStudentProfile")
    fun addStudentProfile(
        @Header("Authorization") token: String,
        @Body addStudentProfile: AddStudentProfile): Call<SendCodeResponse>

    @POST("findJobByCategory")
    fun fetchJobs(
        @Header("Authorization") token: String,
        @Query("userId") userId: Int): Call<JobsByCategoryResponse>

    @POST("findStudentByCategory")
    fun fetchStudents(
        @Header("Authorization") token: String,
        @Query("userId") userId: Int
    ): Call<StudentsByCategoryResponse>

    @POST("addApproachStudent")
    fun addStudentApproach(
        @Header("Authorization") token: String,
        @Body addStudentApproachRequest: AddStudentApproachRequest): Call<SignupResponse>

    @POST("addApproachJob")
    fun addApproachJob(
        @Header("Authorization") token: String,
        @Body addJobApproachRequest: AddJobApproachRequest): Call<SignupResponse>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<String>

    @Multipart
    @POST("updateProfilePicture")
    fun addPhotos(
        @Header("Authorization") token: String,
        @Part("id") id: Int,
        @Part img1: MultipartBody.Part?
    ): Call<SignupResponse>

}