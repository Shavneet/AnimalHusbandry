package animalhusbandry.android.com.animalhusbandry.Activities;

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
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import animalhusbandry.android.com.animalhusbandry.Activities.ForgotPasswordParams.ForgotPasswordRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.ForgotPasswordParams.ForgotPasswordResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.LoginParams.LoginRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.LoginParams.LoginResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.RetroFit.RetroUtils;
import animalhusbandry.android.com.animalhusbandry.Activities.utils.BlurrBuilder;
import animalhusbandry.android.com.animalhusbandry.Activities.utils.VectorDrawableUtils;
import animalhusbandry.android.com.animalhusbandry.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class Login extends AppCompatActivity {
    EditText etEmail, etPassword;
    ImageButton btnLogin;
    TextView tvForgotPassword, tvCreateAccount;
    String strEmail, strPassword;
    public SharedPreferences.Editor editor;
    public ProgressDialog ringProgressDialog;
    ProgressDialog progressDialog;
    CardView cardView;

    /*   public Login(Context context) {
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
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etLoginPassword);
        btnLogin = (ImageButton) findViewById(R.id.btnLogin);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvCreateAccount = (TextView) findViewById(R.id.tvCreateAccount);
        cardView = (CardView) findViewById(R.id.card_view);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        editor = pref.edit();

        final View content = this.findViewById(android.R.id.content).getRootView();
        if (content.getWidth() > 0) {
            Bitmap image = BlurrBuilder.blur(content);
            cardView.setBackground(new BitmapDrawable(Login.this.getResources(), image));
        } else
            content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Bitmap image = BlurrBuilder.blur(content);
                    cardView.setBackgroundDrawable(new BitmapDrawable(Login.this.getResources(), image));
                }
            });
        cardView.setPreventCornerOverlap(true);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().equals("")) {
                    etEmail.setError("Enter email address");
                    etEmail.requestFocus();
                } else if (etPassword.getText().toString().equals("")) {
                    etPassword.setError("Enter password");
                    etPassword.requestFocus();
                } else {
                    ringProgressDialog = ProgressDialog.show(Login.this, "Please wait ...", "Logging in", true);
                    ringProgressDialog.setCancelable(true);
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(10000);
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
                Intent intent = new Intent(Login.this, CreateUserAccount.class);
                startActivity(intent);
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                LayoutInflater layoutInflater = Login.this.getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.alert_dialog, null);
                builder.setView(dialogView);
                builder.setTitle("Enter your email:");
                builder.setCancelable(true);
                final EditText etForgotPasswordEmail = (EditText) dialogView.findViewById(R.id.etForgotPasswordEmail);
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (etForgotPasswordEmail.getText().toString().equals("")) {
                            Toast.makeText(Login.this, "Please enter email", Toast.LENGTH_SHORT).show();
                        } else if (!(etForgotPasswordEmail.getText().toString().equals(""))) {
                            progressDialog = ProgressDialog.show(Login.this, "Please wait ...", "Sending you a mail", true);
                            progressDialog.setCancelable(true);

                           /* new Thread(new Runnable() {

                              @Override
                                public void run() {
                                    try {
                                        Thread.sleep(10000);
                                    } catch (Exception e) {
                                    }
                                    progressDialog.dismiss();
                                }
                            }).start();*/
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
                /*  Intent intent1=new Intent(Login.this,ForgotPassword.class);
              startActivity(intent1  );*/
            }
        });
        setIcons();
        ImageView imageView = (ImageView) findViewById(R.id.iv_bg);
        Glide.with(this).load(R.drawable.giphy).into(imageView);
    }
    private void setIcons() {
        Drawable email = VectorDrawableUtils.getDrawable(this, R.drawable.ic_021_opened_email_envelope);
        Drawable password = VectorDrawableUtils.getDrawable(this, R.drawable.ic_028_key);
        etEmail.setCompoundDrawablesWithIntrinsicBounds(email, null, null, null);
        etPassword.setCompoundDrawablesWithIntrinsicBounds(password, null, null, null);
    }
    private void doLogin(LoginRequest loginRequest) {
        RetroUtils retroUtils = new RetroUtils(this);
        retroUtils.getApiClient().userLogin(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                ringProgressDialog.cancel();
                if (response.body().getResponse().getCode().equals("200")) {
                    Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
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
                    Log.e("!!!!!!!!!!", strUserFullName + "");
                    Log.e("!!!!!!!!!!", strSessionId + "");
                    Log.e("!!!Device ", strDeviceToken + "");
                    Log.e("!USERID ", strUserId + "");
                    Intent intent = new Intent(Login.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                } else if (response.body().getResponse().getCode().equals("404")) {
                    Toast.makeText(Login.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("503")) {
                    Toast.makeText(Login.this, "Your email address have not authorized for user", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("403")) {
                    Toast.makeText(Login.this, "You are currently deleted by admin ", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(Login.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("400")) {
                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ringProgressDialog.cancel();
                Toast.makeText(Login.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Login.this, messageForgotPassword, Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("404")) {
                    Toast.makeText(Login.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("403")) {
                    Toast.makeText(Login.this, "You are currently blocked by admin ", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(Login.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(Login.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        etPassword.setText("");
        etEmail.setText("");
    }
}