package com.herts.partimer.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.herts.partimer.R
import com.herts.partimer.request.SendCodeRequest
import com.herts.partimer.utils.Navigator
import com.herts.partimer.viewmodel.StudentViewModel
import com.mukeshsolanki.OtpView
import java.text.DecimalFormat
import java.text.NumberFormat

class VerifyStudentDetails : AppCompatActivity() {
    private lateinit var vm: StudentViewModel
    var email: EditText? = null
    var sendCode: LinearLayout? = null
    var verifyCode: RelativeLayout? = null
    var tCount = 1
    var txt_timer: TextView? = null
    var txt_resend: TextView? = null
    var userID = 1
    var token = ""

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, VerifyStudentDetails::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_student_details)

        vm = ViewModelProvider(this)[StudentViewModel::class.java]
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        userID = sharedPref.getInt("USER_ID", 1)
        token = sharedPref.getString("TOKEN", "").toString()
        val save = findViewById<LinearLayout>(R.id.book_now)
        email = findViewById<EditText>(R.id.et_email)
        save.setOnClickListener {
            if (sendCode?.visibility == View.VISIBLE) {
                if (email?.text.toString().isNotEmpty())
                    callApi()
            } else {
                callVerifyCodeApi()
            }
//
        }
        val skip = findViewById<LinearLayout>(R.id.change_package)
        skip.setOnClickListener {
            Navigator.navigateToStudentHome(this)
        }

        sendCode = findViewById(R.id.sendCode)
        verifyCode = findViewById(R.id.verifyCode)
        txt_timer = findViewById(R.id.txt_timer)
        txt_resend = findViewById(R.id.txt_resend)
    }

    private fun callVerifyCodeApi() {
        val stopPort = findViewById<OtpView>(R.id.otp_view);
        vm.verifyCode(token, userID, stopPort.text.toString())
        vm.verifyCodeLiveData?.observe(this, Observer {
            if (it != null) {
                showToast(it.responseDescription.toString())
                Navigator.navigateToStudentHome(this)
            } else {
                showToast("Incorrect code")
            }

        })

    }

    private fun callApi() {

        val sendCodeReq = SendCodeRequest()
        sendCodeReq.userId = userID
        sendCodeReq.emailAddress = email?.text.toString()
        vm.sendCode(token, sendCodeReq)

        vm.createPostLiveData?.observe(this, Observer {
            if (it != null) {
                showToast("Verification code sent successfully")
                sendCode?.visibility = View.GONE
                verifyCode?.visibility = View.VISIBLE
                setTimer()

            } else {
                showToast("Cannot send code at the moment")
            }

        })
    }

    private lateinit var timer: CountDownTimer
    private fun setTimer() {
        if (tCount < 3) {
            timer = object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // Used for formatting digit to be in 2 digits only
                    val f: NumberFormat = DecimalFormat("00")
                    val hour = millisUntilFinished / 3600000 % 24
                    val min = millisUntilFinished / 60000 % 60
                    val sec = millisUntilFinished / 1000 % 60
//                tv_timer.setText(
//                    f.format(hour).toString() + ":" + f.format(min) + ":" + f.format(
//                        sec
//                    )
                    txt_timer?.setText(f.format(min) + ":" + f.format(sec))
                }

                // When the task is over it will print 00:00:00 there
                override fun onFinish() {
                    if (tCount == 2) {
                        txt_resend?.visibility = View.GONE
                        txt_timer?.visibility = View.GONE
                    } else {
                        tCount++
                        txt_timer?.setText("00:00")
                        txt_resend?.visibility = View.VISIBLE
                        txt_timer?.visibility = View.GONE
                    }

                }
            }
            timer.start()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}