package com.animalhusbandry.petprofile;

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
import com.animalhusbandry.model.getallpetprofilesmodel.GetAllPetProfilesResponse;
import com.animalhusbandry.retrofit.RetroUtils;

import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.animalhusbandry.R.id.ivCircularImageView;

public class ShowFullPetProfile extends AppCompatActivity {
    protected GetAllPetProfilesResponse.Result userPetArrayList;
    protected String strPetName, strBloodline, strOwnerMobileNumber, strRegistration, strAge, strColor, strLocation, strBreed, strAnyOther,
            strPetId, strImageUrl, strGender, strComment, strUserId, strUserIdofPetProfileOpened, strPetIdofPetProfileOpened;
    protected GetAllPetProfilesResponse.PetVaccinationsList[] userPetVaccinationArrayList;
    protected TextView tvPetName, tvBloodline, tvRegistration, tvAge, tvColor, tvLocation, tvBreed, tvAnyOther, tvOwnerMobileNumber, ivBtnAddComment;
    protected EditText leavecomment;
    protected CheckBox checkboxMale, checkboxFemale, checkboxDHPP, checkboxRabies, checkboxParvoVirus, checkboxNone;
    protected ImageView ivAddImage, ivFavourite;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_pet_profile);
        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton backBtn=(ImageButton)toolbar.findViewById(R.id.backBtn);
        TextView toolbar_title=(TextView)toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Pet profile");
        setSupportActionBar(toolbar);
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        userPetArrayList = (GetAllPetProfilesResponse.Result) bundle.getSerializable("petProfileUserClicked");
        userPetVaccinationArrayList = userPetArrayList.getPetVaccinationsList();
        strUserIdofPetProfileOpened = userPetArrayList.getUserId();
        strPetIdofPetProfileOpened = userPetArrayList.getPetId();
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
        editor.putString("strPetIdofPetProfileOpened", strPetIdofPetProfileOpened);
        editor.putString("strUserIdofPetProfileOpened", strUserIdofPetProfileOpened);
        editor.putString("PetId", strPetId);
        editor.commit();
        backBtn.setClickable(true);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checkboxNone = (CheckBox) findViewById(R.id.checkboxNone);
        if (strGender.equals("Male")) {
            checkboxMale.setChecked(true);
        } else if (strGender.equals("Female")) {
            checkboxFemale.setChecked(true);
        }
        for (int i = 0; i < userPetVaccinationArrayList.length; i++) {
            Log.e("@@@@@@@@", userPetVaccinationArrayList[i] + "");
            if (userPetVaccinationArrayList[i].getName().equals("None")) {
                checkboxNone.setChecked(true);
            } else {

                if (userPetVaccinationArrayList[i].getName().equals("DHPP")) {
                    checkboxDHPP.setChecked(true);
                } else if (userPetVaccinationArrayList[i].getName().equals("Rabies")) {
                    checkboxRabies.setChecked(true);
                } else if (userPetVaccinationArrayList[i].getName().equals("Parvo virus")) {
                    checkboxParvoVirus.setChecked(true);
                } else {
                    tvAnyOther.setText(userPetVaccinationArrayList[i].getName());
                }
            }
        }

        new DownloadImageTask(ivAddImage).execute(strImageUrl);
        ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivFavourite.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.ic_favorite).getConstantState())) {

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
                } else {
                    Toast.makeText(ShowFullPetProfile.this, "Already added to favourites", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivBtnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strComment = leavecomment.getText().toString();

                if (!(leavecomment.getText().toString().equals(""))) {
                    callServiceMethod();
                } else {
                    leavecomment.setError("Write a message please");
                }
            }
        });
    }

    private void callServiceMethod() {
        SharedPreferences sharedPreferences = getSharedPreferences("Option", MODE_PRIVATE);
        SharedPreferences sharedPreferencesLogin = getSharedPreferences("Options", MODE_PRIVATE);
        strUserId = sharedPreferencesLogin.getString("strUserId", "");
        strPetId = sharedPreferences.getString("PetId", "");
        AddMessageRequest addMessageRequest = new AddMessageRequest();
      /*  String strGetPetIdofPetProfileOpened = sharedPreferences.getString("strPetIdofPetProfileOpened", "");
        String strGetUserIdofPetProfileOpened = sharedPreferences.getString("strUserIdofPetProfileOpened", "");
        Log.e("#PETIDProfileOpened%#%@", strGetPetIdofPetProfileOpened + "");
        Log.e("#UserIDProfileOpened%@", strGetUserIdofPetProfileOpened + "");*/
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
                    Toast.makeText(ShowFullPetProfile.this, "Message sent", Toast.LENGTH_SHORT).show();
                    leavecomment.getText().clear();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(ShowFullPetProfile.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
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
                    ivFavourite.setTag(R.drawable.ic_1498812687_like);
                    ivFavourite.setBackgroundResource(R.drawable.ic_1498812687_like);
                    Toast.makeText(ShowFullPetProfile.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(ShowFullPetProfile.this, "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("400")) {
                    Toast.makeText(ShowFullPetProfile.this, "Invalid Request Parameter", Toast.LENGTH_SHORT).show();
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
