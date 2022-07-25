package com.mobtech.fitx360.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mobtech.fitx360.R
import com.mobtech.fitx360.utils.ConstantString
import com.mobtech.fitx360.utils.Utils
import com.mobtech.fitx360.workout.PWorkOutDetails
import kotlinx.android.synthetic.main.activity_next_prev_details_workout.*
import java.util.*

@Suppress("DEPRECATION", "UNCHECKED_CAST")
class NextPrevDetailsWorkoutActivity : BaseActivity() {

    private var timeCountDown = 0
    private var timer: Timer? = null
    private var workoutPos: Int = 0
    private var pWorkoutList = ArrayList<PWorkOutDetails>()
    private lateinit var mContext: Context
    private var viewpagerCurrentItem: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_prev_details_workout)

        mContext = this

        try {
            workoutPos = intent.getIntExtra(ConstantString.key_workout_list_pos, 0)
            pWorkoutList =
                intent.getSerializableExtra(ConstantString.key_workout_list_array) as ArrayList<PWorkOutDetails>
            viewpagerCurrentItem = intent.getIntExtra(ConstantString.extra_workout_list_pos, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        defaultSetup()
        setAction()
        startWorkoutTimer()
        indicator()

    }


    private var recycleWorkIndicatorAdapter: RecycleWorkIndicatorAdapter? = null
    private fun indicator() {
        recycleWorkIndicatorAdapter = RecycleWorkIndicatorAdapter()
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.NOWRAP
        rcyWorkoutStatus.layoutManager = layoutManager
        rcyWorkoutStatus.adapter = recycleWorkIndicatorAdapter
    }

    /* Todo here define adapter */
    inner class RecycleWorkIndicatorAdapter :
        RecyclerView.Adapter<RecycleWorkIndicatorAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(mContext).inflate(R.layout.row_of_recycleview, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {


            if (viewpagerCurrentItem > position) {
                holder.viewIndicator.background =
                    ContextCompat.getDrawable(mContext, R.drawable.view_line_theme)
            } else {
                holder.viewIndicator.background =
                    ContextCompat.getDrawable(mContext, R.drawable.view_line_gray)
            }
        }

        override fun getItemCount(): Int {
            return pWorkoutList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            internal var viewIndicator: View = itemView.findViewById(R.id.viewIndicator) as View

//            init {
//                viewIndicator = itemView.findViewById(R.id.viewIndicator) as View
//            }

        }
    }


    /* Todo common methods */
    private fun defaultSetup() {
        progressBar.progress = 30
        progressBar.max = 30
        progressBar.secondaryProgress = 30

        if (pWorkoutList[workoutPos].time == ConstantString.workout_type_step) {
            txtWorkoutTime.text = "x".plus(pWorkoutList[workoutPos].time)
        } else {
            txtWorkoutTime.text = pWorkoutList[workoutPos].time
        }

        txtWorkoutName.text = pWorkoutList[workoutPos].title
        txtSteps.text = workoutPos.toString().plus(" / ").plus(pWorkoutList.size)

        viewfliperWorkout.removeAllViews()
        val listImg: ArrayList<String> = Utils.getAssetItems(mContext,
            Utils.ReplaceSpacialCharacters(pWorkoutList[workoutPos].title))

        for (i in 0 until listImg.size) {
            val imageView = ImageView(mContext)
//            Glide.with(mContext).load("//android_asset/burpee/".plus(i.toString()).plus(".png")).into(imgview)
            Glide.with(mContext).load(listImg[i]).into(imageView)
            imageView.layoutParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            viewfliperWorkout.addView(imageView)
        }

        viewfliperWorkout.isAutoStart = true
        viewfliperWorkout.flipInterval =
            mContext.resources.getInteger(R.integer.viewfliper_animation)
        viewfliperWorkout.startFlipping()
    }

    private fun setAction() {
        btnSkip.setOnClickListener {
            timer?.cancel()
            finish()
        }
    }

    /* Todo Workout Timing method */
    private fun startWorkoutTimer() {
        timeCountDown = 30

        val handler = Handler()
        timer = Timer(false)

        val timerTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    try {
                        /*if (!flagTimerPause) {
                            timeCountDown++
                            txtCountDown.text = timeCountDown.toString()
                            progressBar.progress = timeCountDown
                            if (timeCountDown == 30) {
                                finish()
                            }
                        }*/
                        if (!flagTimerPause) {

                            timeCountDown--
                            txtCountDown.text = timeCountDown.toString()
                            progressBar.progress = timeCountDown
                            if (timeCountDown == 0) {
                                finish()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
        }
        timer?.schedule(timerTask, 1000, 1000)
    }

    override fun onBackPressed() {
        confirmToExitDialog()
    }

    // Todo Quite confirmatin dialog
    private var flagTimerPause: Boolean = false
    private fun confirmToExitDialog() {
        flagTimerPause = true
        val dialog = Dialog(this)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dl_exercise_exit)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        val imgbtnClose = dialog.findViewById(R.id.imgbtnClose) as ImageView
        val btnQuite = dialog.findViewById(R.id.btnQuite) as Button
        val btnContinue = dialog.findViewById(R.id.btnContinue) as Button


        imgbtnClose.setOnClickListener {
            try {
                flagTimerPause = false
                dialog.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btnQuite.setOnClickListener {
            quiteData(dialog)
        }

        btnContinue.setOnClickListener {
            try {
                flagTimerPause = false
                dialog.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        dialog.setOnCancelListener {
            flagTimerPause = false
        }


        dialog.show()
    }

    private fun quiteData(dialog: Dialog) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
        dialog.dismiss()
    }
}
