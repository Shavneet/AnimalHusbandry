package com.animalhusbandry.model.updateuserdetailsmodel;

/**
 * Created by grewalshavneet on 5/24/2017.
 */

public class UpdateUserDetailsRequest {

    private String lastName;

        private String phone;

        private String userId;

        private String firstName;

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

        public String getUserId ()
        {
            return userId;
        }

        public void setUserId (String userId)
        {
            this.userId = userId;
        }

        public String getFirstName ()
        {
            return firstName;
        }

        public void setFirstName (String firstName)
        {
            this.firstName = firstName;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [lastName = "+lastName+", phone = "+phone+", userId = "+userId+", firstName = "+firstName+"]";
        }
    }



