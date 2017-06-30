package com.animalhusbandry.petprofile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.dashboard.DashboardActivity;
import com.animalhusbandry.model.getpetprofilesofusermodel.GetPetProfilesOfUserResponse;
import com.animalhusbandry.model.updatepetprofile.PetVaccinationsList;
import com.animalhusbandry.model.updatepetprofile.UpdatePetProfileRequest;
import com.animalhusbandry.model.updatepetprofile.UpdatePetProfileResponse;
import com.animalhusbandry.retrofit.RetroUtils;
import com.animalhusbandry.utils.setToolbar;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.animalhusbandry.R.id.tvBloodline;

public class UpdatePetProfileActivity extends AppCompatActivity {
    private static final int REQUEST_READ_PERMISSION = 7860;
    private static final int REQUEST_CAMERA_PERMISSION = 1888;
    private static final int CAMERA_REQUEST = 1100;
    private static final int GALERY_REQUEST = 2700;
    public CheckBox checkboxMale, checkboxFemale, checkboxDHPP, checkboxRabies, checkboxParvoVirus, checkboxNone;
    public ArrayList<PetVaccinationsList> arrrayListVaccination;
    public Bitmap bitmap;
    public String encodedImage;
    public GetPetProfilesOfUserResponse.Result userPetArrayList;
    public GetPetProfilesOfUserResponse.PetVaccinationsList[] userPetVaccinationArrayList;
    public EditText etPetName, etBloodline, etRegistration, etOwnerMobileNumber, etAge, etColor, etLocation, etBreed, etAnyOtherEditable, etAnyOtherNonEditable, etDHPP, etRabies, etParvovirus, etNone, etGender;
    public Button btnEditProfile, btnUpdateProfile;
    public String strPetName, strBloodline, strOwnerMobileNumber, strRegistration, strAge, strColor, strLocation, strBreed, strAnyOther, strGender,
            strPetId, strImageUrl;
    public ProgressDialog ringProgressDialog;
    public AlertDialog cameraDialog;
    public Toolbar toolbar;
    public ImageView ivCircularImageView;
    public UpdatePetProfileActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user_pet_profile);
        Bundle extras = new Bundle();
        extras = getIntent().getExtras();
        userPetArrayList = (GetPetProfilesOfUserResponse.Result) extras.getSerializable("petResultListOfUser");
        userPetVaccinationArrayList = userPetArrayList.getPetVaccinationsList();
        /*        userPetArrayList = (GetPetProfilesOfUserResponse.PetVaccinationsList[]) extras.getSerializable("petVaccinationsListOfUser");
          String strVaccinationResponse = String.valueOf(userPetArrayList[0]);*/
        init();
    }

    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setToolbar.setToolbar(UpdatePetProfileActivity.this, "Update pet profile", true);
        }
        configureToolbar();
        nonEditableEditText();
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnEditProfile.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.ic_edit_black_24dp).getConstantState())) {
                    btnEditProfile.setTag(R.drawable.ic_close);
                    btnEditProfile.setBackgroundResource(R.drawable.ic_close);
                    editableEditTexts();
                } else {
                    btnEditProfile.setTag(R.drawable.ic_edit_black_24dp);
                    btnEditProfile.setBackgroundResource(R.drawable.ic_edit_black_24dp);
                    nonEditableEditText();
                }
            }
        });
    }

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
            ImageButton backBtn = (ImageButton) toolbar.findViewById(R.id.backBtn);
            backBtn.setEnabled(true);
            textView.setEnabled(true);
            toolbar.setEnabled(true);
        }
    }

    private void editableEditTexts() {
        LinearLayout linearLayoutEditTextGender = (LinearLayout) findViewById(R.id.linearLayoutEditTextGender);
        LinearLayout linearLayoutEditTextVaccination = (LinearLayout) findViewById(R.id.linearLayoutEditTextVaccination);
        linearLayoutEditTextGender.setEnabled(false);
        linearLayoutEditTextGender.setClickable(false);
        linearLayoutEditTextGender.setVisibility(View.GONE);
        linearLayoutEditTextVaccination.setEnabled(false);
        linearLayoutEditTextVaccination.setClickable(false);
        linearLayoutEditTextVaccination.setVisibility(View.GONE);
        LinearLayout linearLayoutCheckBoxGender = (LinearLayout) findViewById(R.id.linearLayoutCheckBoxGender);
        LinearLayout linearLayoutCheckBoxVaccination = (LinearLayout) findViewById(R.id.linearLayoutCheckBoxVaccination);
        linearLayoutCheckBoxGender.setEnabled(true);
        linearLayoutCheckBoxGender.setClickable(true);
        linearLayoutCheckBoxGender.setVisibility(View.VISIBLE);
        linearLayoutCheckBoxVaccination.setEnabled(true);
        linearLayoutCheckBoxVaccination.setClickable(true);
        linearLayoutCheckBoxVaccination.setVisibility(View.VISIBLE);
        checkboxMale = (CheckBox) findViewById(R.id.checkboxMale);
        checkboxMale.setClickable(true);
        checkboxMale.setEnabled(true);
        checkboxFemale = (CheckBox) findViewById(R.id.checkboxFemale);
        checkboxFemale.setClickable(true);
        checkboxFemale.setEnabled(true);
        checkboxDHPP = (CheckBox) findViewById(R.id.checkboxDHPP);
        checkboxDHPP.setClickable(true);
        checkboxDHPP.setEnabled(true);
        checkboxRabies = (CheckBox) findViewById(R.id.checkboxRabies);
        checkboxRabies.setClickable(true);
        checkboxRabies.setEnabled(true);
        checkboxParvoVirus = (CheckBox) findViewById(R.id.checkboxParvoVirus);
        checkboxParvoVirus.setClickable(true);
        checkboxParvoVirus.setEnabled(true);
        checkboxNone = (CheckBox) findViewById(R.id.checkboxNone);
        checkboxNone.setClickable(true);
        checkboxNone.setEnabled(true);
        ivCircularImageView = (ImageView) findViewById(R.id.ivCircularImageView);
        ivCircularImageView.setEnabled(true);
        ivCircularImageView.setClickable(true);
        ivCircularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
        etPetName = (EditText) findViewById(R.id.tvPetName);
        etPetName.setEnabled(true);
        etPetName.setClickable(true);
        etBloodline = (EditText) findViewById(tvBloodline);
        etBloodline.setEnabled(true);
        etBloodline.setClickable(true);
        etRegistration = (EditText) findViewById(R.id.tvRegistration);
        etRegistration.setEnabled(true);
        etRegistration.setClickable(true);
        etOwnerMobileNumber = (EditText) findViewById(R.id.tvOwnerMobileNumber);
        etOwnerMobileNumber.setEnabled(true);
        etOwnerMobileNumber.setClickable(true);
        etAge = (EditText) findViewById(R.id.tvAge);
        etAge.setEnabled(true);
        etAge.setClickable(true);
        etGender = (EditText) findViewById(R.id.tvGender);
        etGender.setEnabled(true);
        etGender.setClickable(true);
        etColor = (EditText) findViewById(R.id.tvColor);
        etColor.setEnabled(true);
        etColor.setClickable(true);
        etLocation = (EditText) findViewById(R.id.tvLocation);
        etLocation.setEnabled(true);
        etLocation.setClickable(true);
        etBreed = (EditText) findViewById(R.id.tvBreed);
        etBreed.setEnabled(true);
        etBreed.setClickable(true);
        etAnyOtherEditable = (EditText) findViewById(R.id.tvAnyOtherEditable);
        etAnyOtherEditable.setEnabled(true);
        etAnyOtherEditable.setClickable(true);
        etDHPP = (EditText) findViewById(R.id.tvDHPP);
        etDHPP.setEnabled(true);
        etDHPP.setClickable(true);
        etNone = (EditText) findViewById(R.id.tvNone);
        etNone.setEnabled(true);
        etNone.setClickable(true);
        etParvovirus = (EditText) findViewById(R.id.tvParvovirus);
        etParvovirus.setEnabled(true);
        etParvovirus.setClickable(true);
        etRabies = (EditText) findViewById(R.id.tvRabies);
        etRabies.setEnabled(true);
        etRabies.setClickable(true);
        btnUpdateProfile = (Button) findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setVisibility(View.VISIBLE);
        btnUpdateProfile.setEnabled(true);
        btnUpdateProfile.setClickable(true);
        arrrayListVaccination = new ArrayList<PetVaccinationsList>();
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivCircularImageView.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "Add pet image", Toast.LENGTH_SHORT).show();
                } else if (etPetName.getText().toString().isEmpty()) {
                    etPetName.setError("Enter pet name");
                    etPetName.requestFocus();
                } else if (etPetName.getText().toString().length() < 3) {
                    etPetName.setError("Enter minimum 3 characters");
                    etPetName.requestFocus();
                } else if (etBreed.getText().toString().isEmpty()) {
                    etBreed.setError("Enter pet breed");
                    etBreed.requestFocus();
                } else if (etColor.getText().toString().length() < 3) {
                    etColor.setError("Enter minimum 3 characters");
                    etColor.requestFocus();
                } else if (etColor.getText().toString().isEmpty()) {
                    etColor.setError("Enter pet breed");
                    etColor.requestFocus();
                } else if (etBreed.getText().toString().length() < 3) {
                    etBreed.setError("Enter minimum 3 characters");
                    etBreed.requestFocus();
                } else if (etBloodline.getText().toString().isEmpty()) {
                    etBloodline.setError("Enter bloodline");
                    etBloodline.requestFocus();
                } else if (etBloodline.getText().toString().length() < 3) {
                    etBloodline.setError("Enter minimum 3 characters");
                    etBloodline.requestFocus();
                } else if (etLocation.getText().toString().isEmpty()) {
                    etLocation.setError("Enter location");
                    etLocation.requestFocus();
                } else if (etLocation.getText().toString().length() < 3) {
                    etLocation.setError("Enter minimum 3 characters");
                    etLocation.requestFocus();
                } else if (etRegistration.getText().toString().isEmpty()) {
                    etRegistration.setError("Enter Registration");
                    etRegistration.requestFocus();
                } else if (etRegistration.getText().toString().length() < 3) {
                    etRegistration.setError("Enter minimum 4 characters");
                    etRegistration.requestFocus();
                } else if (etOwnerMobileNumber.getText().toString().isEmpty()) {
                    etOwnerMobileNumber.setError("Enter Mobile number");
                    etOwnerMobileNumber.requestFocus();
                } else if (etOwnerMobileNumber.getText().toString().length() < 10) {
                    etOwnerMobileNumber.setError("Enter minimum 10 digits");
                    etOwnerMobileNumber.requestFocus();
                } else if (etAge.getText().toString().isEmpty()) {
                    etAge.setError("Enter Age");
                    etAge.requestFocus();
                } else if (etAge.getText().toString().length() > 2) {
                    etAge.setError("Entered age can't be possible ");
                    etAge.requestFocus();
                } else if (!(checkboxFemale.isChecked() || checkboxMale.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Please select gender", Toast.LENGTH_SHORT).show();
                } else if (checkboxMale.isChecked() && checkboxFemale.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Your pet can't be both male and female!!", Toast.LENGTH_SHORT).show();
                } else if (!(checkboxRabies.isChecked() || checkboxDHPP.isChecked() || checkboxParvoVirus.isChecked() || checkboxNone.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Vaccination details missing", Toast.LENGTH_SHORT).show();
                } else if ((checkboxRabies.isChecked() && checkboxDHPP.isChecked() && checkboxParvoVirus.isChecked() && checkboxNone.isChecked())) {
                    Toast.makeText(getApplicationContext(), "No need to select'None'with other options", Toast.LENGTH_SHORT).show();
                } else {
                    strPetName = etPetName.getText().toString();
                    strBloodline = etBloodline.getText().toString();
                    strRegistration = etRegistration.getText().toString();
                    strAge = etAge.getText().toString();
                    strColor = etColor.getText().toString();
                    strLocation = etLocation.getText().toString();
                    strBreed = etBreed.getText().toString();
                    strOwnerMobileNumber = etOwnerMobileNumber.getText().toString();
                    strAnyOther = etAnyOtherEditable.getText().toString();
                    if (checkboxMale.isChecked()) {
                        strGender = "Male";
                    } else {
                        strGender = "Female";
                    }
                    if (checkboxDHPP.isChecked()) {
                        PetVaccinationsList object = new PetVaccinationsList();
                        arrrayListVaccination.clear();
                        object.setName("DHPP");
                        arrrayListVaccination.add(object);

                    }
                    if (checkboxRabies.isChecked()) {
                        PetVaccinationsList object = new PetVaccinationsList();
                        object.setName("Rabies");
                        arrrayListVaccination.add(object);
                    }
                    if (checkboxParvoVirus.isChecked()) {
                        PetVaccinationsList object = new PetVaccinationsList();
                        object.setName("Parvo virus");
                        arrrayListVaccination.add(object);
                    }
                    if (checkboxNone.isChecked()) {
                        PetVaccinationsList object = new PetVaccinationsList();
                        object.setName("None");
                        arrrayListVaccination.add(object);
                    }
                    if (!(strAnyOther.isEmpty())) {
                        PetVaccinationsList object = new PetVaccinationsList();
                        object.setName(strAnyOther);
                        arrrayListVaccination.add(object);
                    }
                    ringProgressDialog = ProgressDialog.show(UpdatePetProfileActivity.this, "Please wait ...", "Creating pet profile", true);
                    ringProgressDialog.setCancelable(false);
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
                    UpdatePetProfileRequest updatePetProfilesRequest = new UpdatePetProfileRequest();
                    updatePetProfilesRequest.setUserId(strUserId);
                    updatePetProfilesRequest.setPetId(strPetId);
                    updatePetProfilesRequest.setName(strPetName);
                    updatePetProfilesRequest.setAge(strAge);
                    updatePetProfilesRequest.setBloodLine(strBloodline);
                    updatePetProfilesRequest.setBreed(strBreed);
                    updatePetProfilesRequest.setColor(strColor);
                    updatePetProfilesRequest.setGender(strGender);
                    updatePetProfilesRequest.setImage(encodedImage);
                    updatePetProfilesRequest.setLocation(strLocation);
                    updatePetProfilesRequest.setMicrochipNumber(strRegistration);
                    updatePetProfilesRequest.setOwnerMobileNumber(strOwnerMobileNumber);
                    updatePetProfilesRequest.setPetVaccinationsList(arrrayListVaccination);
                    doUpdatePetProfile(updatePetProfilesRequest);
                }
            }
        });

    }

    private void doUpdatePetProfile(UpdatePetProfileRequest updatePetProfilesRequest) {

        RetroUtils retrofitUtils = new RetroUtils(getBaseContext());
        retrofitUtils.getApiClient().updatePetProfile(updatePetProfilesRequest).enqueue(new Callback<UpdatePetProfileResponse>() {
            @Override
            public void onResponse(Call<UpdatePetProfileResponse> call, Response<UpdatePetProfileResponse> response) {
                ringProgressDialog.dismiss();
                Log.e("###PetProfile###", response.body().getResponse().getResult().getPetVaccinationsList() + "");
                if (response.body().getResponse().getCode().equals("200")) {
                    Toast.makeText(getApplicationContext(), "Pet profile updated sucessfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdatePetProfileActivity.this, DashboardActivity.class);
                    startActivity(intent);
                } else if (response.body().getResponse().getCode().equals("404")) {
                    Toast.makeText(getApplicationContext(), "Pet does not found", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(getApplicationContext(), "Your session has been expired. Please login again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatePetProfileResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PERMISSION);
            } else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                popUpmenu();
            }
        } else {
            popUpmenu();
        }
    }

    private void popUpmenu() {
        cameraDialog = new AlertDialog.Builder(UpdatePetProfileActivity.this).create();
        LayoutInflater layoutInflater = UpdatePetProfileActivity.this.getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.camera_dialog, null, false);
        cameraDialog.setView(dialogView);
        cameraDialog.setTitle("Choose One");
        cameraDialog.setCancelable(true);
        LinearLayout mCamerabtn = (LinearLayout) dialogView.findViewById(R.id.cameradialogbtn);
        LinearLayout mGallerybtn = (LinearLayout) dialogView.findViewById(R.id.gallerydialogbtn);
        LinearLayout canceldialogbtn = (LinearLayout) dialogView.findViewById(R.id.canceldialogbtn);
        mCamerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
                cameraDialog.dismiss();
            }
        });
        mGallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromGallery();
                cameraDialog.dismiss();
            }
        });
        canceldialogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialog.dismiss();
            }
        });
        cameraDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == UpdatePetProfileActivity.RESULT_OK) {
            if (data != null) {

                File dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File dir_temp = new File(String.valueOf(dirPath));
                if (!dir_temp.exists())
                    dir_temp.mkdirs();

                File dest = new File(dir_temp, new Date() + ".png");
                Uri fileUri = Uri.fromFile(dest);
                bitmap = (Bitmap) data.getExtras().get("data");
                try {
                    CropImage.activity(fileUri).setAspectRatio(1, 1).setFixAspectRatio(true).start(UpdatePetProfileActivity.this);
                    FileOutputStream out = new FileOutputStream(dest);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getBaseContext(), "ImageSaved", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == GALERY_REQUEST && resultCode == UpdatePetProfileActivity.RESULT_OK) {
            Bitmap bm;
            if (data != null) {

                try {
                    final Uri imageUri = data.getData();
                    CropImage.activity(imageUri).setAspectRatio(3, 4).setFixAspectRatio(true).start(UpdatePetProfileActivity.this);


                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);


                    getResizedBitmap(bm, 65);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == UpdatePetProfileActivity.RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(UpdatePetProfileActivity.this.getContentResolver(), resultUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 65, byteArrayOutputStream);
                    encodedImage = encodeImage(bitmap);
                    Log.e("%%ENCODEDCAPTUREIMAGE##", encodedImage + "");
                    ivCircularImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else {
        }
    }

    private Bitmap getResizedBitmap(Bitmap selectedImage, int maxSize) {
        int width = selectedImage.getWidth();
        int height = selectedImage.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(selectedImage, width, height, true);
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.NO_WRAP);
        return encImage;
    }

    private void selectFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, GALERY_REQUEST);
    }

    private void takePicture() {
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageIntent, CAMERA_REQUEST);
    }

    private void nonEditableEditText() {
        LinearLayout linearLayoutCheckBoxGender = (LinearLayout) findViewById(R.id.linearLayoutCheckBoxGender);
        LinearLayout linearLayoutCheckBoxVaccination = (LinearLayout) findViewById(R.id.linearLayoutCheckBoxVaccination);
        linearLayoutCheckBoxGender.setEnabled(false);
        linearLayoutCheckBoxGender.setClickable(false);
        linearLayoutCheckBoxGender.setVisibility(View.GONE);
        linearLayoutCheckBoxVaccination.setEnabled(false);
        linearLayoutCheckBoxVaccination.setClickable(false);
        linearLayoutCheckBoxVaccination.setVisibility(View.GONE);
        LinearLayout linearLayoutEditTextGender = (LinearLayout) findViewById(R.id.linearLayoutEditTextGender);
        LinearLayout linearLayoutEditTextVaccination = (LinearLayout) findViewById(R.id.linearLayoutEditTextVaccination);
        linearLayoutEditTextGender.setEnabled(true);
        linearLayoutEditTextGender.setClickable(true);
        linearLayoutEditTextGender.setVisibility(View.VISIBLE);
        linearLayoutEditTextVaccination.setEnabled(true);
        linearLayoutEditTextVaccination.setClickable(true);
        linearLayoutEditTextVaccination.setVisibility(View.VISIBLE);
        etPetName = (EditText) findViewById(R.id.tvPetName);
        etPetName.setEnabled(false);
        etBloodline = (EditText) findViewById(tvBloodline);
        etBloodline.setEnabled(false);
        etRegistration = (EditText) findViewById(R.id.tvRegistration);
        etRegistration.setEnabled(false);
        etOwnerMobileNumber = (EditText) findViewById(R.id.tvOwnerMobileNumber);
        etOwnerMobileNumber.setEnabled(false);
        etAge = (EditText) findViewById(R.id.tvAge);
        etAge.setEnabled(false);
        etGender = (EditText) findViewById(R.id.tvGender);
        etGender.setEnabled(false);
        etColor = (EditText) findViewById(R.id.tvColor);
        etColor.setEnabled(false);
        etLocation = (EditText) findViewById(R.id.tvLocation);
        etLocation.setEnabled(false);
        etBreed = (EditText) findViewById(R.id.tvBreed);
        etBreed.setEnabled(false);
        etAnyOtherNonEditable = (EditText) findViewById(R.id.tvAnyOtherNonEditable);
        etAnyOtherNonEditable.setEnabled(false);
        etDHPP = (EditText) findViewById(R.id.tvDHPP);
        etDHPP.setEnabled(false);
        etNone = (EditText) findViewById(R.id.tvNone);
        etNone.setEnabled(false);
        etParvovirus = (EditText) findViewById(R.id.tvParvovirus);
        etParvovirus.setEnabled(false);
        etRabies = (EditText) findViewById(R.id.tvRabies);
        etRabies.setEnabled(false);
        btnUpdateProfile = (Button) findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setEnabled(false);
        btnUpdateProfile.setVisibility(View.GONE);
        Log.e("STRINGIMAGE", strImageUrl + "");
        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        ivCircularImageView = (ImageView) findViewById(R.id.ivCircularImageView);
        ivCircularImageView.setClickable(false);
        strPetName = userPetArrayList.getName().toString();
        strBloodline = userPetArrayList.getBloodLine().toString();
        strRegistration = userPetArrayList.getMicrochipNumber().toString();
        strAge = userPetArrayList.getAge();
        strColor = userPetArrayList.getColor();
        strLocation = userPetArrayList.getLocation();
        strBreed = userPetArrayList.getBreed();
        strGender = userPetArrayList.getGender();
        strPetId = userPetArrayList.getPetId();
        Log.e("PET ID", strPetId + "");
        strOwnerMobileNumber = userPetArrayList.getOwnerMobileNumber();
        strImageUrl = userPetArrayList.getImageUrl();
        etPetName.setText(strPetName);
        etAge.setText(strAge);
        etBloodline.setText(strBloodline);
        etBreed.setText(strBreed);
        etColor.setText(strColor);
        etGender.setText(strGender);
        etLocation.setText(strLocation);
        etRegistration.setText(strLocation);
        /*checkboxDHPP = (CheckBox) findViewById(R.id.checkboxDHPP);
        checkboxDHPP.setClickable(false);
        checkboxFemale = (CheckBox) findViewById(R.id.checkboxFemale);
        checkboxFemale.setClickable(false);
        checkboxMale = (CheckBox) findViewById(R.id.checkboxMale);
        checkboxMale.setClickable(false);
        checkboxNone = (CheckBox) findViewById(R.id.checkboxNone);
        checkboxNone.setClickable(false);
        checkboxParvoVirus = (CheckBox) findViewById(R.id.checkboxParvoVirus);
        checkboxParvoVirus.setClickable(false);
        checkboxRabies = (CheckBox) findViewById(R.id.checkboxRabies);
        checkboxRabies.setClickable(false);*/
        etOwnerMobileNumber.setText(strOwnerMobileNumber);
        for (int i = 0; i < userPetVaccinationArrayList.length; i++) {
            Log.e("@@@@@@@@", userPetVaccinationArrayList[i] + "");
            if (userPetVaccinationArrayList[i].getName().equals("None")) {
                etDHPP.setText("None");
              /*  etRabies.setText("None");
                etParvovirus.setText("None");
                etNone.setText("None");*/

              etRabies.setVisibility(View.GONE);
                etParvovirus.setVisibility(View.GONE);
                etNone.setVisibility(View.GONE);
            } else {
                etRabies.setVisibility(View.VISIBLE);
                etParvovirus.setVisibility(View.VISIBLE);
                etNone.setVisibility(View.VISIBLE);
                if (userPetVaccinationArrayList[i].getName().equals("DHPP")) {
                    etDHPP.setText(userPetVaccinationArrayList[i].getName());
                } else if (userPetVaccinationArrayList[i].getName().equals("Rabies")) {
                    etRabies.setText(userPetVaccinationArrayList[i].getName());
                } else if (userPetVaccinationArrayList[i].getName().equals("Parvo virus")) {
                    etParvovirus.setText(userPetVaccinationArrayList[i].getName());
                } else if (userPetVaccinationArrayList[i].getName().equals("None")) {
                    etNone.setText(userPetVaccinationArrayList[i].getName());
                } else {
                    etAnyOtherNonEditable.setText(userPetVaccinationArrayList[i].getName());
                }
            }
        }
        new DownloadImageTask(ivCircularImageView).execute(strImageUrl);
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