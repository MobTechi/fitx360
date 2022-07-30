package com.mobtech.fitx360.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mobtech.fitx360.R
import com.mobtech.fitx360.activity.WorkoutListActivity
import com.mobtech.fitx360.utils.ConstantString
import com.mobtech.fitx360.workout.PWorkOutCategory
import java.io.Serializable

class WorkoutCategoryAdapter(
    internal val mContext: Context,
    private val arrWorkoutCategoryData: ArrayList<PWorkOutCategory>,
) : RecyclerView.Adapter<WorkoutCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val convertView = LayoutInflater.from(mContext).inflate(R.layout.workout_row, parent, false)
        return ViewHolder(convertView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val item = getItem(pos)

        holder.imgWorkoutDifficultly.visibility = View.VISIBLE
        holder.txtWorkoutCount.text = item.exerciseCount

        when (item.catDifficultyLevel) {
            ConstantString.biginner -> {
                holder.imgWorkoutDifficultly.setImageResource(R.drawable.ic_beginner_level)
            }
            ConstantString.intermediate -> {
                holder.imgWorkoutDifficultly.setImageResource(R.drawable.ic_intermediate_level)
            }
            ConstantString.advance -> {
                holder.imgWorkoutDifficultly.setImageResource(R.drawable.ic_advanced_level)
            }
            else -> {
                holder.imgWorkoutDifficultly.visibility = View.GONE
            }
        }

        if (item.catDifficultyLevel == ConstantString.main) {
            holder.rltWorkOutTitle.visibility = View.VISIBLE
            holder.RltWorkOutDetails.visibility = View.GONE
            holder.txtWorkoutTitle.text = item.catName

        } else {
            holder.rltWorkOutTitle.visibility = View.GONE
            holder.RltWorkOutDetails.visibility = View.VISIBLE
            holder.txtWorkoutCategoryTitle.text = item.catName
            holder.txtWorkoutDetails.text = item.catSubCategory

            if (item.catDetailsBg == 0) {
                holder.txtWorkoutDetails.background = null
            } else {
                when (item.catSubCategory) {
                    ConstantString.beginnerColor, ConstantString.beginnerColor2 -> {
                        holder.txtWorkoutDetails.setBackgroundResource(R.drawable.bg_beginner_color)
                    }
                    ConstantString.intermediateColor -> {
                        holder.txtWorkoutDetails.setBackgroundResource(R.drawable.bg_intermediate_color)
                    }
                    ConstantString.advanceColor -> {
                        holder.txtWorkoutDetails.setBackgroundResource(R.drawable.bg_advance_color)
                    }
                }
//                holder.txtWorkoutDetails.setBackgroundResource(item.catDetailsBg)
            }
            if (!item.equals(0)) {
                holder.imgWorkoutRow.setImageResource(item.catImage)
            }
        }
    }

    fun getItem(pos: Int): PWorkOutCategory {
        return arrWorkoutCategoryData[pos]
    }

    override fun getItemCount(): Int {
        return arrWorkoutCategoryData.size
    }

    @Suppress("DEPRECATION", "PropertyName")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val rltWorkOutTitle: RelativeLayout = itemView.findViewById(R.id.rltWorkOutTitle)
        val RltWorkOutDetails: CardView = itemView.findViewById(R.id.RltWorkOutDetails)

        val txtWorkoutTitle: TextView = itemView.findViewById(R.id.txtWorkoutTitle)
        val txtWorkoutDetails: TextView = itemView.findViewById(R.id.txtWorkoutDetails)
        val txtWorkoutCategoryTitle: TextView = itemView.findViewById(R.id.txtWorkoutCategoryTitle)
        val txtWorkoutCount: TextView = itemView.findViewById(R.id.txtWorkoutCount)

        val imgWorkoutRow: ImageView = itemView.findViewById(R.id.imgWorkoutRow)
        val imgWorkoutDifficultly: ImageView = itemView.findViewById(R.id.imgWorkoutDeificulty)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val item = getItem(position)
            if (item.catDifficultyLevel != ConstantString.main) {
                val intent = Intent(mContext, WorkoutListActivity::class.java)
                intent.putExtra(ConstantString.key_workout_category_item, item as Serializable)
                mContext.startActivity(intent)
            }
        }

    }
}
