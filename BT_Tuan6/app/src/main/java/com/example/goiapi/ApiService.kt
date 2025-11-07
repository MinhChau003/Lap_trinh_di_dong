package com.example.goiapi

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("m1/890655-872447-default/v2/product")
    fun getProduct(): Call<Product>
}