package animalhusbandry.android.com.animalhusbandry.Activities.RetroFit;

import animalhusbandry.android.com.animalhusbandry.Activities.CreatePetProfileParams.CreatePetProfileRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.CreatePetProfileParams.CreatePetProfileResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.ForgotPasswordParams.ForgotPasswordRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.ForgotPasswordParams.ForgotPasswordResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.ChangePasswordParams.ChangePasswordRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.ChangePasswordParams.ChangePasswordResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.DeleteUserProfileParams.DeleteUserProfileRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.DeleteUserProfileParams.DeleteUserProfileResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.GetUserDetailsParams.GetUserDetailsRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.GetUserDetailsParams.GetUserDetailsResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.UpdateUserDetailsParams.UpdateUserDetailsRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.UpdateUserDetailsParams.UpdateUserDetailsResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.GetAllPetProfilesParams.GetAllPetProfilesRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.GetAllPetProfilesParams.GetAllPetProfilesResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.GetPetProfilesOfUserParams.GetPetProfilesOfUserRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.GetPetProfilesOfUserParams.GetPetProfilesOfUserResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.LoginParams.LoginRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.LoginParams.LoginResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.LogoutParams.LogoutRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.LogoutParams.LogoutResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.SignUpParams.SignUpRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.SignUpParams.SignUpResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.UpdatePetProfileParam.UpdatePetProfileResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.UpdatePetProfileParam.UpdatePetProfilesRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by grewalshavneet on 5/10/2017.
 */

public interface ApiEndpointInterface {
    public static final String DEV_URL = "http://115.248.100.78:8092";
    public static final String TOKEN = "ADMN17899F5HJ320MSELSYG5FS5R2IJ8K6GEFJ1BK9NZW12ZMBQLT7CU9TFQH80A8N";

    public static final String PRODUCTION_URL = "";
    public static final String URL = DEV_URL;

    @POST("/dogHusbandryApp/ws/user/login")
    Call<LoginResponse> userLogin(@Body LoginRequest payload);

    @POST("/dogHusbandryApp/ws/user/userSignUp")
    Call<SignUpResponse> userSignUp(@Body SignUpRequest payload);

    @POST("/dogHusbandryApp/rest/user/password/forgot")
    Call<ForgotPasswordResponse> userForgotPassword(@Body ForgotPasswordRequest payload);

    @POST("/dogHusbandryApp/ws/user/logout")
    Call<LogoutResponse> userLogout(@Body LogoutRequest payload);

    @POST("/dogHusbandryApp/ws/user/getUserDetail")
    Call<GetUserDetailsResponse> getUserDetails(@Body GetUserDetailsRequest payload);

    @POST("dogHusbandryApp/ws/user/updateUserDetail")
    Call<UpdateUserDetailsResponse> updateUserDetails(@Body UpdateUserDetailsRequest payload);

    @POST("dogHusbandryApp/ws/user/deleteUserProfile")
    Call<DeleteUserProfileResponse> deleteUserProfile(@Body DeleteUserProfileRequest payload);

    @POST("dogHusbandryApp/ws/user/changePassword")
    Call<ChangePasswordResponse> changeUserPassword(@Body ChangePasswordRequest payload);

    @POST("dogHusbandryApp/ws/user/addPetProfile")
    Call<CreatePetProfileResponse> createPetProfile(@Body CreatePetProfileRequest payload);

    @POST("dogHusbandryApp/ws/user/getAllPetProfilesByUserId")
    Call<GetPetProfilesOfUserResponse> getPetProfilesOfUserId(@Body GetPetProfilesOfUserRequest payload);

    @POST("dogHusbandryApp/ws/user/getAllPetProfiles")
    Call<GetAllPetProfilesResponse> getAllPetProfiles(@Body GetAllPetProfilesRequest payload);
    @POST("dogHusbandryApp/ws/user/updatePetProfile")
    Call<UpdatePetProfileResponse>updatePetProfile(@Body UpdatePetProfilesRequest payload);
}
