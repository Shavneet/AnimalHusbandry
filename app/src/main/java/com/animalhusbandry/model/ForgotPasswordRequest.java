package com.animalhusbandry.model;

/**
 * Created by grewalshavneet on 5/11/2017.
 */

public class ForgotPasswordRequest {

    private String email;

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [email = "+email+"]";
    }
}
