package animalhusbandry.android.com.animalhusbandry.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.BaseFragment;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.FragAccountSettings;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.FragAddNewPet;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.FragEditPetProfile;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.FragEditUserProfile;
import animalhusbandry.android.com.animalhusbandry.Activities.LogoutParams.LogoutRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.LogoutParams.LogoutResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.RetroFit.RetroUtils;
import animalhusbandry.android.com.animalhusbandry.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener, FragEditUserProfile.OnFragmentInteractionListener {
    public static DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public NavigationView navigationView;
    public String strLogoutFullName, strLogoutEmail, strDeviceToken, strUserId;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView tvDrawerUserName, tvDrawerUserEmail;
    public ProgressDialog progressDialog;
    public boolean doubleBackToExitPressedOnce = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        initViews();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        strLogoutFullName = sharedPreferences.getString("strUserFullName", strLogoutFullName);
        strLogoutEmail = sharedPreferences.getString("strUserEmail", strLogoutEmail);
        strDeviceToken = sharedPreferences.getString("strDeviceToken", strDeviceToken);
        strUserId = sharedPreferences.getString("strUserId", strUserId);
        Log.e("@@@@@@@", strLogoutFullName + "");
        navigationView = (NavigationView) findViewById(R.id.nvView);
        View header = navigationView.getHeaderView(0);
        tvDrawerUserName = (TextView) header.findViewById(R.id.tvDrawerUserName);
        tvDrawerUserName.setText(strLogoutFullName);
        tvDrawerUserEmail = (TextView) header.findViewById(R.id.tvDrawerUserEmail);
        tvDrawerUserEmail.setText(strLogoutEmail);
        navigationView.setNavigationItemSelectedListener(this);

        openBaseFragment();

    }

    private void openBaseFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BaseFragment baseFragment = new BaseFragment();
        fragmentTransaction.replace(R.id.xmlFragment, baseFragment, "baseFragmentTag");
        fragmentTransaction.addToBackStack("baseFragment");
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_add_pet) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragAddNewPet fragAddNewPet= new FragAddNewPet();
            fragmentTransaction.addToBackStack("fragAddNewPet");
            fragmentTransaction.replace(R.id.xmlFragment, fragAddNewPet, "fragAddNewPetTag");
            fragmentTransaction.commit();

        } else {
            if (id == R.id.nav_account_settings) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragAccountSettings fragAccountSettings = new FragAccountSettings();
                fragmentTransaction.addToBackStack("fragAccountSettings");
                fragmentTransaction.replace(R.id.xmlFragment, fragAccountSettings, "fragAccountSettingsTag");
                fragmentTransaction.commit();
               /* FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragEditUserProfile fragmentEditUserProfile = new FragEditUserProfile();
                fragmentTransaction.replace(R.id.xmlFragment, fragmentEditUserProfile, "gf");
                fragmentTransaction.addToBackStack("fragmentEditUserProfile");
                fragmentTransaction.commit();*/

            } else if (id == R.id.nav_edit_pet_profile) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragEditPetProfile fragmentEditPetProfile = new FragEditPetProfile();
                fragmentTransaction.replace(R.id.xmlFragment, fragmentEditPetProfile, "fragmentEditPetProfileTag");
                fragmentTransaction.addToBackStack("fragmentEditPetProfile");
                fragmentTransaction.commit();

            } else if (id == R.id.nav_tips) {


            } else if (id == R.id.nav_logout) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                LayoutInflater layoutInflater = Dashboard.this.getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.alert_dialog_logout, null);
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = ProgressDialog.show(Dashboard.this, "Please wait", "logging out", true);
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

    private void doLogout(LogoutRequest logoutRequest) {
        RetroUtils retroUtils = new RetroUtils(this);
        retroUtils.getApiClient().userLogout(logoutRequest).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {

                Log.e("@@@@@@@@@", response.body().getResponse().getCode() + "");
                if (response.body().getResponse().getCode().equals("200")) {
                    Toast.makeText(Dashboard.this, "Come back soon ", Toast.LENGTH_SHORT).show();
                    finish();

                } else if (response.body().getResponse().getCode().equals("404")) {
                    progressDialog.cancel();
                    Toast.makeText(Dashboard.this, "Device token not found", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("400")) {
                    progressDialog.cancel();
                    Toast.makeText(Dashboard.this, "Logoff failed ", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    progressDialog.cancel();
                    Toast.makeText(Dashboard.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(Dashboard.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);}
            super.onBackPressed();
            return;}
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_settings, menu);
        return true;
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
