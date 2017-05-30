package animalhusbandry.android.com.animalhusbandry.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import animalhusbandry.android.com.animalhusbandry.R;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class AddPet extends AppCompatActivity {
    ImageButton imageButtonAddPet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet);
        imageButtonAddPet=(ImageButton)findViewById(R.id.imageButtonAddPet);

}}
