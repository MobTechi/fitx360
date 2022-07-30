package com.mobtech.fitx360.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.mobtech.fitx360.R
import com.mobtech.fitx360.UI.HourGlass.Hourglass
import com.mobtech.fitx360.utils.ConstantString
import com.mobtech.fitx360.utils.Utils
import com.mobtech.fitx360.utils.UtilsKt
import com.mobtech.fitx360.workout.PWorkOutDetails
import kotlinx.android.synthetic.main.activity_workout.*
import java.util.*

@Suppress("DEPRECATION", "UNCHECKED_CAST")
class WorkoutActivity : BaseActivity() {

    private lateinit var pWorkoutList: ArrayList<PWorkOutDetails>
    private lateinit var mContext: Context
    private var recycleWorkIndicatorAdapter: RecycleWorkIndicatorAdapter? = null
    private var recycleWorkoutTimeIndicatorAdapter: RecycleWorkoutTimeIndicatorAdapter? = null

    private var timeCountDown = 0
    private var timer: Timer? = null
    private var flagTimerPause: Boolean = false
    private var textToSpeech: TextToSpeech? = null
    private var boolSound = true
    private var tableName: String = ""


    override fun onBackPressed() {
//        super.onBackPressed()
        confirmToExitDialog()
    }

    private fun confirmToExitDialog() {
        flagTimerPause = true
        val dialog = Dialog(this)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dl_exercise_exit)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

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
        try {
            saveData()
            finish()
            dialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        window.statusBarColor = resources.getColor(R.color.colorGrayTrans)
        mContext = this

        try {
            pWorkoutList =
                intent.getSerializableExtra(ConstantString.workout_list) as ArrayList<PWorkOutDetails>
            tableName = intent.getStringExtra(ConstantString.work_table_name)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }

        defaultSetup()

        initAction()

    }

    /* Todo Common methods */
    lateinit var txtTimer: TextView
    private fun defaultSetup() {
        txtTimer = findViewById(R.id.txtTimer)
        start()
        boolSound = Utils.getPref(mContext, ConstantString.key_workout_sound, true)
        if (boolSound) {
            imgSound.setImageResource(R.drawable.ic_sound_on)
        } else {
            imgSound.setImageResource(R.drawable.ic_sound_off)
        }

        recycleWorkIndicatorAdapter = RecycleWorkIndicatorAdapter()
        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.NOWRAP
        rcyWorkoutStatus.layoutManager = layoutManager
        rcyWorkoutStatus.adapter = recycleWorkIndicatorAdapter

        val doWorkOutPgrAdapter = DoWorkoutPagerAdapter()
        viewPagerWorkout.adapter = doWorkOutPgrAdapter
//        viewPagerWorkout.currentItem = 0
        val currentItemPosition = Utils.getPref(this,
            ConstantString.PREF_LAST_UN_COMPLETE_DAY + "_" + tableName + "_" + pWorkoutList[0].workout_id,
            0)
        viewPagerWorkout.currentItem = currentItemPosition
        workoutSetup(0)
    }

    private var startTime: Long = 0
    private var running = false
    private var currentTime: Long = 0

