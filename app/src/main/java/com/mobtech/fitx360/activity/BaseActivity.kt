package com.mobtech.fitx360.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.mobtech.fitx360.R
import com.mobtech.fitx360.interfaces.ConfirmDialogCallBack
import com.mobtech.fitx360.utils.ConstantString
import com.mobtech.fitx360.utils.UtilsKt
import com.mobtech.fitx360.workout.PWorkOutCategory
import kotlinx.android.synthetic.main.activity_base.*
import java.io.Serializable

@Suppress("DEPRECATION")
open class BaseActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
    ConfirmDialogCallBack {

    lateinit var drawerLayout: DrawerLayout
    private lateinit var context: Context
    private lateinit var listOfMenuItem: ListView
    private var arrWorkoutCategoryData: ArrayList<PWorkOutCategory> = ArrayList()

    @SuppressLint("InflateParams")
    override fun setContentView(layoutResID: Int) {
        drawerLayout =
            LayoutInflater.from(this).inflate(R.layout.activity_base, null) as DrawerLayout
        val activityContainer = drawerLayout.findViewById(R.id.activity_content) as FrameLayout
        LayoutInflater.from(this).inflate(layoutResID, activityContainer, true)
        super.setContentView(drawerLayout)
        context = this
        listOfMenuItem = findViewById(R.id.listOfMenuItem)

        setDrawerMenuItems()
        imgItem.setOnClickListener {
            confirmationDialog(this, this, "", getString(R.string.exit_confirmation))
        }
        txtExit.setOnClickListener {
            confirmationDialog(this, this, "", getString(R.string.exit_confirmation))
        }

        llBase.post {
            val resources: Resources = resources
            val width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                280f,
                resources.displayMetrics
            )
            val params =
                llBase.layoutParams
            params.width = width.toInt()
            llBase.layoutParams = params
        }
    }

    private lateinit var menuAdapter: MenuAdapter

    private fun setDrawerMenuItems() {
        appVersionCode.text = UtilsKt.getVersionCodeName(this)
        val menuItems = UtilsKt.getMenuItems()
        arrWorkoutCategoryData =
            menuItems.filter { item -> item.catDifficultyLevel != ConstantString.main } as ArrayList<PWorkOutCategory>
        addExtraMenuItems()
        listOfMenuItem.onItemClickListener = this
        menuAdapter = MenuAdapter()
        listOfMenuItem.adapter = menuAdapter
        setListViewHeightBasedOnItems(listOfMenuItem)
    }

    inner class MenuAdapter : BaseAdapter() {

        @SuppressLint("ViewHolder", "InflateParams", "SetTextI18n")
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val menuView = LayoutInflater.from(context).inflate(R.layout.cell_of_drawer_item, null)
            val imgItem = menuView.findViewById(R.id.imgItem) as ImageView
            val txtItem = menuView.findViewById(R.id.txtItem) as TextView
            val txtCount = menuView.findViewById(R.id.txtCount) as TextView
            Glide.with(this@BaseActivity).load(arrWorkoutCategoryData[p0].catImage).into(imgItem)
            txtItem.text =
                "${arrWorkoutCategoryData[p0].catName} ${arrWorkoutCategoryData[p0].catSubCategory}"
            if (p0 < 17) {
                txtCount.text = arrWorkoutCategoryData[p0].exerciseCount
            } else {
                txtCount.visibility = View.GONE
            }
            return menuView
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return arrWorkoutCategoryData.size
        }
    }

    @SuppressLint("WrongConstant")
    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        drawerLayout.closeDrawer(Gravity.START)
        if (position < 17) {
            val intent = Intent(this, WorkoutListActivity::class.java)
            intent.putExtra(ConstantString.key_workout_category_item,
                arrWorkoutCategoryData[position] as Serializable)
            startActivity(intent)
        } else {
            when {
                arrWorkoutCategoryData[position].catName === "Rate Us" -> rateUs()
                arrWorkoutCategoryData[position].catName === "More App's" -> moreApp()
                arrWorkoutCategoryData[position].catName === "Privacy Policy" -> privacyPolicy()
            }
        }
    }

    private fun moreApp() {
        UtilsKt.mobTechPlayStore(this)
    }

    private fun privacyPolicy() {
        UtilsKt.privacyPolicy(this)
    }

    private fun rateUs() {
        startActivity(Intent(Intent.ACTION_VIEW,
            Uri.parse("https://play.google.com/store/apps/details?id=com.mobtech.fitx360")))
    }

    private fun setListViewHeightBasedOnItems(listView: ListView): Boolean {
        val listAdapter = listView.adapter
        if (listAdapter != null) {
            val numberOfItems = listAdapter.count
            // Get total height of all items.
            var totalItemsHeight = 0
            for (itemPos in 0 until numberOfItems) {
                val item = listAdapter.getView(itemPos, null, listView)
                item.measure(0, 0)
                totalItemsHeight += item.measuredHeight
            }
            // Get total height of all item dividers.
            val totalDividersHeight = listView.dividerHeight * (numberOfItems - 1)
            // Set list height.
            val params = listView.layoutParams
            params.height = totalItemsHeight + totalDividersHeight
            listView.layoutParams = params
            listView.requestLayout()
            return true
        } else {
            return false
        }
    }

    fun confirmationDialog(
        content: Context,
        confirmCallBack: ConfirmDialogCallBack,
        strTitle: String,
        strMsg: String,
    ): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        val builder1 = AlertDialog.Builder(content)
        builder1.setTitle(strTitle)
        builder1.setMessage(strMsg)
        builder1.setCancelable(true)

        builder1.setPositiveButton("Yes") { dialog, _ ->
            dialog.cancel()
            confirmCallBack.Okay()
        }

        builder1.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
            confirmCallBack.cancel()
        }

        val alert11 = builder1.create()
        alert11.show()

        return false
    }

    override fun Okay() {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(homeIntent)
        finishAffinity()
    }

    override fun cancel() {
    }

    private fun addExtraMenuItems() {
        var workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catName = "Rate Us"
        workoutCategoryDetails.catImage = R.drawable.ic_round_star
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catName = "More App's"
        workoutCategoryDetails.catImage = R.drawable.ic_more_apps
        arrWorkoutCategoryData.add(workoutCategoryDetails)

        workoutCategoryDetails = PWorkOutCategory()
        workoutCategoryDetails.catName = "Privacy Policy"
        workoutCategoryDetails.catImage = R.drawable.ic_policy
        arrWorkoutCategoryData.add(workoutCategoryDetails)
    }
}
