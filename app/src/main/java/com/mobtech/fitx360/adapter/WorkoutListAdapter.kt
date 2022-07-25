package com.mobtech.fitx360.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobtech.fitx360.R
import com.mobtech.fitx360.activity.WorkoutListActivity
import com.mobtech.fitx360.activity.WorkoutListDetailsActivity
import com.mobtech.fitx360.utils.ConstantString
import com.mobtech.fitx360.utils.Utils
import com.mobtech.fitx360.workout.PWorkOutDetails

class WorkoutListAdapter(
    val mContext: Context,
    val workOutCategoryData: ArrayList<PWorkOutDetails>
) : RecyclerView.Adapter<WorkoutListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return workOutCategoryData.size
    }

    private fun getItem(pos: Int): PWorkOutDetails {
        return workOutCategoryData[pos]
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val convertView =
            LayoutInflater.from(mContext).inflate(R.layout.workout_list_row, parent, false)
        return ViewHolder(convertView)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val item: PWorkOutDetails = getItem(pos)

        holder.txtWorkoutTitle.text = item.title
//        holder.txtWorkoutTime.text = item.time
        val timeType = if (item.time_type == "time") item.time else "x ".plus(item.time)
        holder.txtWorkoutTime.text = timeType
        holder.imgWorkoutDemo.removeAllViews()
        val listImg: ArrayList<String> =
            Utils.getAssetItems(mContext, Utils.ReplaceSpacialCharacters(item.title))

        for (i in 0 until listImg.size) {
            val imgview = ImageView(mContext)
//            Glide.with(mContext).load("//android_asset/burpee/".plus(i.toString()).plus(".png")).into(imgview)
            Glide.with(mContext).load(listImg[i]).into(imgview)
            imgview.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
            holder.imgWorkoutDemo.addView(imgview)
        }

        holder.imgWorkoutDemo.isAutoStart = true
        holder.imgWorkoutDemo.flipInterval =
            mContext.resources.getInteger(R.integer.viewfliper_animation)
        holder.imgWorkoutDemo.startFlipping()

    }

    @Suppress("DEPRECATION")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        //        val imgWorkoutDemo:ImageView = itemView.findViewById(R.id.imgWorkoutDemo)
        val imgWorkoutDemo: ViewFlipper = itemView.findViewById(R.id.imgWorkoutDemo)
        val txtWorkoutTitle: TextView = itemView.findViewById(R.id.txtWorkoutTitle)
        val txtWorkoutTime: TextView = itemView.findViewById(R.id.txtWorkoutTime)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent = Intent(mContext, WorkoutListDetailsActivity::class.java)
            intent.putExtra(ConstantString.key_workout_details_type,
                ConstantString.val_is_workout_list_activity)
            intent.putExtra(ConstantString.key_workout_list_array, workOutCategoryData)
            intent.putExtra(ConstantString.key_workout_list_pos, position)
            mContext.startActivity(intent)
            (mContext as WorkoutListActivity).overridePendingTransition(R.anim.slide_up,
                R.anim.none)
        }
    }

}
