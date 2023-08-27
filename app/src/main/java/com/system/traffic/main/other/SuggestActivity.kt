package com.system.traffic.main.other

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.system.traffic.databinding.ActivitySuggestBinding

class SuggestActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sendEmail()
    }

    private fun sendEmail(){
        val emailAddress = "tmfrl1590@gmail.com"

        val intent = Intent( Intent.ACTION_SEND).apply {
            type = "text/plain"
            setPackage("com.google.android.gm")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
            putExtra(Intent.EXTRA_SUBJECT, "[문의하기]")
            putExtra(Intent.EXTRA_TEXT, "문의내용을 입력해주세요.")
        }

        startActivity(Intent.createChooser(intent, "메일전송하기"))

        finish()
    }
}