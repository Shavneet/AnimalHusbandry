package com.animalhusbandry.model;

/**
 * Created by grewalshavneet on 5/11/2017.
 */

public class SignUpRequest {

    private String lastName;

    private String phone;

    private String email;

    private String firstName;

    private String password;

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
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
        return "ClassPojo [lastName = "+lastName+", phone = "+phone+", email = "+email+", firstName = "+firstName+", password = "+password+"]";
    }
}
