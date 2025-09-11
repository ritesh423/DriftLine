package com.riteshapps.driftline.utils

import com.riteshapps.driftline.api.RetrofitInstance
import com.riteshapps.driftline.models.Product

class ProductRepository {
    suspend fun getAllProducts(): List<Product> {
        return RetrofitInstance.api.getAllProducts()
    }

}
