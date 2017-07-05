package com.animalhusbandry.dashboard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.login.LoginActivity;
import com.animalhusbandry.model.LogoutRequest;
import com.animalhusbandry.model.LogoutResponse;
import com.animalhusbandry.petprofile.FragAddNewPet;
import com.animalhusbandry.petprofile.FragAllFavouritePets;
import com.animalhusbandry.retrofit.RetroUtils;
import com.animalhusbandry.settings.FragAccountSettings;
import com.animalhusbandry.userprofile.FragEditUserProfile;
import com.animalhusbandry.userprofile.FragGetAllMessageByUserId;
import com.animalhusbandry.utils.SessionManager;
import com.animalhusbandry.utils.setToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener, FragEditUserProfile.OnFragmentInteractionListener {
    public static DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public SharedPreferences sharedPreferences;
    public NavigationView navigationView;
    public String strLogoutFullName, strLogoutEmail, strDeviceToken, strUserId;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView tvDrawerUserName, tvDrawerUserEmail;
    public ProgressDialog progressDialog;
    public boolean doubleBackToExitPressedOnce = false;
    public FragmentManager fragmentManager;
    private ImageView btnToggle,imageViewDrawerHeader;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        initViews();
        fragmentManager = getSupportFragmentManager();
        openBaseFragment();//first fragment on startup
    }

    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews() {
        setToolbar.setToolbar(DashboardActivity.this, "DashBoard", false);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView ivSearch=(ImageView)toolbar.findViewById(R.id.ivSearch);
        btnToggle=(ImageView)toolbar.findViewById(R.id.btnToggle);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ImageButton backBtn = (ImageButton) toolbar.findViewById(R.id.backBtn);
        textView.setEnabled(true);
        //toolbar.setEnabled(true);
        setSupportActionBar(toolbar);
        //toolbar.collapseActionView();
        //getSupportActionBar().setTitle("Dashboard");
        //toolbar.setTitle("Dasboard");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        sharedPreferences = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        strLogoutFullName = sharedPreferences.getString("strUserFullName", strLogoutFullName);
        strLogoutEmail = sharedPreferences.getString("strUserEmail", strLogoutEmail);
        strDeviceToken = sharedPreferences.getString("strDeviceToken", strDeviceToken);
        strUserId = sharedPreferences.getString("strUserId", strUserId);
        Log.e("@@@@@@@", strLogoutFullName + "");
        navigationView = (NavigationView) findViewById(R.id.nvView);
        final View header = navigationView.getHeaderView(0);
        tvDrawerUserName = (TextView) header.findViewById(R.id.tvDrawerUserName);
        tvDrawerUserName.setText(strLogoutFullName);
        tvDrawerUserEmail = (TextView) header.findViewById(R.id.tvDrawerUserEmail);
        tvDrawerUserEmail.setText(strLogoutEmail);
        imageViewDrawerHeader=(ImageView)header.findViewById(R.id.imageViewDrawerHeader);
        navigationView.setNavigationItemSelectedListener(this);
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });
        imageViewDrawerHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragEditUserProfile fragmentEditUserProfile = new FragEditUserProfile();
                fragmentTransaction.replace(R.id.xmlFragment, fragmentEditUserProfile, "fragmentEditUserProfileTag");
                fragmentTransaction.addToBackStack("fragmentEditUserProfile");
                fragmentTransaction.commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

    }

    private void openBaseFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DashBoardFragment baseFragment = new DashBoardFragment();
        fragmentTransaction.replace(R.id.xmlFragment, baseFragment, "baseFragmentTag");
        fragmentTransaction.addToBackStack("baseFragment");
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_add_pet) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragAddNewPet fragAddNewPet = new FragAddNewPet();
            fragmentTransaction.replace(R.id.xmlFragment, fragAddNewPet, "fragAddNewPetTag");
            fragmentTransaction.addToBackStack("fragAddNewPet");
            fragmentTransaction.commit();

        } else {
            if (id == R.id.nav_account_settings) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragAccountSettings fragAccountSettings = new FragAccountSettings();
                fragmentTransaction.replace(R.id.xmlFragment, fragAccountSettings, "fragAccountSettingsTag");
                fragmentTransaction.addToBackStack("fragAccountSettings");
                fragmentTransaction.commit();
            } else if (id == R.id.nav_get_all_pet_profile) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DashBoardFragment dashBoardFragment = new DashBoardFragment();
                fragmentTransaction.replace(R.id.xmlFragment, dashBoardFragment, "dashBoardFragmentTag");
                fragmentTransaction.addToBackStack("dashBoardFragment");
                fragmentTransaction.commit();
            } else if (id == R.id.nav_tips) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragHealthTips fragHealthTips = new FragHealthTips();
                fragmentTransaction.replace(R.id.xmlFragment, fragHealthTips, "fragHealthTipsTag");
                fragmentTransaction.addToBackStack("fragHealthTips");
                fragmentTransaction.commit();
            }
            else if (id == R.id.nav_favourite_pet) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragAllFavouritePets fragAllFavouritePets= new FragAllFavouritePets();
                fragmentTransaction.replace(R.id.xmlFragment, fragAllFavouritePets, "fragAllFavouritePetsTag");
                fragmentTransaction.addToBackStack("fragAllFavouritePets");
                fragmentTransaction.commit();
            }
            else if (id == R.id.nav_getAll_Message_ByUser_Id) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragGetAllMessageByUserId fragGetAllMessageByUserId= new FragGetAllMessageByUserId();
                fragmentTransaction.replace(R.id.xmlFragment, fragGetAllMessageByUserId, "fragGetAllMessageByUserIdTag");
                fragmentTransaction.addToBackStack("fragGetAllMessageByUserId");
                fragmentTransaction.commit();
            }
            else if (id == R.id.nav_logout) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                LayoutInflater layoutInflater = DashboardActivity.this.getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.alert_dialog_logout, null);
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = ProgressDialog.show(DashboardActivity.this, "Please wait", "logging out", true);
                        progressDialog.setCancelable(true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(10000);
                                } catch (Exception e) {
                                }
                                progressDialog.dismiss();
                            }
                        }).start();
                        LogoutRequest logoutRequest = new LogoutRequest();
                        logoutRequest.setUserId(strUserId);
                        doLogout(logoutRequest);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create();
                builder.show();
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * open or close navigation drawer when click on toogle
     */
    public void openDrawer() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }





    private void doLogout(LogoutRequest logoutRequest) {
        RetroUtils retroUtils = new RetroUtils(this);
        retroUtils.getApiClient().userLogout(logoutRequest).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                Log.e("@@@@@@@@@", response.body().getResponse().getCode() + "");
                if (response.body().getResponse().getCode().equals("200")) {
                    Toast.makeText(DashboardActivity.this, "Come back soon ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    SessionManager session = new SessionManager(getBaseContext());
                    session.logoutUser();
                    finish();
                } else if (response.body().getResponse().getCode().equals("404")) {
                    progressDialog.cancel();
                    Toast.makeText(DashboardActivity.this, "Device token not found", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("400")) {
                    progressDialog.cancel();
                    Toast.makeText(DashboardActivity.this, "Logoff failed ", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    progressDialog.cancel();
                    Toast.makeText(DashboardActivity.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(DashboardActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (fragmentManager.getBackStackEntryCount() <= 1) {
            exit();
        } else {
            fragmentManager.popBackStack();
        }
    }

    private void exit() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            finish();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }

    /*    } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            finish();

        }
    }*/

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_layout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

  /*  public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_settings, menu);
        return true;
    }*/

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }*/
}
