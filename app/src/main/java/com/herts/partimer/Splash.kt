package com.herts.partimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.herts.partimer.utils.Constant
import com.herts.partimer.utils.Navigator

class Splash : AppCompatActivity() {

    private var isLoggedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        isLoggedIn = SharedPrefsHelper.getUserLoginSuccess("").equals(Constant.LOGIN)

        Handler().postDelayed({
            if (isLoggedIn) {
                Navigator.navigateToLoginActivity(this)
            } else {
                Navigator.navigateToLoginActivity(this)
            }
            finish()
        }, 2000)
    }
}