package com.herts.partimer.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.herts.partimer.R
import com.herts.partimer.request.SignInRequest
import com.herts.partimer.utils.Navigator
import com.herts.partimer.viewmodel.LoginViewModel

class Login : AppCompatActivity() {


    var login_email: EditText? = null
    var login_password: EditText? = null
    private lateinit var vm: LoginViewModel

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, Login::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        vm = ViewModelProvider(this)[LoginViewModel::class.java]

        val signup = findViewById<TextView>(R.id.txt_view_dont_hv_acc)
        signup.setOnClickListener {
            Navigator.navigateToRole(this)
        }

        val signInButton = findViewById<Button>(R.id.email_sign_in_button)
        signInButton.setOnClickListener {
            if (checkValidations()) {
                callApi()
            }
//            Navigator.navigateToStudentHome(this)
        }
    }

    private fun callApi() {

        val mSignInRequest = SignInRequest()
        mSignInRequest.username = login_email?.text.toString()
        mSignInRequest.password = login_password?.text.toString()


//        vm.loginUser(mSignInRequest)
        vm.loginUser(mSignInRequest)

        vm.createPostLiveData?.observe(this, Observer {
            if (it != null) {
                if (it.user != null) {
                    val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putInt("USER_ID", it?.user?.userId?.toInt()!!)
                        apply()
                    }
                    val token = it.token
                    if (token != null) {
                        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("TOKEN", "Bearer "+ it?.token)
                            apply()
                        }

                    }
                    if (it?.user?.roleId == 1)
                        Navigator.navigateToEmployer(this)
                    else
                        Navigator.navigateToStudentHome(this)
                    showToast("Login successful!")
                } else {
                    showToast("Incorrect password!")
                }

            } else {
                showToast("Something went wrong! Try again later!")
            }

        })
    }

    private fun checkValidations(): Boolean {
        var flag: Boolean = true
        login_email = findViewById<EditText>(R.id.login_email)
        login_password = findViewById<EditText>(R.id.login_password)

        if (login_email?.text.isNullOrEmpty() || !vm.isEmailValid(login_email?.text.toString())) {
            login_email?.requestFocus()
            showToast("please enter email ID")
            flag = false
        }

        if (login_password?.text.toString().isNullOrEmpty()) {
            showToast("please enter password")
            flag = false
        } else if (!vm.isPasswordValid(login_password?.text.toString())) {
            showToast("please enter proper password")
            flag = false
        }

        return flag
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}