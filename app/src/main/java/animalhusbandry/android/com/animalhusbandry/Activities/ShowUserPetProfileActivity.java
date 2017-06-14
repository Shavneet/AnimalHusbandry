package animalhusbandry.android.com.animalhusbandry.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import animalhusbandry.android.com.animalhusbandry.Activities.GetPetProfilesOfUserParams.GetPetProfilesOfUserResponse;
import animalhusbandry.android.com.animalhusbandry.R;

import static android.R.attr.width;
import static android.support.design.R.attr.height;

public class ShowUserPetProfileActivity extends AppCompatActivity {

    public GetPetProfilesOfUserResponse.Result userPetArrayList;
    public GetPetProfilesOfUserResponse.PetVaccinationsList[] userPetArrayList1;
    public TextView tvPetName, tvBloodline, tvRegistration, tvAge, tvColor, tvLocation, tvBreed, tvAnyOther;
    public Button btnCreateProfile;
    public String strPetName, strBloodline, strRegistration, strAge, strColor, strLocation, strBreed, strAnyOther, strGender,
            strPetId,strImageUrl;
    public ProgressDialog ringProgressDialog;
    public AlertDialog cameraDialog;
   public ImageView ivCircularImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user_pet_profile);
        init();
        Bundle extras = new Bundle();
        extras = getIntent().getExtras();
        userPetArrayList = (GetPetProfilesOfUserResponse.Result) extras.getSerializable("petVaccinationsListOfUser");
        userPetArrayList1 = userPetArrayList.getPetVaccinationsList();
        /*        userPetArrayList = (GetPetProfilesOfUserResponse.PetVaccinationsList[]) extras.getSerializable("petVaccinationsListOfUser");
          String strVaccinationResponse = String.valueOf(userPetArrayList[0]);*/

        Log.e("@@@@@@@@", userPetArrayList1[0] + "");

    }

    public void init() {
        ivCircularImageView = (ImageView) findViewById(R.id.ivCircularImageView);
        tvPetName = (TextView) findViewById(R.id.tvPetName);
        tvBloodline = (TextView) findViewById(R.id.tvBloodline);
        tvRegistration = (TextView) findViewById(R.id.tvRegistration);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvColor = (TextView) findViewById(R.id.tvColor);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvBreed = (TextView) findViewById(R.id.tvBreed);
        tvAnyOther = (TextView) findViewById(R.id.tvAnyOther);
        btnCreateProfile = (Button) findViewById(R.id.btnCreateProfile);
        strPetName = userPetArrayList.getName().toString();
        strBloodline = userPetArrayList.getBloodLine().toString();
        strRegistration = userPetArrayList.getMicrochipNumber().toString();
        strAge = userPetArrayList.getAge();
        strColor = userPetArrayList.getColor();
        strLocation = userPetArrayList.getLocation();
        strBreed = userPetArrayList.getBreed();
        strGender=userPetArrayList.getGender();
        strPetId=userPetArrayList.getPetId();
        strImageUrl=userPetArrayList.getImageUrl();
        tvPetName.setText(strPetName);
        tvAge.setText(strAge);
        tvBloodline.setText(strBloodline);
        tvBreed.setText(strBreed);
        tvColor.setText(strColor);
        tvLocation.setText(strLocation);
        tvRegistration.setText(strLocation);
        Picasso.with(getBaseContext())
                .load(strImageUrl)
                .resize(width,height).into(ivCircularImageView);
    }
}