package com.animalhusbandry.petprofile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.dashboard.Dashboard;
import com.animalhusbandry.model.CreatePetProfileRequest;
import com.animalhusbandry.model.CreatePetProfileResponse;
import com.animalhusbandry.model.PetVaccinationsList;
import com.animalhusbandry.retrofit.RetroUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class CreatePetProfile extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1100;
    private static final int GALERY_REQUEST = 2700;
    EditText etPetName, etBloodline, etRegistration, etAge, etColor, etLocation, etBreed, etAnyOther,etOwnerMobileNumber;
    Button btnCreateProfile;
    CheckBox checkboxMale, checkboxFemale, checkboxDHPP, checkboxRabies, checkboxParvoVirus, checkboxNone;
    String strPetName, strOwnerMobileNumber,strPetBloodline, strPetRegistration, strPetAge, strPetColor, strPetLocation, strPetBreed, strPetAnyOther, strGender;
    public ProgressDialog ringProgressDialog;
    public AlertDialog cameraDialog;
    ImageView ivAddImage;
    public Bitmap bitmap;
    public String encodedImage;
    private static final int REQUEST_READ_PERMISSION = 7860;
    private static final int REQUEST_CAMERA_PERMISSION = 1888;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_createpetprofile);
        init();
    }

    public void init() {
        ivAddImage = (ImageView) findViewById(R.id.ivCircularImageView);
        etPetName = (EditText) findViewById(R.id.etPetName);
        etBloodline = (EditText) findViewById(R.id.etBloodline);
        etRegistration = (EditText) findViewById(R.id.etRegistration);
        etAge = (EditText) findViewById(R.id.etAge);
        etColor = (EditText) findViewById(R.id.etColor);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etBreed = (EditText) findViewById(R.id.etBreed);
        etAnyOther = (EditText) findViewById(R.id.etAnyOtherEditable);
        etOwnerMobileNumber=(EditText)findViewById(R.id.etOwnerMobileNumber);
        checkboxMale = (CheckBox) findViewById(R.id.checkboxMale);
        checkboxFemale = (CheckBox) findViewById(R.id.checkboxFemale);
        checkboxDHPP = (CheckBox) findViewById(R.id.checkboxDHPP);
        checkboxRabies = (CheckBox) findViewById(R.id.checkboxRabies);
        checkboxParvoVirus = (CheckBox) findViewById(R.id.checkboxParvoVirus);
        btnCreateProfile = (Button) findViewById(R.id.btnCreateProfile);
        checkboxNone = (CheckBox) findViewById(R.id.checkboxNone);


        final ArrayList<PetVaccinationsList> arrrayListVaccination = new ArrayList<PetVaccinationsList>();


        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ivAddImage.getDrawable() == null) {
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
                }  else if (etOwnerMobileNumber.getText().toString().isEmpty()) {
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
                    strPetBloodline = etBloodline.getText().toString();
                    strPetRegistration = etRegistration.getText().toString();
                    strPetAge = etAge.getText().toString();
                    strPetColor = etColor.getText().toString();
                    strPetLocation = etLocation.getText().toString();
                    strPetBreed = etBreed.getText().toString();
                    strOwnerMobileNumber=etOwnerMobileNumber.getText().toString();
                    strPetAnyOther = etAnyOther.getText().toString();
                    if (checkboxMale.isChecked()) {
                        strGender = "Male";
                    } else {
                        strGender = "Female";
                    }
                    if (checkboxDHPP.isChecked()) {
                        PetVaccinationsList object = new PetVaccinationsList();
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
                    if (!(strPetAnyOther.isEmpty())) {
                        PetVaccinationsList object = new PetVaccinationsList();
                        object.setName(strPetAnyOther);
                        arrrayListVaccination.add(object);
                    }
                    ringProgressDialog = ProgressDialog.show(CreatePetProfile.this, "Please wait ...", "Creating pet profile", true);
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
                    CreatePetProfileRequest createPetProfileRequest = new CreatePetProfileRequest();
                    createPetProfileRequest.setName(strPetName);
                    createPetProfileRequest.setUserId(strUserId);
                    createPetProfileRequest.setBreed(strPetBreed);
                    createPetProfileRequest.setBloodLine(strPetBloodline);
                    createPetProfileRequest.setColor(strPetColor);
                    createPetProfileRequest.setLocation(strPetLocation);
                    createPetProfileRequest.setAge(strPetAge);
                    createPetProfileRequest.setImage(encodedImage);
                    Log.e("%%SETIMAGE$$", encodedImage + "");
                    createPetProfileRequest.setMicrochipNumber(strPetRegistration);
                    createPetProfileRequest.setOwnerMobileNumber(strOwnerMobileNumber);
                    createPetProfileRequest.setGender(strGender);
                    createPetProfileRequest.setPetVaccinationsList(arrrayListVaccination);
                    doCreatePetProfile(createPetProfileRequest);
                    Log.e("VACCINATION  1111", arrrayListVaccination.get(0) + "");
                }
            }
        });
        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();

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


    public void takePicture() {
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageIntent, CAMERA_REQUEST);
    }

    private void selectFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, GALERY_REQUEST);

    }

    void popUpmenu() {
        cameraDialog = new AlertDialog.Builder(CreatePetProfile.this).create();
        LayoutInflater layoutInflater = CreatePetProfile.this.getLayoutInflater();
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
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {

                File dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File dir_temp = new File(String.valueOf(dirPath));
                if (!dir_temp.exists())
                    dir_temp.mkdirs();

                File dest = new File(dir_temp, new Date() + ".png");
                Uri fileUri = Uri.fromFile(dest);
                bitmap = (Bitmap) data.getExtras().get("data");
                try {
                    CropImage.activity(fileUri).setAspectRatio(1, 1).setFixAspectRatio(true).start(CreatePetProfile.this);
                    FileOutputStream out = new FileOutputStream(dest);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getBaseContext(), "ImageSaved", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == GALERY_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap bm;
            if (data != null) {

                try {
                    final Uri imageUri = data.getData();
                    CropImage.activity(imageUri).setAspectRatio(3, 4).setFixAspectRatio(true).start(CreatePetProfile.this);


                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);


                  /*  final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    selectedImage = ImageConverter.getRoundedCornerBitmap(bm, 90);
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    selectedImage = getResizedBitmap(selectedImage, 100);*/
                    getResizedBitmap(bm, 65);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(CreatePetProfile.this.getContentResolver(), resultUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 65, byteArrayOutputStream);
                    encodedImage = encodeImage(bitmap);
                    Log.e("%%ENCODEDCAPTUREIMAGE##", encodedImage + "");
                    ivAddImage.setImageBitmap(bitmap);
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
