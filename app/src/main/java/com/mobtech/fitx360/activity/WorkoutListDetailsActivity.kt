package com.mobtech.fitx360.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.mobtech.fitx360.R
import com.mobtech.fitx360.utils.ConstantString
import com.mobtech.fitx360.utils.Utils
import com.mobtech.fitx360.workout.PWorkOutDetails
import kotlinx.android.synthetic.main.activity_workout_list_details.*

@Suppress("UNCHECKED_CAST", "DEPRECATION")
class WorkoutListDetailsActivity : BaseActivity() {

    lateinit var workOutCategoryData: ArrayList<PWorkOutDetails>
    lateinit var mContext: Context
    private var currentPos: Int = 0
    private var typeOfControl: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list_details)
        mContext = this

        window.statusBarColor = resources.getColor(R.color.colorGrayTrans)
        workOutCategoryData =
            intent.getSerializableExtra(ConstantString.key_workout_list_array) as ArrayList<PWorkOutDetails>
        currentPos = intent.getIntExtra(ConstantString.key_workout_list_pos, 0)
        typeOfControl = intent.getStringExtra(ConstantString.key_workout_details_type) as String

        defaultSetup()

        initAction()
    }


    /* Todo Common settings methods */
    @SuppressLint("SetTextI18n")
    private fun defaultSetup() {
        val doWorkOutPgrAdpt = DoWorkoutPagerAdapter()
        viewPagerWorkoutDetails.adapter = doWorkOutPgrAdpt
        viewPagerWorkoutDetails.currentItem = currentPos
        imgbtnDone.text = (1 + currentPos).toString().plus(" / ").plus(workOutCategoryData.size)

        if (typeOfControl != ConstantString.val_is_workout_list_activity) {
            rltBottomControl.visibility = View.GONE
        }

    }

    private fun initAction() {
        imgbtnBack.setOnClickListener {
            onBackPressed()
            //overridePendingTransition(R.anim.none, R.anim.slide_down)
        }
        imgbtnNext.setOnClickListener {
            if (viewPagerWorkoutDetails.currentItem < workOutCategoryData.size)
                viewPagerWorkoutDetails.currentItem = viewPagerWorkoutDetails.currentItem + 1
        }
        imgbtnPrev.setOnClickListener {
            if (viewPagerWorkoutDetails.currentItem > 0)
                viewPagerWorkoutDetails.currentItem = viewPagerWorkoutDetails.currentItem - 1
        }
        viewPagerWorkoutDetails.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPageScrollStateChanged(p0: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(pos: Int) {
                imgbtnDone.text = (1 + pos).toString().plus(" / ").plus(workOutCategoryData.size)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.none, R.anim.slide_down)
    }

    /* Todo adapter */
    inner class DoWorkoutPagerAdapter : PagerAdapter() {

        override fun isViewFromObject(convertView: View, anyObject: Any): Boolean {
            return convertView === anyObject as RelativeLayout
        }

        override fun getCount(): Int {
            return workOutCategoryData.size
        }

        private fun getItem(pos: Int): PWorkOutDetails {
            return workOutCategoryData[pos]
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val item: PWorkOutDetails = getItem(position)
            val itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.workout_details_row, container, false)
            val txtWorkoutTitle: TextView = itemView.findViewById(R.id.txtWorkoutTitle)
            val txtWorkoutDetails: TextView = itemView.findViewById(R.id.txtWorkoutDetails)
            val viewfliperWorkout: ViewFlipper = itemView.findViewById(R.id.imgWorkoutDemo)

            txtWorkoutTitle.text = item.title
            txtWorkoutDetails.text = item.descriptions.replace("\\n",
                System.getProperty("line.separator")!!).replace("\\r", "")

            viewfliperWorkout.removeAllViews()

            val listImg: ArrayList<String> =
                Utils.getAssetItems(mContext, Utils.ReplaceSpacialCharacters(item.title))
            for (i in 0 until listImg.size) {
                val imageview = ImageView(mContext)
//                Glide.with(mContext).load("//android_asset/burpee/".plus(i.toString()).plus(".png")).into(imgview)
                Glide.with(mContext).load(listImg[i]).into(imageview)

                val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                layoutParams.gravity = Gravity.START
                imageview.layoutParams = layoutParams

                // imgview.setLayoutParams(LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
                viewfliperWorkout.addView(imageview)
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

}
