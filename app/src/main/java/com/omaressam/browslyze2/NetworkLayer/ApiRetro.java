package com.omaressam.browslyze2.NetworkLayer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRetro {


    String BASE_URL = "https://website-categorization-api.whoisxmlapi.com/api/";

    @GET("v1?apiKey=at_bmiT6ScLYZmtNF8XRHXJDxFnIsZro")
    Call<GetType> getType(@Query("domainName") String url);



}
 // https://website-categorization.whoisxmlapi.com/api/v1?apiKey=at_bmiT6ScLYZmtNF8XRHXJDxFnIsZro & domainName=bbc.com