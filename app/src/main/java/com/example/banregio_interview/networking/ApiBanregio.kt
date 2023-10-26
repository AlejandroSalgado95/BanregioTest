package com.example.banregio_interview.networking

import com.example.banregio_interview.features.domain.response.CreditCardDTO
import com.example.banregio_interview.features.domain.response.MovementDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiBanregio {

    @GET("tarjetacredito.php/{id}")
    suspend fun getCreditCard(
        @Path(value = "id", encoded = true) id: Int
    ): Response<CreditCardDTO>


    @GET("tarjetacredito-movimientos.php/{id}")
    suspend fun getCreditCardMovements(
        @Path(value = "id", encoded = true) id: Int
    ): Response<List<MovementDTO>>

    companion object {
        const val BASE_URL = "https://xqualo.com.mx/rest/"
    }
}