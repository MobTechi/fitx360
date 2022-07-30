package com.mobtech.fitx360.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.mobtech.fitx360.R
import com.mobtech.fitx360.workout.PWorkOutCategory


object UtilsKt {

    private val arrWorkoutCategoryData = ArrayList<PWorkOutCategory>()

    private fun unitFormat(i: Int): String {
        return if (i < 0 || i >= 10) {
            "" + i
        } else "0$i"
    }

    fun secToTime(time: Int): String {
        return if (time <= 0) {
            "00:00"
        } else unitFormat(time / 60) + ":" + unitFormat(time % 60)
    }

    fun getVersionCodeName(context: Context): String {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            ""
        }
    }

    fun mobTechPlayStore(activity: Activity) {
        val mobTechURL = "https://play.google.com/store/apps/dev?id=7579037844327234040"
        try {
            val mobTechIntent = Intent(Intent.ACTION_VIEW)
            mobTechIntent.data = Uri.parse(mobTechURL)
            activity.startActivity(mobTechIntent)
        } catch (ignored: ActivityNotFoundException) {
        }
    }

    fun privacyPolicy(activity: Activity) {
        val privacyPolicyURL = "https://tamilandroo.web.app/fitx360/privacy-policy"
        try {
            val mobTechIntent = Intent(Intent.ACTION_VIEW)
            mobTechIntent.data = Uri.parse(privacyPolicyURL)
            activity.startActivity(mobTechIntent)
        } catch (ignored: ActivityNotFoundException) {
        }
    }

    fun getMenuItems(): ArrayList<PWorkOutCategory> {

        if (arrWorkoutCategoryData.isNotEmpty()) {
            return arrWorkoutCategoryData
        }

        var workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.main
        workoutCategoryDetails.catName = "7 X 4 Challenge"
        workoutCategoryDetails.catSubCategory = ""
        workoutCategoryDetails.catDetailsBg = 0
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = 0
        workoutCategoryDetails.catTableName = ""
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.full_body
        workoutCategoryDetails.catName = "Full Body"
        workoutCategoryDetails.catSubCategory = "7 X 4 Challenge"
        workoutCategoryDetails.catDetailsBg = 0
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.full_body
        workoutCategoryDetails.catTableName = ConstantString.tbl_full_body_workouts_list
        workoutCategoryDetails.exerciseCount = "11 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.full_body
        workoutCategoryDetails.catName = "Lower Body"
        workoutCategoryDetails.catSubCategory = "7 X 4 Challenge"
        workoutCategoryDetails.catDetailsBg = 0
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.lower_body
        workoutCategoryDetails.catTableName = ConstantString.tbl_lower_body_list
        workoutCategoryDetails.exerciseCount = "17 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.main
        workoutCategoryDetails.catName = "Chest"
        workoutCategoryDetails.catSubCategory = ""
        workoutCategoryDetails.catDetailsBg = 0
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = 0
        workoutCategoryDetails.catTableName = ""
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.biginner
        workoutCategoryDetails.catName = "Chest"
        workoutCategoryDetails.catSubCategory = "Beginners"
        workoutCategoryDetails.catDetailsBg = R.color.color_beginner
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.chest_beginner
        workoutCategoryDetails.catTableName = ConstantString.tbl_chest_beginner
        workoutCategoryDetails.exerciseCount = "11 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.intermediate
        workoutCategoryDetails.catName = "Chest"
        workoutCategoryDetails.catSubCategory = "Intermediate"
        workoutCategoryDetails.catDetailsBg = R.color.color_intermediate
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.chest_intermediate
        workoutCategoryDetails.catTableName = ConstantString.tbl_chest_intermediate
        workoutCategoryDetails.exerciseCount = "14 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.advance
        workoutCategoryDetails.catName = "Chest"
        workoutCategoryDetails.catSubCategory = "Advanced"
        workoutCategoryDetails.catDetailsBg = R.color.color_advance
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.chest_advanced
        workoutCategoryDetails.catTableName = ConstantString.tbl_chest_advanced
        workoutCategoryDetails.exerciseCount = "16 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.main
        workoutCategoryDetails.catName = "Abs"
        workoutCategoryDetails.catSubCategory = ""
        workoutCategoryDetails.catDetailsBg = 0
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = 0
        workoutCategoryDetails.catTableName = ""
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.biginner
        workoutCategoryDetails.catName = "Abs"
        workoutCategoryDetails.catSubCategory = "Beginner"
        workoutCategoryDetails.catDetailsBg = R.color.color_beginner
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.abs_beginner
        workoutCategoryDetails.catTableName = ConstantString.tbl_abs_beginner
        workoutCategoryDetails.exerciseCount = "16 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.intermediate
        workoutCategoryDetails.catName = "Abs"
        workoutCategoryDetails.catSubCategory = "Intermediate"
        workoutCategoryDetails.catDetailsBg = R.color.color_intermediate
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.abs_intermediate
        workoutCategoryDetails.catTableName = ConstantString.tbl_abs_intermediate
        workoutCategoryDetails.exerciseCount = "21 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.advance
        workoutCategoryDetails.catName = "Abs"
        workoutCategoryDetails.catSubCategory = "Advanced"
        workoutCategoryDetails.catDetailsBg = R.color.color_advance
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.abs_advanced
        workoutCategoryDetails.catTableName = ConstantString.tbl_abs_advanced
        workoutCategoryDetails.exerciseCount = "21 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.main
        workoutCategoryDetails.catName = "Arm"
        workoutCategoryDetails.catSubCategory = ""
        workoutCategoryDetails.catDetailsBg = 0
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = 0
        workoutCategoryDetails.catTableName = ""
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.biginner
        workoutCategoryDetails.catName = "Arm"
        workoutCategoryDetails.catSubCategory = "Beginner"
        workoutCategoryDetails.catDetailsBg = R.color.color_beginner
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.arm_beginner
        workoutCategoryDetails.catTableName = ConstantString.tbl_arm_beginner
        workoutCategoryDetails.exerciseCount = "19 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.intermediate
        workoutCategoryDetails.catName = "Arm"
        workoutCategoryDetails.catSubCategory = "Intermediate"
        workoutCategoryDetails.catDetailsBg = R.color.color_intermediate
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.abs_intermediate
        workoutCategoryDetails.catTableName = ConstantString.tbl_arm_intermediate
        workoutCategoryDetails.exerciseCount = "25 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.advance
        workoutCategoryDetails.catName = "Arm"
        workoutCategoryDetails.catSubCategory = "Advanced"
        workoutCategoryDetails.catDetailsBg = R.color.color_advance
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.abs_advanced
        workoutCategoryDetails.catTableName = ConstantString.tbl_arm_advanced
        workoutCategoryDetails.exerciseCount = "28 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.main
        workoutCategoryDetails.catName = "Shoulder & Back"
        workoutCategoryDetails.catSubCategory = ""
        workoutCategoryDetails.catDetailsBg = 0
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = 0
        workoutCategoryDetails.catTableName = ""
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.biginner
        workoutCategoryDetails.catName = "Shoulder & Back"
        workoutCategoryDetails.catSubCategory = "Beginner"
        workoutCategoryDetails.catDetailsBg = R.color.color_beginner
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.shoulder_beginner
        workoutCategoryDetails.catTableName = ConstantString.tbl_shoulder_back_beginner
        workoutCategoryDetails.exerciseCount = "17 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.intermediate
        workoutCategoryDetails.catName = "Shoulder & Back"
        workoutCategoryDetails.catSubCategory = "Intermediate"
        workoutCategoryDetails.catDetailsBg = R.color.color_intermediate
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.shoulder_intermediate
        workoutCategoryDetails.catTableName = ConstantString.tbl_shoulder_back_intermediate
        workoutCategoryDetails.exerciseCount = "17 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.advance
        workoutCategoryDetails.catName = "Shoulder & Back"
        workoutCategoryDetails.catSubCategory = "Advanced"
        workoutCategoryDetails.catDetailsBg = R.color.color_advance
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.shoulder_advanced
        workoutCategoryDetails.catTableName = ConstantString.tbl_shoulder_back_advanced
        workoutCategoryDetails.exerciseCount = "17 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.main
        workoutCategoryDetails.catName = "Leg"
        workoutCategoryDetails.catSubCategory = ""
        workoutCategoryDetails.catDetailsBg = 0
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = 0
        workoutCategoryDetails.catTableName = ""
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.biginner
        workoutCategoryDetails.catName = "Leg"
        workoutCategoryDetails.catSubCategory = "Beginner"
        workoutCategoryDetails.catDetailsBg = R.color.color_beginner
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.leg_beginner
        workoutCategoryDetails.catTableName = ConstantString.tbl_leg_beginner
        workoutCategoryDetails.exerciseCount = "23 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.intermediate
        workoutCategoryDetails.catName = "Leg"
        workoutCategoryDetails.catSubCategory = "Intermediate"
        workoutCategoryDetails.catDetailsBg = R.color.color_intermediate
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.leg_intermediate
        workoutCategoryDetails.catTableName = ConstantString.tbl_leg_intermediate
        workoutCategoryDetails.exerciseCount = "36 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catDifficultyLevel = ConstantString.advance
        workoutCategoryDetails.catName = "Leg"
        workoutCategoryDetails.catSubCategory = "Advanced"
        workoutCategoryDetails.catDetailsBg = R.color.color_advance
        workoutCategoryDetails.catTypeImage = 0
        workoutCategoryDetails.catImage = R.drawable.leg_advanced
        workoutCategoryDetails.catTableName = ConstantString.tbl_leg_advanced
        workoutCategoryDetails.exerciseCount = "46 Exercises"
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        return arrWorkoutCategoryData
    }

}