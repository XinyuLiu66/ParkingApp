package com.example.andriod.parkingapp.service;


import com.example.andriod.parkingapp.data.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dell on 30.05.2017.
 */

public interface UserAPIService {
    @GET("users")
    Call<List<User>> getAllUserInfos();

//    @GET("users/{email}")
//    Call<List<User>> getUserInfo(@Path("email") String email);

//    @GET("users")
//    Call<List<User>> getUser(@Query("email") String email, @Query("password") String password);

    @POST("users")
//@FormUrlEncoded
    Call<User> postUserInfo(@Body User user);


    @DELETE("users/{id}")
    Call<User> deleteUserInfo(@Path("id") Integer id);

}
