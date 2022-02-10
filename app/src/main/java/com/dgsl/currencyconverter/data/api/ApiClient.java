package com.dgsl.currencyconverter.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://freecurrencyapi.net/api/v2/";//TODO: REPLACE WITH SERVER ADDRESS php/nodejs backend
    private static Retrofit retrofit = null;
    public static String apiKey = "10dc0050-735d-11ec-b32c-af66d62ce4c3";
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
