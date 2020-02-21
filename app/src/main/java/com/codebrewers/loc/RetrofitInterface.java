package com.codebrewers.loc;

import com.codebrewers.loc.weather.WeatherPOJO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @POST("auth/registration/")
    Call<RegistrationModel> registerUser(@Body RegistrationModel registrationModel);

    @POST("auth/token/login/")
    Call<LogInResponse> logIn(@Body LogInRequest logInRequest);

    @GET("forecast")
    Call<WeatherPOJO> getWeatherData(@Query("lat") String lat,@Query("lon") String lon, @Query("APPID") String akey);

    @GET("forecast")
    Call<WeatherPOJO> getData(@Query("id") String id,@Query("appid") String akey);

}
