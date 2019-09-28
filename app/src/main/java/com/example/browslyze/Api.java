package com.example.browslyze;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {


    String BASE_URL = "https://website-categorization-api.whoisxmlapi.com/api/";

    @GET("v1?apiKey=at_bmiT6ScLYZmtNF8XRHXJDxFnIsZro")
    Call<GetType> getType(@Query("domainName") String url);



}
