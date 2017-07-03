package com.animalhusbandry.search;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.model.AddFavouritePetRequest;
import com.animalhusbandry.model.AddFavouritePetResponse;
import com.animalhusbandry.model.AddMessageRequest;
import com.animalhusbandry.model.AddMessageResponse;
import com.animalhusbandry.model.SearchPetUsingFiltersResponse;
import com.animalhusbandry.retrofit.RetroUtils;

import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.animalhusbandry.R.id.ivCircularImageView;

public class ShowFullPetProfileAfterSearchActivity extends AppCompatActivity {
    protected SearchPetUsingFiltersResponse.Result userPetArrayList;
    protected String strPetName, strBloodline, strOwnerMobileNumber, strRegistration, strAge, strColor, strLocation, strBreed, strAnyOther,
            strPetId, strImageUrl, strGender, strComment, strUserId;
    protected SearchPetUsingFiltersResponse.PetVaccinationsList[] searchPetUsingFiltersArrayList;
    protected TextView tvPetName, tvBloodline, tvRegistration, tvAge, tvColor, tvLocation, tvBreed, tvAnyOther, tvOwnerMobileNumber, ivBtnAddComment;
    protected EditText leavecomment;
    protected CheckBox checkboxMale, checkboxFemale, checkboxDHPP, checkboxRabies, checkboxParvoVirus, checkboxNone;
    protected ImageView ivAddImage, ivFavourite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_pet_profile_after_search);
        init();
    }

    private void init() {

        Toolbar   toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton backBtn=(ImageButton)toolbar.findViewById(R.id.backBtn);
        TextView toolbar_title=(TextView)toolbar.findViewById(R.id.toolbar_title);
        ImageView btnToggle=(ImageView)toolbar.findViewById(R.id.btnToggle);
        ImageView ivSearch = (ImageView) toolbar.findViewById(R.id.ivSearch);
        btnToggle.setVisibility(View.GONE);
        ivSearch.setVisibility(View.GONE);
        toolbar_title.setText("Pet profile");
        setSupportActionBar(toolbar);
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        userPetArrayList = (SearchPetUsingFiltersResponse.Result) bundle.getSerializable("petProfileUserClicked");
        searchPetUsingFiltersArrayList = userPetArrayList.getPetVaccinationsList();
        strPetName = userPetArrayList.getName().toString();
        strBloodline = userPetArrayList.getBloodLine();
        strGender = userPetArrayList.getGender().toString();
        strOwnerMobileNumber = userPetArrayList.getOwnerMobileNumber();
        strRegistration = userPetArrayList.getMicrochipNumber();
        strAge = userPetArrayList.getAge();
        strColor = userPetArrayList.getColor();
        strLocation = userPetArrayList.getLocation();
        strBreed = userPetArrayList.getBreed();
        strPetId = userPetArrayList.getPetId();
        strImageUrl = userPetArrayList.getImageUrl();
        ivAddImage = (ImageView) findViewById(ivCircularImageView);
        tvPetName = (TextView) findViewById(R.id.etPetName);
        tvBloodline = (TextView) findViewById(R.id.etBloodline);
        tvRegistration = (TextView) findViewById(R.id.etRegistration);
        tvAge = (TextView) findViewById(R.id.etAge);
        tvColor = (TextView) findViewById(R.id.etColor);
        leavecomment = (EditText) findViewById(R.id.leave_comment);
        ivBtnAddComment = (TextView) findViewById(R.id.ivBtnAddComment);
        tvLocation = (TextView) findViewById(R.id.etLocation);
        tvBreed = (TextView) findViewById(R.id.etBreed);
        tvAnyOther = (TextView) findViewById(R.id.etAnyOther);
        tvOwnerMobileNumber = (TextView) findViewById(R.id.etOwnerMobileNumber);
        checkboxMale = (CheckBox) findViewById(R.id.checkboxMale);
        checkboxFemale = (CheckBox) findViewById(R.id.checkboxFemale);
        checkboxDHPP = (CheckBox) findViewById(R.id.checkboxDHPP);
        checkboxRabies = (CheckBox) findViewById(R.id.checkboxRabies);
        checkboxParvoVirus = (CheckBox) findViewById(R.id.checkboxParvoVirus);
        ivFavourite = (ImageView) findViewById(R.id.ivFavourite);
        tvPetName.setText(strPetName);
        tvBloodline.setText(strBloodline);
        tvRegistration.setText(strRegistration);
        tvAge.setText(strAge);
        tvColor.setText(strColor);
        tvLocation.setText(strLocation);
        tvBreed.setText(strBreed);
        tvOwnerMobileNumber.setText(strOwnerMobileNumber);
        SharedPreferences.Editor editor;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Option", MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("PetId", strPetId);
        editor.commit();
        checkboxNone = (CheckBox) findViewById(R.id.checkboxNone);
        if (strGender.equals("Male")) {
            checkboxMale.setChecked(true);
        } else if (strGender.equals("Female")) {
            checkboxFemale.setChecked(true);
        }
        for (int i = 0; i < searchPetUsingFiltersArrayList.length; i++) {
            Log.e("@@@@@@@@", searchPetUsingFiltersArrayList[i] + "");
            if (searchPetUsingFiltersArrayList[i].getName().equals("None")) {
                checkboxNone.setChecked(true);
            } else {

                if (searchPetUsingFiltersArrayList[i].getName().equals("DHPP")) {
                    checkboxDHPP.setChecked(true);
                } else if (searchPetUsingFiltersArrayList[i].getName().equals("Rabies")) {
                    checkboxRabies.setChecked(true);
                } else if (searchPetUsingFiltersArrayList[i].getName().equals("Parvo virus")) {
                    checkboxParvoVirus.setChecked(true);
                } else {
                    tvAnyOther.setText(searchPetUsingFiltersArrayList[i].getName());
                }
            }
        }

        new DownloadImageTask(ivAddImage).execute(strImageUrl);
        ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("Option", MODE_PRIVATE);
                SharedPreferences sharedPreferencesLogin = getSharedPreferences("Options", MODE_PRIVATE);
                strUserId = sharedPreferencesLogin.getString("strUserId", "");
                strPetId = sharedPreferences.getString("PetId", "");
                Log.e("ADDPETUSERID", strPetId + "");
                Log.e("##%@%#%@", strUserId + "");
                AddFavouritePetRequest addFavouritePetRequest = new AddFavouritePetRequest();
                addFavouritePetRequest.setPetId(strPetId);
                addFavouritePetRequest.setUserId(strUserId);
                doAddFavouritePet(addFavouritePetRequest);
            }
        });

        ivBtnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strComment = leavecomment.getText().toString();

                callServiceMethod();


            }
        });
    }

    private void callServiceMethod() {

        SharedPreferences sharedPreferences = getSharedPreferences("Option", MODE_PRIVATE);
        SharedPreferences sharedPreferencesLogin = getSharedPreferences("Options", MODE_PRIVATE);
        strUserId = sharedPreferencesLogin.getString("strUserId", "");
        strPetId = sharedPreferences.getString("PetId", "");
        AddMessageRequest addMessageRequest = new AddMessageRequest();
        addMessageRequest.setComment(strComment);
        addMessageRequest.setUserId(strUserId);
        addMessageRequest.setPetId(strPetId);
        doAddMessage(addMessageRequest);


    }

    private void doAddMessage(AddMessageRequest addMessageRequest) {
        RetroUtils retroUtils = new RetroUtils(getBaseContext());
        retroUtils.getApiClient().ADD_MESSAGE_RESPONSE_CALL(addMessageRequest).enqueue(new Callback<AddMessageResponse>() {
            @Override
            public void onResponse(Call<AddMessageResponse> call, Response<AddMessageResponse> response) {
                if (response == null || response.body() == null) {
                    Toast.makeText(getBaseContext(), "Server Error", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("200")) {
                    Toast.makeText(ShowFullPetProfileAfterSearchActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(ShowFullPetProfileAfterSearchActivity.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddMessageResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Service failure.Try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void doAddFavouritePet(AddFavouritePetRequest addFavouritePetRequest) {
        RetroUtils retroUtils = new RetroUtils(getBaseContext());
        retroUtils.getApiClient().ADD_FAVOURITE_PET_RESPONSE_CALL(addFavouritePetRequest).enqueue(new Callback<AddFavouritePetResponse>() {
            @Override
            public void onResponse(Call<AddFavouritePetResponse> call, Response<AddFavouritePetResponse> response) {
                if (response == null || response.body() == null) {
                    Toast.makeText(getBaseContext(), "Server Error", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("200")) {
                    Toast.makeText(ShowFullPetProfileAfterSearchActivity.this, "Added Favourite", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(ShowFullPetProfileAfterSearchActivity.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("400")) {
                    Toast.makeText(ShowFullPetProfileAfterSearchActivity.this, "Invalid Request Parameter", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddFavouritePetResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Service failure.Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
