package com.betterlyf.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.InputType
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.activity_name_edit.*

class NameEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_edit)
        clickListener()

        edt_name.hint = intent.extras.getString("value")
        inp_name.hint = intent.extras.getString("value")
        edt_name.setText(intent.extras.getString("result"))

        when {
            intent.extras.getString("value") == "Name" -> {
                txt_update.text = "Update Name"
            }
            intent.extras.getString("value") == "Email" -> {
                txt_update.text = "Update Email"
            }
            else -> {
                txt_update.text = "Update Mobile"
                edt_name.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
        edt_name.isFocusable = true
        edt_name.requestFocus()

    }

    private fun clickListener() {
        img_back.setOnClickListener {
            onBackPressed()
        }
    }
}