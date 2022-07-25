package com.mobtech.fitx360.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mobtech.fitx360.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val serviceIntent = Intent("com.android.vending.billing.InAppBillingService.BIND")
        serviceIntent.setPackage("com.android.vending")
        successCall()
    }

    private fun successCall() {
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            startActivity(Intent(this, HomeActivity::class.java))
        }, 1500)
    }
}
