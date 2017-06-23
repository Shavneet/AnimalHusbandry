package com.animalhusbandry.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.animalhusbandry.R;

/**
 * Created by grewalshavneet on 6/19/2017.
 */

public class setToolbar {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Toolbar setToolbar(final AppCompatActivity activity, String title, boolean backArrowEnable) throws NullPointerException {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ImageButton imgBack = (ImageButton) toolbar.findViewById(R.id.backBtn);
        if (backArrowEnable) {
            imgBack.setVisibility(View.VISIBLE);
       /* activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);*/
        } else {
            imgBack.setVisibility(View.INVISIBLE);
        }

        activity.setTitle("");
        mTitle.setText(title);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(activity.getResources().getColor(R.color.DarkGreen, null));
        } else {
            toolbar.setTitleTextColor(activity.getResources().getColor(R.color.DarkGreen));
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity.onBackPressed();
                activity.finish();
            }
        });
        return toolbar;
    }

}
