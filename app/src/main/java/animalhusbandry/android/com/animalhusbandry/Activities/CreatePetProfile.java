package animalhusbandry.android.com.animalhusbandry.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import animalhusbandry.android.com.animalhusbandry.Activities.CreatePetProfileParams.CreatePetProfileRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.CreatePetProfileParams.CreatePetProfileResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.RetroFit.RetroUtils;
import animalhusbandry.android.com.animalhusbandry.Activities.utils.ImageConverter;
import animalhusbandry.android.com.animalhusbandry.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class CreatePetProfile extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1100;
    private static final int GALERY_REQUEST = 2700;
    private static final int RESULT_CROP = 2900;
    EditText etPetName, etBloodline, etRegistration, etAge, etColor, etLocation, etBreed, etAnyOther;
    Button btnCreateProfile;
    CheckBox checkboxMale, checkboxFemale, checkboxDHPP, checkboxRabies, checkboxParvoVirus, checkboxNone;
    String strPetName, strPetBloodline, strPetRegistration, strPetAge, strPetColor, strPetLocation, strPetBreed, strPetAnyOther, strGender;
    public ProgressDialog ringProgressDialog;
    public AlertDialog cameraDialog;
    ImageView ivBtnAddImage;
    public Uri fileUri;
    public Bitmap bitmap;
    public String encodedImage;
    private static final int REQUEST_READ_PERMISSION = 7860;
    private static final int REQUEST_CAMERA_PERMISSION = 1888;
    public Bitmap circularBitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_pet_profile);
        init();
    }

    public void init() {
        ivBtnAddImage = (ImageView) findViewById(R.id.ivBtnAddImage);
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
                    ringProgressDialog = ProgressDialog.show(CreatePetProfile.this, "Please wait ...", "Creating pet profile", true);
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
                    createPetProfileRequest.setImage(encodedImage);
                    Log.e("%%SETIMAGE$$", encodedImage + "");
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
                //uploadPicture();
            }
        } else {
            popUpmenu();
            //uploadPicture();

        }
    }


    public void takePicture() {
        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(imageIntent, CAMERA_REQUEST);
    /*    try {
            File dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File dir_temp = new File(String.valueOf(dirPath));
            if (!dir_temp.exists())
                dir_temp.mkdirs();

            File uploadFile = new File(dir_temp, new Date() + ".png");
            fileUri = Uri.fromFile(uploadFile);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void selectFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                /*String filename = "animalhusbandry.png";
                File sd = Environment.getExternalStorageDirectory();*/
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
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.flush();
                    out.close();
                    encodedImage = encodeImage(bitmap);
                    Log.e("%%ENCODEDCAPTUREIMAGE##", encodedImage + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getBaseContext(), "ImageSaved", Toast.LENGTH_SHORT).show();
            }
            ivBtnAddImage.setImageBitmap(bitmap);
        } else if (requestCode == GALERY_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap bm = null;
            if (data != null) {

                try {
                    final Uri imageUri = data.getData();
                    CropImage.activity(imageUri).setAspectRatio(1, 1).setFixAspectRatio(true).start(CreatePetProfile.this);
              /*      CropImage.activity(imageUri).start(this);
                    CropImage.activity().start(CreatePetProfile.this);*/
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                    circularBitmap = ImageConverter.getRoundedCornerBitmap(bm, 90);
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    selectedImage = getResizedBitmap(selectedImage, 100);
                    encodedImage = encodeImage(selectedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ivBtnAddImage.setImageBitmap(circularBitmap);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(CreatePetProfile.this.getContentResolver(), resultUri);
                    ivBtnAddImage.setImageBitmap(bitmap);
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
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
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
