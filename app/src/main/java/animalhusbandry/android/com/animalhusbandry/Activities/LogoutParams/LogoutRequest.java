package animalhusbandry.android.com.animalhusbandry.Activities.LogoutParams;

/**
 * Created by grewalshavneet on 5/18/2017.
 */

public class LogoutRequest {
    private String userId;

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [userId = "+userId+"]";
    }
}
