package com.example.andriod.parkingapp.service;


import com.example.andriod.parkingapp.data.saver.ReservationInfo;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by dell on 27.05.2017.
 */

public interface APIService {
    @GET("reservationInformations")
    Call<List<ReservationInfo>> getAllReservationInfo();


//    @POST("reservationInformations")
//    @FormUrlEncoded
//    Call<ReservationInfo> postReservationInfo(@Field("id") Integer id,
//                                              @Field("coordinate") String coordinate,
//                                              @Field("timeStatus") String timeStatus);
@POST("reservationInformations")
//@FormUrlEncoded
Call<ReservationInfo> postReservationInfo(@Body ReservationInfo reservationInfo);



    // RxJava
//    @POST("reservationInformations")
//    @FormUrlEncoded
//    Observable<ReservationInfo> postReservationInfo(@Field("id") Integer id,
//                                                    @Field("coordinate") String coordinate,
//                                                    @Field("timeStatus") String timeStatus);
    @PUT("reservationInformations/{id}")
 //   @FormUrlEncoded
    Call<ReservationInfo> putReservationInfo(@Path("id") Integer id,@Body ReservationInfo reservationInfo);

    @DELETE("reservationInformations/{id}")
    Call<ReservationInfo> deleteReservationInfo(@Path("id") Integer id);
}