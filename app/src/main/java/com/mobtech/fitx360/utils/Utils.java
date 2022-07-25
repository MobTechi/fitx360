package com.mobtech.fitx360.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobtech.fitx360.R;

import java.io.IOException;
import java.util.ArrayList;

public class Utils {
    public static void setPref(Context c, String pref, Boolean val) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putBoolean(pref, val);
        e.apply();
    }

    public static boolean getPref(Context c, String pref, Boolean val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(pref, val);
    }

    public static void setPref(Context context, String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value);
        editor.apply();
    }

    public static String getPref(Context context, String key, String value) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, value);
    }

    public static void setPref(Context context, String key, Integer value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value);
        editor.apply();
    }

    public static Integer getPref(Context context, String key, Integer value) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, value);
    }

    public static class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private final Drawable mDivider;

        @SuppressLint("UseCompatLoadingForDrawables")
        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.rcy_divider_line);
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, RecyclerView parent, @NonNull RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    /**
     * TODO Get Images From Assets
     */
    public static ArrayList<String> getAssetItems(Context mContext, String categoryName) {
        ArrayList<String> arrayList = new ArrayList<>();
        String[] imgPath;
        AssetManager assetManager = mContext.getAssets();
        try {
            imgPath = assetManager.list(categoryName);
            if (imgPath != null) {
                for (String anImgPath : imgPath) {
                    arrayList.add("///android_asset/" + categoryName + "/" + anImgPath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String ReplaceSpacialCharacters(String string) {
        return string.replace(" ", "").replace("&", "").replace("-", "").replace("'", "");
    }
}
