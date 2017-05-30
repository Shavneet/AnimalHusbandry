package animalhusbandry.android.com.animalhusbandry.Activities.Fragments.ChangePasswordParams;

/**
 * Created by grewalshavneet on 5/25/2017.
 */

public class ChangePasswordRequest {
    private String userId;

    private String oldPassword;

    private String password;

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getOldPassword ()
    {
        return oldPassword;
    }

    public void setOldPassword (String oldPassword)
    {
        this.oldPassword = oldPassword;
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
        return "ClassPojo [userId = "+userId+", oldPassword = "+oldPassword+", password = "+password+"]";
    }
}
