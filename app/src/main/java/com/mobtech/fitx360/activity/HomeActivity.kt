package com.mobtech.fitx360.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobtech.fitx360.R
import com.mobtech.fitx360.adapter.WorkoutCategoryAdapter
import com.mobtech.fitx360.database.DataHelper
import com.mobtech.fitx360.interfaces.CallbackListener
import com.mobtech.fitx360.interfaces.ConfirmDialogCallBack
import com.mobtech.fitx360.utils.Utils
import com.mobtech.fitx360.utils.UtilsKt
import com.mobtech.fitx360.workout.PWorkOutCategory
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(), View.OnClickListener, CallbackListener {

    override fun onBackPressed() {


        confirmationDialog(this, object : ConfirmDialogCallBack {
            override fun Okay() {
                val homeIntent = Intent(Intent.ACTION_MAIN)
                homeIntent.addCategory(Intent.CATEGORY_HOME)
                homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(homeIntent)
                finishAffinity()
            }

            override fun cancel() {

            }

        }, "", getString(R.string.exit_confirmation))
    }


    /* Todo Objects*/
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mContext = this
        DataHelper(mContext).getReadWriteDB()


        //setDefaultData()
        setupHomeData()

        successCall()

        //subScribeToFirebaseTopic()
    }


    @SuppressLint("WrongConstant")
    override fun onClick(v: View) {


        when (v.id) {
            R.id.imgbtnDrawer -> drawerLayout.openDrawer(Gravity.START)
        }
    }


    private fun setupHomeData() {
        val arrWorkoutCategoryData: ArrayList<PWorkOutCategory> =UtilsKt.getMenuItems()

        rcyWorkout.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcyWorkout.addItemDecoration(Utils.SimpleDividerItemDecoration(this))

        val workoutAdapter = WorkoutCategoryAdapter(mContext, arrWorkoutCategoryData)
        rcyWorkout.adapter = workoutAdapter

    }

    private fun successCall() {
    }

    override fun onSuccess() {

    }

    override fun onCancel() {

    }

    override fun onRetry() {

    }
}
