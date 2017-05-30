package animalhusbandry.android.com.animalhusbandry.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import animalhusbandry.android.com.animalhusbandry.R;

/**
 * Created by grewalshavneet on 4/28/2017.
 */

public class EditUserAccount extends AppCompatActivity {
    EditText etEditUserFName,etEditUserLName,etEditUserContact,etEditUserPassword,etEditUserConfirmPassword;
    Button btnSubmitUserChanges;
    String strEditUserFName, strEditUserLName, strEditUserContact, strEditUserEmail, strEditUserPassword, strEditUserConfirmPassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_account);
        etEditUserFName=(EditText)findViewById(R.id.etEditFName);
        etEditUserLName =(EditText)findViewById(R.id.etEditLName);
        etEditUserContact =(EditText)findViewById(R.id.etEditContact);
        etEditUserPassword=(EditText)findViewById(R.id.etEditPassword);
        etEditUserConfirmPassword =(EditText)findViewById(R.id.etEditConfirmPassword);
        btnSubmitUserChanges=(Button)findViewById(R.id.btnSubmitUserChanges);
        btnSubmitUserChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEditUserFName = etEditUserFName.getText().toString();
                strEditUserLName = etEditUserLName.getText().toString();
                strEditUserContact = etEditUserContact.getText().toString();
                strEditUserPassword = etEditUserPassword.getText().toString();
                strEditUserConfirmPassword = etEditUserConfirmPassword.getText().toString();

            }
        });

}}
