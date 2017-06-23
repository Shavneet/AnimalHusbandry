package com.animalhusbandry.dashboard;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by grewalshavneet on 6/19/2017.
 */

public class BaseFragment extends Fragment {
    public DashboardActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (DashboardActivity) context;
    }
}
