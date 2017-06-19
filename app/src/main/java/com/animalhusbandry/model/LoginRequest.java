package com.animalhusbandry.model;

/**
 * Created by grewalshavneet on 5/10/2017.
 */

public class LoginRequest {

    private String email;

    private String password;

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [email = "+email+", password = "+password+"]";
    }
}
