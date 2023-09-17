package com.herts.partimer.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.herts.partimer.R
import com.herts.partimer.utils.Navigator

class Role : AppCompatActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, Role::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)

        val btnEmployer = findViewById<Button>(R.id.btn_bissness)
        val btnPartTimer = findViewById<Button>(R.id.btn_partimer)
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        btnEmployer.setOnClickListener {
            with(sharedPref.edit()) {
                putInt("ROLE", 1)
                apply()
            }
            Navigator.navigateToRegister(this)
        }

        btnPartTimer.setOnClickListener {
            with(sharedPref.edit()) {
                putInt("ROLE", 2)
                apply()
            }
            Navigator.navigateToRegister(this)
        }
    }
}