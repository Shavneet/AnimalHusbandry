package animalhusbandry.android.com.animalhusbandry.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import animalhusbandry.android.com.animalhusbandry.R;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class EditPetProfile extends AppCompatActivity {

    EditText etEditPetName, etEditBloodline, etEditRegistration, etEditAge, etEditColor, etEditLocation, etEditBreed, etEditAnyOther;
    Button btnSubmitChanges;
    CheckBox checkboxEditMale, checkboxEditFemale, checkboxEditDHPP, checkboxEditRabies, checkboxEditParvoVirus;
    String strEditPetName, strEditPetBloodline, strEditPetRegistration, strEditPetAge, strEditPetColor, strEditPetLocation, strEditPetBreed, strEditPetAnyOther;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pet_profile);
        etEditPetName = (EditText) findViewById(R.id.etEditPetName);
        etEditBloodline = (EditText) findViewById(R.id.etEditBloodline);
        etEditRegistration = (EditText) findViewById(R.id.etEditRegistration);
        etEditAge = (EditText) findViewById(R.id.etEditAge);
        etEditColor = (EditText) findViewById(R.id.etEditColor);
        etEditLocation = (EditText) findViewById(R.id.etEditLocation);
        etEditBreed = (EditText) findViewById(R.id.etEditBreed);
        etEditAnyOther = (EditText) findViewById(R.id.etEditAnyOther);
        checkboxEditMale = (CheckBox) findViewById(R.id.checkboxEditMale);
        checkboxEditFemale = (CheckBox) findViewById(R.id.checkboxEditFemale);
        checkboxEditDHPP = (CheckBox) findViewById(R.id.checkboxEditDHPP);
        checkboxEditRabies = (CheckBox) findViewById(R.id.checkboxEditRabies);
        checkboxEditParvoVirus = (CheckBox) findViewById(R.id.checkboxEditParvoVirus);
        btnSubmitChanges = (Button) findViewById(R.id.btnSubmitChanges);
        btnSubmitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEditPetName.getText().toString().equals("")) {
                    etEditPetName.setError("Enter pet name");
                    etEditPetName.requestFocus();
                } else if (etEditBloodline.getText().toString().equals("")) {
                    etEditBloodline.setError("Enter pet name");
                    etEditBloodline.requestFocus();
                } else if (etEditBreed.getText().toString().equals("")) {
                    etEditBreed.setError("Enter pet name");
                    etEditBreed.requestFocus();
                } else if (etEditColor.getText().toString().equals("")) {
                    etEditColor.setError("Enter pet name");
                    etEditColor.requestFocus();
                } else if (etEditAge.getText().toString().equals("")) {
                    etEditAge.setError("Enter pet name");
                    etEditAge.requestFocus();
                } else if (etEditLocation.getText().toString().equals("")) {
                    etEditLocation.setError("Enter pet name");
                    etEditLocation.requestFocus();
                } else if (etEditRegistration.getText().toString().equals("")) {
                    etEditRegistration.setError("Enter pet name");
                    etEditRegistration.requestFocus();
                } else if (!(checkboxEditMale.isChecked()) && (!(checkboxEditFemale.isChecked()))) {
                    checkboxEditMale.setError("Specify");
                    checkboxEditFemale.setError("Gender");
                    checkboxEditMale.requestFocus();
                    checkboxEditFemale.requestFocus();
                }
                else


                strEditPetBloodline = etEditBloodline.getText().toString();
                strEditPetRegistration = etEditRegistration.getText().toString();
                strEditPetAge = etEditAge.getText().toString();
                strEditPetColor = etEditColor.getText().toString();
                strEditPetLocation = etEditLocation.getText().toString();
                strEditPetBreed = etEditBreed.getText().toString();
                strEditPetAnyOther = etEditAnyOther.getText().toString();
                if (checkboxEditDHPP.isChecked()) {
                } else {
                }
                if (checkboxEditParvoVirus.isChecked()) {
                } else {
                }
                if (checkboxEditRabies.isChecked()) {
                } else {
                }
                if (checkboxEditFemale.isChecked()) {
                } else {
                }
                if (checkboxEditMale.isChecked()) {
                } else {
                }
            }
        });

    }
}
