package com.betterlyf.app.ui.activity

import android.os.Bundle
import com.betterlyf.app.BaseActivity
import com.betterlyf.app.R
import com.fxn769.TextGetListner
import kotlinx.android.synthetic.main.activity_change_pin.*

class ChangePinActivity : BaseActivity(), TextGetListner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pin)

        txt_number.setOnTextChangeListner(this)
        clickListener()
    }

    override fun onTextChange(text: String?, digits_remaining: Int) {
        if (text!!.length == 4) {
            edt1.setText("" + ((Integer.parseInt(text) / 1000) % 10));
            edt2.setText("" + ((Integer.parseInt(text) / 100) % 10));
            edt3.setText("" + ((Integer.parseInt(text) / 10) % 10));
            edt4.setText("" + (Integer.parseInt(text) % 10));
        }
        if (text!!.length == 3) {
            edt1.setText("" + ((Integer.parseInt(text) / 100) % 10));
            edt2.setText("" + ((Integer.parseInt(text) / 10) % 10));
            edt3.setText("" + ((Integer.parseInt(text) / 1) % 10));
            edt4.setText("")
        }
        if (text!!.length == 2) {
            edt1.setText("" + ((Integer.parseInt(text) / 10) % 10));
            edt2.setText("" + ((Integer.parseInt(text) / 1) % 10));
            edt4.setText("")
            edt3.setText("")
        }
        if (text!!.length == 1) {
            edt1.setText("" + text);
            edt2.setText("")
            edt3.setText("")
            edt4.setText("")
        }
        if (text!!.isEmpty()) {
            edt1.setText("");
            edt2.setText("")
            edt3.setText("")
            edt4.setText("")
        }
    }

    private fun clickListener(){
        img_back.setOnClickListener {
            onBackPressed()
        }
    }
}
