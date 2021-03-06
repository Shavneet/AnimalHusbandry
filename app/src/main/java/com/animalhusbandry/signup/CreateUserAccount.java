package com.animalhusbandry.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.login.LoginActivity;
import com.animalhusbandry.model.SignUpRequest;
import com.animalhusbandry.model.SignUpResponse;
import com.animalhusbandry.retrofit.RetroUtils;
import com.animalhusbandry.utils.VectorDrawableUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class CreateUserAccount extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText etFName, etLName, etContact, etEmail, etPassword, etConfirmPassword;
    Button btnCreateUserAccount, googleSignInButton;
    String strUserFName, strUserLName, strUserContact, strUserEmail, strUserPassword, strUserConfirmPassword;
    public SharedPreferences.Editor editor;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN = 100;
    public ProgressDialog ringProgressDialog;
    public ProgressDialog googleProgressDialog;
    private CardView cardView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user_account);
        /*getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/
        init();
        setIcons();
    }

    public void init() {

        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);
        etContact = (EditText) findViewById(R.id.etContact);
        etEmail = (EditText) findViewById(R.id.etEmail);
        googleSignInButton = (Button) findViewById(R.id.googleSignInButton);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        btnCreateUserAccount = (Button) findViewById(R.id.btnCreateUserAccount);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
        editor = pref.edit();
       /* Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView textView=(TextView)toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Create your account");*/
        btnCreateUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFName.getText().toString().equals("")) {
                    etFName.setError("Please enter first name");
                } else if (etFName.getText().toString().length() < 3) {
                    etFName.setError("Firstname is must");
                    etFName.requestFocus();
                } else if (etLName.getText().toString().equals("")) {
                    etLName.setError("Please enter last name");
                    etLName.requestFocus();
                } else if (etLName.getText().toString().length() < 3) {
                    etLName.setError("Last name is must");
                    etLName.requestFocus();
                } else if (etContact.getText().toString().equals("")) {
                    etContact.setError("Enter a valid contact number");
                    etContact.requestFocus();
                } else if (etContact.getText().toString().length() < 10) {
                    etContact.setError("Enter a valid contact number");
                    etContact.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim().toLowerCase()).matches()) {
                    etEmail.setError("Invalid Email Address");
                    etEmail.requestFocus();
                } else if (etPassword.getText().toString().length() < 8 || etPassword.getText().toString().length() > 15) {
                    etPassword.setError("Password must be 8-15 characters");
                    etPassword.requestFocus();
                } else if (!(isValidPassword(etPassword.getText().toString()))) {
                    etPassword.setError("Password should include atleast 1 special character(@,# etc) & 1 number(0-9)");
                    etPassword.requestFocus();
                } else if (etConfirmPassword.getText().toString().equals("")) {
                    etConfirmPassword.setError("Confirm your password");
                    etConfirmPassword.requestFocus();
                } else if (!(etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))) {
                    Toast.makeText(CreateUserAccount.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    ringProgressDialog = ProgressDialog.show(CreateUserAccount.this, "Please wait ...", "Creating your account", true);
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
                    strUserFName = etFName.getText().toString();
                    strUserLName = etLName.getText().toString();
                    strUserEmail = etEmail.getText().toString();
                    strUserContact = etContact.getText().toString();
                    strUserPassword = etPassword.getText().toString();
                    strUserConfirmPassword = etConfirmPassword.getText().toString();
                    SignUpRequest signUpRequest = new SignUpRequest();
                    signUpRequest.setFirstName(strUserFName);
                    signUpRequest.setLastName(strUserLName);
                    signUpRequest.setPhone(strUserContact);
                    signUpRequest.setEmail(strUserEmail);
                    signUpRequest.setPassword(strUserPassword);
                    doSignUp(signUpRequest);
                }
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleProgressDialog = ProgressDialog.show(CreateUserAccount.this, "Please wait ...", "Fetching your data from google", true);

                googleProgressDialog.setCancelable(true);

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                        } catch (Exception e) {
                        }
                        googleProgressDialog.dismiss();
                    }
                }).start();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

                //Starting intent for result
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        setupUI(viewGroup);

    }

    private void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }

                private void hideSoftKeyboard() {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etFName.getWindowToken(), 0);
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

    private void doSignUp(SignUpRequest signUpRequest) {
        RetroUtils retroUtils = new RetroUtils(this);
        retroUtils.getApiClient().userSignUp(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.body().getResponse().getCode().equals("200")) {
                    final SharedPreferences sharedPreferences = CreateUserAccount.this.getSharedPreferences("Options", MODE_PRIVATE);
                    if (sharedPreferences.getString("strUserId", "").isEmpty()) {
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
                    }
                    ringProgressDialog.cancel();
                    Log.e("%%%%%%%%%%%%", response.body().getResponse().getResult().getPhone() + "");
                    Toast.makeText(CreateUserAccount.this, "Account created Successfully.You can login now", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateUserAccount.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else if (response.body().getResponse().getCode().equals("409")) {
                    ringProgressDialog.cancel();
                    Toast.makeText(CreateUserAccount.this, " Email already exist", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    ringProgressDialog.cancel();
                    Toast.makeText(CreateUserAccount.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                } else {

                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                ringProgressDialog.cancel();
                Toast.makeText(CreateUserAccount.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            Log.e("##############", "requestcode");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            googleProgressDialog.cancel();
            GoogleSignInAccount acct = result.getSignInAccount();
            //Displaying name and email
            etFName.setText(acct.getGivenName());
            etEmail.setText(acct.getEmail());
            etLName.setText(acct.getFamilyName());
        } else {
            //If login fails
            googleProgressDialog.cancel();
            Status s = result.getStatus();
            Log.e("##############", s + "");
            Toast.makeText(this, "Google login failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[@#$%]).{8,15})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void setIcons() {
        Drawable FName = VectorDrawableUtils.getDrawable(this, R.drawable.ic_person_add);
        Drawable password = VectorDrawableUtils.getDrawable(this, R.drawable.ic_vpn_key);
        Drawable Confirmpassword = VectorDrawableUtils.getDrawable(this, R.drawable.ic_lock_filled);
        Drawable LName = VectorDrawableUtils.getDrawable(this, R.drawable.ic_group_add);
        Drawable email = VectorDrawableUtils.getDrawable(this, R.drawable.ic_email_filled);
        Drawable contact = VectorDrawableUtils.getDrawable(this, R.drawable.ic_phone_iphone);
        etFName.setCompoundDrawablesWithIntrinsicBounds(null, null, FName, null);
        etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, password, null);
        etLName.setCompoundDrawablesWithIntrinsicBounds(null, null, LName, null);
        etEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, email, null);
        etContact.setCompoundDrawablesWithIntrinsicBounds(null, null, contact, null);
        etConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(null, null,Confirmpassword, null);
    }

}