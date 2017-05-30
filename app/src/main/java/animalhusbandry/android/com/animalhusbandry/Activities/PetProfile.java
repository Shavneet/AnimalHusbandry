package animalhusbandry.android.com.animalhusbandry.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

import animalhusbandry.android.com.animalhusbandry.R;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class PetProfile extends AppCompatActivity {

    CheckBox checkboxMale, checkboxFemale, checkboxDHPP, checkboxRabies, checkboxParvoVirus;
    String strPetName, strPetBloodline, strPetRegistration, strPetAge, strPetColor, strPetLocation, strPetBreed, strPetAnyOther;
    TextView tvPetName, tvBloodline, tvRegistration, tvAge, tvColor, tvLocation, tvBreed, tvAnyOther,tvLeaveMessage,tvAddToFav
            ,tvContactOwner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile);
        tvPetName = (TextView) findViewById(R.id.tvPetName);
        tvBloodline = (TextView) findViewById(R.id.tvBloodline);
        tvRegistration = (TextView) findViewById(R.id.tvRegistration);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvColor = (TextView) findViewById(R.id.tvColor);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvBreed = (TextView) findViewById(R.id.tvBreed);
        tvAnyOther = (TextView) findViewById(R.id.tvAnyOther);
        tvLeaveMessage = (TextView) findViewById(R.id.tvLeaveMessage);
        tvAddToFav = (TextView) findViewById(R.id.tvAddToFav);
        tvContactOwner = (TextView) findViewById(R.id.tvContactOwner);



    }
}
