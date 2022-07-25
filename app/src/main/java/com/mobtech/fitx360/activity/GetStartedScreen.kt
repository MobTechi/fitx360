package com.mobtech.fitx360.activity

import android.content.Intent
import android.os.Bundle
import com.mobtech.fitx360.R
import com.mobtech.fitx360.utils.ConstantString
import com.mobtech.fitx360.utils.Utils
import kotlinx.android.synthetic.main.activity_get_started_screen.*

class GetStartedScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started_screen)

        Utils.setPref(this,ConstantString.pref_key_is_first_time,false)

        initAction()
    }

    /* todo Default init methods */
    private fun initAction() {
        btnGetStarted.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            /*if(Utils.getPref(this@GetStartedScreen, ConstantString.pref_Key_purchase_status,false)){
                startActivity(Intent(this, HomeActivity::class.java))
            } else{
                startActivity(Intent(this, PurchaseActivity::class.java))
            }*/
            finish()
        }
    }

}
