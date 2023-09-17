package com.herts.partimer.utils

import android.content.Context
import com.herts.partimer.request.Cat
import com.herts.partimer.request.Category
import com.herts.partimer.response.JobProfileList
import com.herts.partimer.response.StudentProfileList
import com.herts.partimer.view.AddJob
import com.herts.partimer.view.ApplicantDetails
import com.herts.partimer.view.EmployerHome
import com.herts.partimer.view.JobDetails
import com.herts.partimer.view.Login
import com.herts.partimer.view.Register
import com.herts.partimer.view.Role
import com.herts.partimer.view.Skills
import com.herts.partimer.view.StudentHome
import com.herts.partimer.view.StudentMoreDetails
import com.herts.partimer.view.StudentProfile
import com.herts.partimer.view.VerifyStudentDetails
import com.herts.partimer.view.VerifyStudentId

object Navigator {
    fun navigateToLoginActivity(context: Context) {
        context.startActivity(Login.getCallingIntent(context))
    }

    fun navigateToRegister(context: Context) {
        context.startActivity(Register.getCallingIntent(context))
    }

    fun navigateToRole(context: Context) {
        context.startActivity(Role.getCallingIntent(context))
    }

    fun navigateToEmployer(context: Context) {
        context.startActivity(EmployerHome.getCallingIntent(context))
    }

    fun navigateToAddJobPost(context: Context) {
        context.startActivity(AddJob.getCallingIntent(context))
    }

    fun navigateToStudentHome(context: Context) {
        context.startActivity(StudentHome.getCallingIntent(context))
    }

    fun navigateToApplicantDetails(context: Context, postModel: StudentProfileList) {
        context.startActivity(ApplicantDetails.getCallingIntent(context, postModel))
    }

    fun navigateToStudentProfile(context: Context) {
        context.startActivity(StudentProfile.getCallingIntent(context))
    }


    fun navigateToStudentSkills(context: Context, updatedList: ArrayList<Category>) {
        context.startActivity(Skills.getCallingIntent(context, updatedList))
    }

    fun navigateToStudentMoreDetails(context: Context, updatedList: ArrayList<Cat>) {
        context.startActivity(StudentMoreDetails.getCallingIntent(context, updatedList))
    }

    fun navigateToVerifyEmail(context: Context) {
        context.startActivity(VerifyStudentDetails.getCallingIntent(context))
    }

    fun navigateToVerifyStudentId(context: Context) {
        context.startActivity(VerifyStudentId.getCallingIntent(context))
    }

    fun navigateToJobDetails(context: Context, postModel: JobProfileList) {
        context.startActivity(JobDetails.getCallingIntent(context, postModel))
    }

}