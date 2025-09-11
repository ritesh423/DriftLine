package com.riteshapps.driftline.api

import com.riteshapps.driftline.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products/{id}")
    suspend fun searchProduct(@Path("id") id: Int): Product

}