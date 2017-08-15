package com.example.alexandrzanko.smallshop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by alexandrzanko on 8/14/17.
 */

public interface IApi {
    @GET("/rest/api/getProducts.php")
    Call<List<Product>> getProducts();

    @FormUrlEncoded
    @POST("/rest/api/checkOut.php")
    Call<Integer> checkOut(@Field("name") String name, @Field("phone") String phone, @Field("address") String address, @Field("orderItems") String orderItems);
}
