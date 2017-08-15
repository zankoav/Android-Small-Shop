package com.example.alexandrzanko.smallshop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexandrzanko on 8/14/17.
 */
public class ApiController {

    public static final String BASE_URL = "http://zanko.posevnaya.com/";

    public static IApi getApi() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IApi api = retrofit.create(IApi.class);

        return api;
    }
}