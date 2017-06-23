package com.animalhusbandry.petprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.animalhusbandry.R;
import com.animalhusbandry.model.GetAllFavouritePetByUserIdResponse;
import com.animalhusbandry.model.getallpetprofilesmodel.GetAllPetProfilesResponse;

public class ShowFavouritePetProfileActivity extends AppCompatActivity {
    protected GetAllFavouritePetByUserIdResponse.Result userFavouritePetArrayList;
    protected String strPetName, strBloodline, strOwnerMobileNumber, strRegistration, strAge, strColor, strLocation, strBreed, strAnyOther,
            strPetId, strImageUrl, strGender;
    protected GetAllPetProfilesResponse.PetVaccinationsList[] userPetVaccinationArrayList;
    protected TextView tvPetName, tvBloodline, tvRegistration, tvAge, tvColor, tvLocation, tvBreed, tvAnyOther, tvOwnerMobileNumber;
    protected CheckBox checkboxMale, checkboxFemale, checkboxDHPP, checkboxRabies, checkboxParvoVirus, checkboxNone;
    protected ImageView ivAddImage, ivFavourite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favourite_pet_profile);
    }
}
