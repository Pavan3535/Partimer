package com.herts.partimer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.herts.partimer.R
import com.herts.partimer.request.Experience
import com.herts.partimer.request.Reference

class AddReference : AppCompatActivity() {
    var ref_name: EditText? = null
    var ref_email: EditText? = null
    var ref_relation: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reference)
        initViews()
    }

    private fun initViews() {
        ref_name = findViewById<EditText>(R.id.ref_name)
        ref_email = findViewById<EditText>(R.id.ref_email)
        ref_relation = findViewById<EditText>(R.id.ref_relation)

        val btn_save_ref = findViewById<Button>(R.id.btn_save_ref)
        btn_save_ref.setOnClickListener {

            val ref = Reference()
            ref.name = ref_name?.text.toString()
            ref.email = ref_email?.text.toString()
            ref.relation = ref_relation?.text.toString()

            val intent = Intent()
            intent.putExtra("ref", ref)
            this.setResult(RESULT_OK, intent)
            finish()

        }
    }
}