    private fun start() {
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                setTime()
                handler.postDelayed(this, 1000)
            }
        }, 1000)
        this.startTime = System.currentTimeMillis()
        this.running = true
    }

    private var totalSec = 0
    private fun setTime() {
        /*if (getElapsedTimeMin() == 0L) {
            txtTimer.text = "0" + getElapsedTimeMin() + ":" + String.format("%02d", getElapsedTimeSecs())
        } else {
            if (getElapsedTimeMin() + "".length > 1) {
                txtTimer.text = "" + getElapsedTimeMin() + ":" + String.format("%02d", getElapsedTimeSecs())
            } else {
                txtTimer.text = "0" + getElapsedTimeMin() + ":" + String.format("%02d", getElapsedTimeSecs())
            }
        }*/

        try {
            if (!flagTimerPause) {
                totalSec += 1
                txtTimer.text = UtilsKt.secToTime(totalSec)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun pauseWorkOutTime() {
        this.running = false
        currentTime = System.currentTimeMillis() - startTime
    }

    private fun resumeWorkOutTime() {
        this.running = true
        this.startTime = System.currentTimeMillis() - currentTime
    }

    internal var progress = 0
    private var countDownTimer: Hourglass? = null
    private fun setupCountDown() {
        pauseWorkOutTime()
        progress = 0
        countDownTimer = object : Hourglass(30000, 1000) {
            override fun onTimerTick(timeRemaining: Long) {
            }

            override fun onTimerFinish() {
                resumeWorkOutTime()
            }
        }
        (countDownTimer as Hourglass).startTimer()
    }

    override fun onPause() {
        super.onPause()
        pauseWorkOutTime()
    }

    private fun initAction() {
        imgBack.setOnClickListener {
            finish()
        }

        imgbtnNext.setOnClickListener {
            workoutCompleted(viewPagerWorkout.currentItem + 1)
//            viewPagerWorkout.currentItem = viewPagerWorkout.currentItem + 1
        }

        imgbtnPrev.setOnClickListener {
            workoutCompleted(viewPagerWorkout.currentItem - 1)
//            viewPagerWorkout.currentItem = viewPagerWorkout.currentItem - 1
        }

        imgbtnDone.setOnClickListener {
            workoutCompleted(viewPagerWorkout.currentItem + 1)
        }

        imgbtnPause.setOnClickListener {
            showWorkoutDetails()
        }

        imgInfo.setOnClickListener {
            showWorkoutDetails()
        }

        imgSound.setOnClickListener {
            boolSound = Utils.getPref(mContext, ConstantString.key_workout_sound, true)
            if (boolSound) {
                imgSound.setImageResource(R.drawable.ic_sound_off)
                boolSound = false
                Utils.setPref(mContext, ConstantString.key_workout_sound, boolSound)
            } else {
                imgSound.setImageResource(R.drawable.ic_sound_on)
                boolSound = true
                Utils.setPref(mContext, ConstantString.key_workout_sound, boolSound)
            }
        }

        viewPagerWorkout.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(pos: Int) {
                workoutSetup(pos)
                if (boolSound) {
                    if (textToSpeech != null) {
                        textToSpeech!!.setSpeechRate(0.5f)
                        textToSpeech!!.speak(pWorkoutList[viewPagerWorkout.currentItem].title.toLowerCase(
                            Locale.ROOT)
                            .replace("ups", "up's"), TextToSpeech.QUEUE_FLUSH, null)
                    }
                }
            }

        })
    }

    /* Todo show current workout details */
    private fun showWorkoutDetails() {
        flagTimerPause = true
        val intent = Intent(mContext, WorkoutListDetailsActivity::class.java)
        intent.putExtra(ConstantString.key_workout_details_type, ConstantString.val_is_workout)
        intent.putExtra(ConstantString.key_workout_list_array, pWorkoutList)
        intent.putExtra(ConstantString.key_workout_list_pos, viewPagerWorkout.currentItem)
        mContext.startActivity(intent)
        overridePendingTransition(R.anim.slide_up, R.anim.none)
    }

    /* Todo set current workout setup */
    @SuppressLint("NotifyDataSetChanged")
    private fun workoutSetup(pos: Int) {
        val workDetails: PWorkOutDetails = pWorkoutList[pos]
        if (timer != null) {
            timer?.cancel()
        }

        if (workDetails.time_type == ConstantString.workout_type_time) {
            rltStepTypeWorkOut.visibility = View.GONE
            rltTimeTypeWorkOut.visibility = View.VISIBLE
            startWorkoutTimer(workDetails.time.substring(workDetails.time.indexOf(":") + 1)
                .toInt())
        } else {
            rltStepTypeWorkOut.visibility = View.VISIBLE
            rltTimeTypeWorkOut.visibility = View.GONE
        }
        recycleWorkIndicatorAdapter?.notifyDataSetChanged()
    }

    var timeTotal: String = ""

    /* Todo workout completed method */
    private fun workoutCompleted(pos: Int) {
        if (viewPagerWorkout.currentItem == (pWorkoutList.size - 1)) {
            timeTotal = txtTimer.text.toString()
            nextActivityStart()
        } else {
            flagTimerPause = true
            val intent = Intent(mContext, NextPrevDetailsWorkoutActivity::class.java)
            intent.putExtra(ConstantString.key_workout_list_pos, pos)
            intent.putExtra(ConstantString.key_workout_list_array, pWorkoutList)
            intent.putExtra(ConstantString.extra_workout_list_pos,
                (viewPagerWorkout.currentItem + 1))
            startActivity(intent)
            viewPagerWorkout.currentItem = pos
        }
    }

    private fun nextActivityStart() {
        finish()
        val intent = Intent(mContext, CompletedActivity::class.java)
        intent.putExtra(ConstantString.workout_list, pWorkoutList)
        intent.putExtra("Duration", timeTotal)
        intent.putExtra(ConstantString.workout_id_from_workactivity,
            pWorkoutList[0].workout_id.toString())
        intent.putExtra(ConstantString.table_name_from_workactivity, tableName)
        startActivity(intent)
        pauseWorkOutTime()
        setupCountDown()
    }

    /* Todo Workout Timing method */
    private fun startWorkoutTimer(totalTime: Int) {
        timeCountDown = 0
        txtTimeCountDown.text =
            "".plus(timeCountDown.toString()).plus(" / ").plus(totalTime.toString())

        recycleWorkoutTimeIndicatorAdapter = RecycleWorkoutTimeIndicatorAdapter(totalTime)
        val layoutManagerTimer = FlexboxLayoutManager()
        layoutManagerTimer.flexWrap = FlexWrap.NOWRAP
        rcyBottomWorkoutTimeStatus.layoutManager = layoutManagerTimer
        rcyBottomWorkoutTimeStatus.adapter = recycleWorkoutTimeIndicatorAdapter

        val handler = Handler()
        timer = Timer(false)

        val timerTask = object : TimerTask() {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                handler.post {
                    if (!flagTimerPause) {
                        timeCountDown++
//                    viewPagerWorkout.setCurrentItem(timeCountDown)
                        txtTimeCountDown.text =
                            "".plus(timeCountDown.toString()).plus(" / ").plus(totalTime.toString())
                        recycleWorkoutTimeIndicatorAdapter?.notifyDataSetChanged()

                        if (timeCountDown.equals(totalTime)) {
                            timer?.cancel()
                            workoutCompleted(viewPagerWorkout.currentItem + 1)
                            //viewPagerWorkout.currentItem = viewPagerWorkout.currentItem + 1
                        }
                    }
                }
            }
        }
        timer?.schedule(timerTask, 1000, 1000)
    }

    /* Todo Override methods */
    @SuppressLint("DefaultLocale")
    override fun onResume() {
        super.onResume()
        resumeWorkOutTime()
        textToSpeech = TextToSpeech(mContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech!!.language = Locale.US
                if (boolSound) {
                    textToSpeech!!.setSpeechRate(0.5f)
                    textToSpeech!!.speak(pWorkoutList[viewPagerWorkout.currentItem].title.toLowerCase()
                        .replace("ups", "up's"), TextToSpeech.QUEUE_FLUSH, null)
                }
            }
        })
        flagTimerPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
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

            /*if (viewPagerWorkout.currentItem > position) {
                holder.viewIndicator.setBackgroundColor(mContext.resources.getColor(R.color.color_theme))
            } else {
                holder.viewIndicator.setBackgroundColor(mContext.resources.getColor(R.color.color_gray))
            }*/

            if (viewPagerWorkout.currentItem > position) {
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

    inner class RecycleWorkoutTimeIndicatorAdapter(private val totalWorkOut: Int) :
        RecyclerView.Adapter<RecycleWorkoutTimeIndicatorAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(mContext).inflate(R.layout.row_of_recycle_time, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            if (timeCountDown > position) {
                holder.viewIndicator.setBackgroundColor(mContext.resources.getColor(R.color.color_theme))
            } else {
                holder.viewIndicator.setBackgroundColor(mContext.resources.getColor(R.color.color_gray))
            }
        }

        override fun getItemCount(): Int {
            return totalWorkOut
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            internal var viewIndicator: View = itemView.findViewById(R.id.viewIndicator) as View
//            init {
//                viewIndicator = itemView.findViewById(R.id.viewIndicator) as View
//            }
        }
    }


    inner class DoWorkoutPagerAdapter : PagerAdapter() {

        override fun isViewFromObject(convertView: View, anyObject: Any): Boolean {
            return convertView === anyObject as RelativeLayout
        }

        override fun getCount(): Int {
            return pWorkoutList.size
        }

        private fun getItem(pos: Int): PWorkOutDetails {
            return pWorkoutList[pos]
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val item: PWorkOutDetails = getItem(position)
            val itemView =
                LayoutInflater.from(mContext).inflate(R.layout.start_workout_row, container, false)
//            val txtWorkoutTime: TextView = itemView.findViewById(R.id.txtWorkoutTime)
            val txtWorkoutTitle: TextView = itemView.findViewById(R.id.txtWorkoutTitle)
            val txtWorkoutDetails: TextView = itemView.findViewById(R.id.txtWorkoutDetails)
            val viewfliperWorkout: ViewFlipper = itemView.findViewById(R.id.viewfliperWorkout)

//            txtWorkoutTime.text = item.time
            if (item.time_type == ConstantString.workout_type_step) {
                txtWorkoutTitle.text = "x ".plus(item.time)
            } else {
                txtWorkoutTitle.text = item.time
            }

            txtWorkoutDetails.text = item.title

            viewfliperWorkout.removeAllViews()
            val listImg: ArrayList<String> =
                Utils.getAssetItems(mContext, Utils.ReplaceSpacialCharacters(item.title))

            for (i in 0 until listImg.size) {
                val imgview = ImageView(mContext)
//                Glide.with(mContext).load("//android_asset/burpee/".plus(i.toString()).plus(".png")).into(imgview)
                Glide.with(mContext).load(listImg.get(i)).into(imgview)
                imgview.layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT)

                viewfliperWorkout.addView(imgview)
            }

            viewfliperWorkout.isAutoStart = true
            viewfliperWorkout.flipInterval =
                mContext.resources.getInteger(R.integer.viewfliper_animation)
            viewfliperWorkout.startFlipping()

            container.addView(itemView)
            return itemView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as RelativeLayout)
        }

    }


    private fun saveData() {
        Utils.setPref(this,
            ConstantString.PREF_LAST_UN_COMPLETE_DAY + "_" + tableName + "_" + pWorkoutList[0].workout_id.toString(),
            viewPagerWorkout.currentItem)
    }

    override fun onStop() {
        saveData()
        flagTimerPause = true
        super.onStop()
    }
}

