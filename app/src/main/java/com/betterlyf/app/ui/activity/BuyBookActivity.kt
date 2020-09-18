package com.betterlyf.app.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.betterlyf.app.R
import kotlinx.android.synthetic.main.activity_buy_book.*


class BuyBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_book)
        clickListener()
    }

    fun clickListener(){
        img_back.setOnClickListener {
            onBackPressed()
        }
        txt_book.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://www.amazon.in/dp/B086Z3PY2S/ref=cm_sw_r_apa_i_ecLKEbZYJW4QK")

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)

        }
    }
}
