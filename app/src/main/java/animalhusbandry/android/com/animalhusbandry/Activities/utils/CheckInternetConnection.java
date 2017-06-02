package animalhusbandry.android.com.animalhusbandry.Activities.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by grewalshavneet on 6/1/2017.
 */

public class CheckInternetConnection {

    public static Boolean checkInternet(Context activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else if (netInfo != null && (netInfo.getState() == NetworkInfo.State.DISCONNECTED || netInfo.getState() == NetworkInfo.State.DISCONNECTING || netInfo.getState() == NetworkInfo.State.SUSPENDED || netInfo.getState() == NetworkInfo.State.UNKNOWN)) {
            return false;
        } else {
            return false;
        }
    }
}
