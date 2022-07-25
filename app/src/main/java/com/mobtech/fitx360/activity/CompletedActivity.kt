@file:Suppress("DEPRECATION")

package com.mobtech.fitx360.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.mobtech.fitx360.R
import com.mobtech.fitx360.utils.ConstantString
import com.mobtech.fitx360.utils.Utils
import com.mobtech.fitx360.workout.PWorkOutDetails

@Suppress("DEPRECATION", "UNCHECKED_CAST")
open class CompletedActivity : BaseActivity() {

    private lateinit var context: Context
    private lateinit var pWorkoutList: ArrayList<PWorkOutDetails>
    private var tableName: String = ""
    private var workoutId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed)
        window.statusBarColor = resources.getColor(R.color.colorAccent)
        context = this
        txtDurationTime = findViewById(R.id.txtDurationTime)
        txtTotalNoOfLevel = findViewById(R.id.txtTotalNoOfLevel)
        getSetIntent()
        setProgressDialog()

    }


    fun onClickBack() {
        saveData()
        finish()
    }

    private lateinit var txtTotalNoOfLevel: TextView
    private lateinit var txtDurationTime: TextView

    private fun getSetIntent() {
        val intent = intent
        pWorkoutList =
            intent.getSerializableExtra(ConstantString.workout_list) as ArrayList<PWorkOutDetails>
        tableName = intent.getStringExtra(ConstantString.table_name_from_workactivity)!!
        workoutId = intent.getStringExtra(ConstantString.workout_id_from_workactivity)!!
        txtTotalNoOfLevel.text = pWorkoutList.size.toString()
        txtDurationTime.text = intent.getStringExtra("Duration")
    }

    fun onClickTryAgain() {
        saveData()
        val intent1 = intent
        val intent = Intent(context, WorkoutActivity::class.java)
        intent.putExtra(ConstantString.workout_list,
            intent1.getSerializableExtra(ConstantString.workout_list))
        startActivity(intent)
        finish()
    }

    private lateinit var progressDialog: ProgressDialog
    private fun setProgressDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }

    private fun saveData() {
        Utils.setPref(this,
            ConstantString.PREF_LAST_UN_COMPLETE_DAY + "_" + tableName + "_" + workoutId,
            0)
    }

    override fun onBackPressed() {
        saveData()
        super.onBackPressed()
    }

    override fun onDestroy() {
        saveData()
        super.onDestroy()
    }

    override fun onStop() {
        saveData()
        super.onStop()
    }
}
