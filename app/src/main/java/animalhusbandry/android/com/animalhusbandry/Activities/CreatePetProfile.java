package animalhusbandry.android.com.animalhusbandry.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import animalhusbandry.android.com.animalhusbandry.Activities.CreatePetProfileParams.CreatePetProfileRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.CreatePetProfileParams.CreatePetProfileResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.RetroFit.RetroUtils;
import animalhusbandry.android.com.animalhusbandry.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class CreatePetProfile extends AppCompatActivity {
    EditText etPetName, etBloodline, etRegistration, etAge, etColor, etLocation, etBreed, etAnyOther;
    Button btnCreateProfile;
    CheckBox checkboxMale, checkboxFemale, checkboxDHPP, checkboxRabies, checkboxParvoVirus, checkboxNone;
    String strPetName, strPetBloodline, strPetRegistration, strPetAge, strPetColor, strPetLocation, strPetBreed, strPetAnyOther, strGender;
    public ProgressDialog ringProgressDialog;
    ImageButton ivBtnAddImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pet_profile);
        init();
    }

    public void init() {
        ivBtnAddImage = (ImageButton) findViewById(R.id.ivBtnAddImage);
        etPetName = (EditText) findViewById(R.id.etPetName);
        etBloodline = (EditText) findViewById(R.id.etBloodline);
        etRegistration = (EditText) findViewById(R.id.etRegistration);
        etAge = (EditText) findViewById(R.id.etAge);
        etColor = (EditText) findViewById(R.id.etColor);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etBreed = (EditText) findViewById(R.id.etBreed);
        etAnyOther = (EditText) findViewById(R.id.etAnyOther);
        checkboxMale = (CheckBox) findViewById(R.id.checkboxMale);
        checkboxFemale = (CheckBox) findViewById(R.id.checkboxFemale);
        checkboxDHPP = (CheckBox) findViewById(R.id.checkboxDHPP);
        checkboxRabies = (CheckBox) findViewById(R.id.checkboxRabies);
        checkboxParvoVirus = (CheckBox) findViewById(R.id.checkboxParvoVirus);
        btnCreateProfile = (Button) findViewById(R.id.btnCreateProfile);
        checkboxNone = (CheckBox) findViewById(R.id.checkboxNone);
        final ArrayList<CreatePetProfileRequest.PetVaccinationsList> arrrayListVaccination = new ArrayList<CreatePetProfileRequest.PetVaccinationsList>();
        final CreatePetProfileRequest.PetVaccinationsList itemVaccinationGender = new CreatePetProfileRequest.PetVaccinationsList();
        final CreatePetProfileRequest.PetVaccinationsList itemVaccinationDHPP = new CreatePetProfileRequest.PetVaccinationsList();
        final CreatePetProfileRequest.PetVaccinationsList itemVaccinationRabies = new CreatePetProfileRequest.PetVaccinationsList();
        final CreatePetProfileRequest.PetVaccinationsList itemVaccinationParvo = new CreatePetProfileRequest.PetVaccinationsList();
        final CreatePetProfileRequest.PetVaccinationsList itemVaccinationAnyOther = new CreatePetProfileRequest.PetVaccinationsList();
        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPetName.getText().toString().isEmpty()) {
                    etPetName.setError("");
                    etPetName.requestFocus();
                } else if (etBreed.getText().toString().isEmpty()) {
                    etBreed.setError("");
                    etBreed.requestFocus();
                } else if (etBloodline.getText().toString().isEmpty()) {
                    etBloodline.setError("");
                    etBloodline.requestFocus();
                } else if (etColor.getText().toString().isEmpty()) {
                    etColor.setError("");
                    etColor.requestFocus();
                } else if (etLocation.getText().toString().isEmpty()) {
                    etLocation.setError("");
                    etLocation.requestFocus();
                } else if (etRegistration.getText().toString().isEmpty()) {
                    etRegistration.setError("");
                    etRegistration.requestFocus();
                } else if (etAge.getText().toString().isEmpty()) {
                    etAge.setError("");
                    etAge.requestFocus();
                } else if (!(checkboxFemale.isChecked() || checkboxMale.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Please select gender", Toast.LENGTH_SHORT).show();
                } else if (checkboxMale.isChecked() && checkboxFemale.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Your pet can't be both male and female!!", Toast.LENGTH_SHORT).show();
                } else if (!(checkboxRabies.isChecked() || checkboxDHPP.isChecked() || checkboxParvoVirus.isChecked() || checkboxNone.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Heyy you can also select no one", Toast.LENGTH_SHORT).show();
                } else if ((checkboxRabies.isChecked() && checkboxDHPP.isChecked() && checkboxParvoVirus.isChecked() && checkboxNone.isChecked())) {
                    Toast.makeText(getApplicationContext(), "No need to select'None'with other options", Toast.LENGTH_SHORT).show();
                } else {
                    strPetName = etPetName.getText().toString();
                    strPetBloodline = etBloodline.getText().toString();
                    strPetRegistration = etRegistration.getText().toString();
                    strPetAge = etAge.getText().toString();
                    strPetColor = etColor.getText().toString();
                    strPetLocation = etLocation.getText().toString();
                    strPetBreed = etBreed.getText().toString();
                    strPetAnyOther = etAnyOther.getText().toString();
                    if (checkboxMale.isChecked()) {
                        itemVaccinationGender.setName("Male");
                    } else {
                        itemVaccinationGender.setName("Female");
                    }
                    if (checkboxDHPP.isChecked()) {
                        itemVaccinationDHPP.setName("DHPP");
                    } else {
                        itemVaccinationDHPP.setName("");
                    }
                    if (checkboxParvoVirus.isChecked()) {
                        itemVaccinationParvo.setName("Parvo Virus");
                    } else {
                        itemVaccinationParvo.setName("");
                    }
                    if (checkboxRabies.isChecked()) {
                        itemVaccinationRabies.setName("Rabies");
                    } else {
                        itemVaccinationRabies.setName("");
                    }
                    if (checkboxNone.isChecked()) {
                        itemVaccinationAnyOther.setName("None");
                    } else {
                        itemVaccinationAnyOther.setName("");
                    }
                    ringProgressDialog = ProgressDialog.show(CreatePetProfile.this, "Please wait ...", "Logging in", true);
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
                    final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
                    final String strUserId = sharedPreferences.getString("strUserId", "");
                    CreatePetProfileRequest createPetProfileRequest = new CreatePetProfileRequest();
                    createPetProfileRequest.setName(strPetName);
                    createPetProfileRequest.setUserId(strUserId);
                    createPetProfileRequest.setBreed(strPetBreed);
                    createPetProfileRequest.setBloodLine(strPetBloodline);
                    createPetProfileRequest.setColor(strPetColor);
                    createPetProfileRequest.setLocation(strPetLocation);
                    createPetProfileRequest.setAge(strPetAge);
                    createPetProfileRequest.setImage("no image");
                    createPetProfileRequest.setMicrochipNumber(strPetRegistration);
                    createPetProfileRequest.setOwnerMobileNumber("1212112");
                    createPetProfileRequest.setGender(strGender);
                    createPetProfileRequest.setPetVaccinationsList(arrrayListVaccination);
                    doCreatePetProfile(createPetProfileRequest);
                }
            }
        });
        ivBtnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void doCreatePetProfile(CreatePetProfileRequest createPetProfileRequest) {
        RetroUtils retroUtils = new RetroUtils(getApplicationContext());
        retroUtils.getApiClient().createPetProfile(createPetProfileRequest).enqueue(new Callback<CreatePetProfileResponse>() {
            @Override
            public void onResponse(Call<CreatePetProfileResponse> call, Response<CreatePetProfileResponse> response) {
                ringProgressDialog.dismiss();
                Log.e("###PetProfile###", response.body().getResponse().getCode() + "");
                if (response.body().getResponse().getCode().equals("200")) {
                    Toast.makeText(getApplicationContext(), "Pet profile created sucessfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreatePetProfile.this, Dashboard.class);
                    startActivity(intent);
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(getApplicationContext(), "You are not authorized to perform this operation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreatePetProfileResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
