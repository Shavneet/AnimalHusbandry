package com.animalhusbandry.retrofit;


import com.animalhusbandry.model.ChangePasswordRequest;
import com.animalhusbandry.model.ChangePasswordResponse;
import com.animalhusbandry.model.CreatePetProfileRequest;
import com.animalhusbandry.model.CreatePetProfileResponse;
import com.animalhusbandry.model.DeleteUserProfileRequest;
import com.animalhusbandry.model.DeleteUserProfileResponse;
import com.animalhusbandry.model.ForgotPasswordRequest;
import com.animalhusbandry.model.ForgotPasswordResponse;
import com.animalhusbandry.model.GetAllPetProfilesRequest;
import com.animalhusbandry.model.GetAllPetProfilesResponse;
import com.animalhusbandry.model.GetPetProfilesOfUserRequest;
import com.animalhusbandry.model.GetPetProfilesOfUserResponse;
import com.animalhusbandry.model.GetUserDetailsRequest;
import com.animalhusbandry.model.GetUserDetailsResponse;
import com.animalhusbandry.model.LoginRequest;
import com.animalhusbandry.model.LoginResponse;
import com.animalhusbandry.model.LogoutRequest;
import com.animalhusbandry.model.LogoutResponse;
import com.animalhusbandry.model.SignUpRequest;
import com.animalhusbandry.model.SignUpResponse;
import com.animalhusbandry.model.UpdatePetProfileResponse;
import com.animalhusbandry.model.UpdatePetProfilesRequest;
import com.animalhusbandry.model.UpdateUserDetailsRequest;
import com.animalhusbandry.model.UpdateUserDetailsResponse;

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
