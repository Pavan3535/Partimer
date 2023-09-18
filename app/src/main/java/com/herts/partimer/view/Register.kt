package com.herts.partimer.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.herts.partimer.R
import com.herts.partimer.request.SignupRequest
import com.herts.partimer.utils.Navigator
import com.herts.partimer.viewmodel.LoginViewModel

class Register : AppCompatActivity() {

    private lateinit var vm: LoginViewModel
    var email_address: EditText? = null
    var name: EditText? = null
    var last_name: EditText? = null
    var etPassword: EditText? = null
    var business_name: EditText? = null

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, Register::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        vm = ViewModelProvider(this)[LoginViewModel::class.java]
        makeTermsPolicyClickable()
        val button: Button = findViewById(R.id.email_sign_in_button)
        val login: TextView = findViewById(R.id.alreadyAccount)
        button.setOnClickListener {
            if (checkValidations())
                callApi()
            else
                showToast("Enter correct details!")
//            Navigator.navigateToStudentHome(this)
        }
        login.setOnClickListener {
            Navigator.navigateToLoginActivity(this)
        }
    }

    private fun makeTermsPolicyClickable() {
        val termsp = findViewById<TextView>(R.id.text_view_term_and_services)
        var text = termsp.text.toString()

        var spannableString = SpannableString(text)
        var clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                callTermsDialog(1)
            }
        }

        spannableString.setSpan(clickableSpan, 10, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    baseContext,
                    R.color.accent
                )
            ), 10, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        var clickablePrivacy = object : ClickableSpan() {
            override fun onClick(widget: View) {
                callTermsDialog(2)
            }
        }

        spannableString.setSpan(clickablePrivacy, 32, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    baseContext,
                    R.color.accent
                )
            ), 32, 47, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        termsp.setText(spannableString)

        termsp.movementMethod = LinkMovementMethod.getInstance()

        termsp.highlightColor = Color.TRANSPARENT
    }

    private fun callTermsDialog(i: Int) {
        Navigator.navigateToPrivacy(this, i)

    }

    private fun callApi() {

        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val role = sharedPref.getInt("ROLE", 2)
        val mSignupRequest = SignupRequest()
        mSignupRequest.firstName = name?.text.toString()
        mSignupRequest.lastName = last_name?.text.toString()
        mSignupRequest.password = etPassword?.text.toString()
        mSignupRequest.email = email_address?.text.toString()
        mSignupRequest.roleId = role

        vm.createUser(mSignupRequest)

        vm.createUserLiveData?.observe(this, Observer {
            if (it != null) {
                showToast("User registered successfully!")
                Navigator.navigateToLoginActivity(this)
            } else {
                showToast("Cannot create post at the moment")
            }

        })

    }


    private fun checkValidations(): Boolean {

        email_address = findViewById<EditText>(R.id.email)
        name = findViewById<EditText>(R.id.first_name)
        last_name = findViewById<EditText>(R.id.last_name)
        etPassword = findViewById<EditText>(R.id.password)
        business_name = findViewById<EditText>(R.id.buissness_name)

        var flag: Boolean = true
        if (email_address?.text.isNullOrEmpty() || !vm.isEmailValid(email_address?.text.toString())) {
            email_address?.requestFocus()
            showToast("Enter valid email address")
            flag = false
        }
        if (name?.text.isNullOrEmpty()) {
            name?.requestFocus()
            showToast("Enter first name")
            flag = false
        }
        if (last_name?.text.isNullOrEmpty()) {
            last_name?.requestFocus()
            showToast("Enter last name")
            flag = false
        }
        if (etPassword?.text.isNullOrEmpty()) {
            etPassword?.requestFocus()
            showToast("Enter password")
            flag = false
        }
        if (!vm.isPasswordValid(etPassword?.text.toString())) {
            showToast("Enter 8-20 characters, 1 Uppercase, 1 lowercase and 1 Number")
            flag = false

        }
        return flag
    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}