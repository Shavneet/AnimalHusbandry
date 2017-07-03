package com.animalhusbandry.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.dashboard.DashboardActivity;
import com.animalhusbandry.model.ForgotPasswordRequest;
import com.animalhusbandry.model.ForgotPasswordResponse;
import com.animalhusbandry.model.LoginRequest;
import com.animalhusbandry.model.LoginResponse;
import com.animalhusbandry.retrofit.RetroUtils;
import com.animalhusbandry.signup.CreateUserAccount;
import com.animalhusbandry.utils.BlurrBuilder;
import com.animalhusbandry.utils.CheckInternetConnection;
import com.animalhusbandry.utils.SessionManager;
import com.animalhusbandry.utils.VectorDrawableUtils;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class LoginActivity extends AppCompatActivity {
 private EditText etEmail, etPassword;
 private ImageView btnLogin;
 private TextView tvForgotPassword, tvCreateAccount;
 private String strEmail, strPassword;
 private SharedPreferences.Editor editor;
 private ProgressDialog ringProgressDialog;
 private ProgressDialog progressDialog;
 private CardView cardView;

    /*   public LoginActivity(Context context) {
           this.context = context;
       }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (ringProgressDialog != null && ringProgressDialog.isShowing()) {
            ringProgressDialog.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etLoginPassword);
        btnLogin = (ImageView) findViewById(R.id.btnLogin);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvCreateAccount = (TextView) findViewById(R.id.tvCreateAccount);
        cardView = (CardView) findViewById(R.id.card_view_pet_profile_ui);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        editor = pref.edit();
        final View content = this.findViewById(android.R.id.content).getRootView();
      /*  if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.DarkGreen));
        }*/
        if (content.getWidth() > 0) {
            Bitmap image = BlurrBuilder.blur(content);
            cardView.setBackground(new BitmapDrawable(LoginActivity.this.getResources(), image));
        } else
            content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Bitmap image = BlurrBuilder.blur(content);
                    cardView.setBackgroundDrawable(new BitmapDrawable(LoginActivity.this.getResources(), image));
                }
            });
        cardView.setPreventCornerOverlap(true);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        setupUI(viewGroup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(CheckInternetConnection.checkInternet(LoginActivity.this))) {
                    Toast.makeText(LoginActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                } else if (etEmail.getText().toString().equals("")) {
                    etEmail.setError("Enter email address");
                    etEmail.requestFocus();
                } else if (etPassword.getText().toString().equals("")) {
                    etPassword.setError("Enter password");
                    etPassword.requestFocus();
                } else {
                    ringProgressDialog = ProgressDialog.show(LoginActivity.this, "Please wait ...", "Logging in", true);
                    ringProgressDialog.setCancelable(false);
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(50000);
                            } catch (Exception e) {
                            }
                            ringProgressDialog.dismiss();
                        }
                    }).start();
                    strEmail = etEmail.getText().toString();
                    strPassword = etPassword.getText().toString();
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(strEmail);
                    loginRequest.setPassword(strPassword);
                    doLogin(loginRequest);
                }
            }
        });
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateUserAccount.class);
                startActivity(intent);
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                LayoutInflater layoutInflater = LoginActivity.this.getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.alert_dialog, null);
                builder.setView(dialogView);
                builder.setTitle("Enter your email:");
                builder.setCancelable(true);
                final EditText etForgotPasswordEmail = (EditText) dialogView.findViewById(R.id.etForgotPasswordEmail);
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (etForgotPasswordEmail.getText().toString().equals("")) {
                            Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                        } else if (!(etForgotPasswordEmail.getText().toString().equals(""))) {
                            progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait ...", "Sending you a mail", true);
                            progressDialog.setCancelable(false);
                            String strForgotPasswordEmail = etForgotPasswordEmail.getText().toString();
                            ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
                            forgotPasswordRequest.setEmail(strForgotPasswordEmail);
                            doForgotPassword(forgotPasswordRequest);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create();
                builder.show();
            }
        });
        setIcons();
        ImageView imageView = (ImageView) findViewById(R.id.iv_bg);
        Glide.with(this).load(R.drawable.giphy).into(imageView);
    }

    private void setIcons() {
        Drawable email = VectorDrawableUtils.getDrawable(this, R.drawable.ic_023_email);
        /*Drawable password = VectorDrawableUtils.getDrawable(this, R.drawable.ic_017_security_2);*/
        etEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, email, null);
        /*etPassword.setCompoundDrawablesWithIntrinsicBounds(password, null, null, null);*/
    }

    private void doLogin(LoginRequest loginRequest) {
        RetroUtils retroUtils = new RetroUtils(this);
        retroUtils.getApiClient().userLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                ringProgressDialog.cancel();
                if (response.body() == null) {
                    Toast.makeText(getBaseContext(), "Server error", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("200")) {
/*
                    Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
*/
                    String strUserFullName = response.body().getResponse().getResult().getUserFullName();
                    String strUserEmail = response.body().getResponse().getResult().getEmail();
                    String strSessionId = response.body().getResponse().getResult().getSessionId();
                    String strUserId = response.body().getResponse().getResult().getUserId();
                    String strDeviceToken = response.body().getResponse().getResult().getDeviceToken();
                    editor.putString("strUserFullName", strUserFullName);
                    editor.putString("strSessionId", strSessionId);
                    editor.putString("strUserEmail", strUserEmail);
                    editor.putString("strUserId", strUserId);
                    editor.putString("strDeviceToken", strDeviceToken);
                    editor.commit();
                    SessionManager session = new SessionManager(getBaseContext());
                    session.createLoginSession(strEmail, strPassword, strSessionId);
                    Log.e("!!!!!!!!!!", strUserFullName + "");
                    Log.e("!!!!!!!!!!", strSessionId + "");
                    Log.e("!!!Device ", strDeviceToken + "");
                    Log.e("!USERID ", strUserId + "");
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (response.body().getResponse().getCode().equals("404")) {
                    Toast.makeText(LoginActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("503")) {
                    Toast.makeText(LoginActivity.this, "Your email address have not authorized for user", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("403")) {
                    Toast.makeText(LoginActivity.this, "You are currently deleted by admin ", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(LoginActivity.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("400")) {
                    Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ringProgressDialog.cancel();
                Toast.makeText(LoginActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doForgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        RetroUtils retroUtils = new RetroUtils(this);
        retroUtils.getApiClient().userForgotPassword(forgotPasswordRequest).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                progressDialog.cancel();
                if (response.body().getResponse().getCode().equals("200")) {
                    Log.e("%%%%%%%%%%%%", response.body().getResponse().getMessage() + "");
                    String messageForgotPassword = response.body().getResponse().getMessage();
                    Toast.makeText(LoginActivity.this, messageForgotPassword, Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("404")) {
                    Toast.makeText(LoginActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("403")) {
                    Toast.makeText(LoginActivity.this, "You are currently blocked by admin ", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(LoginActivity.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(LoginActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
       /* etPassword.setText("");
        etEmail.setText("");*/
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }

                private void hideSoftKeyboard() {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}