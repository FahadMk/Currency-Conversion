package com.dgsl.currencyconverter.data.api;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //currency request
    @GET("latest")
    Call<JsonObject> getCurrencyConversion(@Query(value = "apikey", encoded = true) String apiKey, @Query(value = "base_currency", encoded = true) String base_currency);



}