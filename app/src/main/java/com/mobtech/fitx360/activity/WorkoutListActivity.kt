package com.mobtech.fitx360.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobtech.fitx360.R
import com.mobtech.fitx360.adapter.WorkoutListAdapter
import com.mobtech.fitx360.database.DataHelper
import com.mobtech.fitx360.utils.ConstantString
import com.mobtech.fitx360.workout.PWorkOutCategory
import com.mobtech.fitx360.workout.PWorkOutDetails
import kotlinx.android.synthetic.main.activity_workout_list.*

class WorkoutListActivity : BaseActivity() {

    private lateinit var mContext: Context
    private lateinit var pWorkOutCategory: PWorkOutCategory
    private lateinit var workOutDetailData: ArrayList<PWorkOutDetails>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)
        mContext = this

        pWorkOutCategory =
            intent.getSerializableExtra(ConstantString.key_workout_category_item) as PWorkOutCategory

        defaultSetup()
        initAction()

    }


    /* Todo common methods */
    private fun defaultSetup() {
        txtWorkoutListCategoryName.text = pWorkOutCategory.catName
        txtWorkoutListCategoryDetails.text = pWorkOutCategory.catSubCategory
        imgToolbarBack.setImageResource(pWorkOutCategory.catImage)

        when (pWorkOutCategory.catDefficultyLevel) {
            ConstantString.biginner -> imgWorkoutDificultyImage.setImageResource(
                R.drawable.ic_beginner_level)
            ConstantString.intermediate -> imgWorkoutDificultyImage.setImageResource(
                R.drawable.ic_intermediate_level)
            ConstantString.advance -> imgWorkoutDificultyImage.setImageResource(
                R.drawable.ic_advanced_level)
            else -> imgWorkoutDificultyImage.visibility = View.GONE
        }

        val data = DataHelper(mContext)
        workOutDetailData = data.getWorkOutDetails(pWorkOutCategory.catTableName)

        rcyWorkoutList.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        //rcyWorkoutList.addItemDecoration(Utils.SimpleDividerItemDecoration(this))

        val workoutListAdapter = WorkoutListAdapter(mContext, workOutDetailData)
        rcyWorkoutList.adapter = workoutListAdapter

    }

    private fun initAction() {
        imgWorkOutListBack.setOnClickListener {


            finish()
        }
        btnStartWorkout.setOnClickListener {
            startExerciseActivity()
        }
    }

    private fun startExerciseActivity() {
        val intent = Intent(mContext, WorkoutActivity::class.java)
        intent.putExtra(ConstantString.workout_list, workOutDetailData)
        intent.putExtra(ConstantString.work_table_name, pWorkOutCategory.catTableName)
        startActivity(intent)
    }
}
