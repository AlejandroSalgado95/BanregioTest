package com.example.banregio_interview.features.domain.response

import com.google.gson.annotations.SerializedName


data class MovementDTO(
    @SerializedName("pkMovimientosID") val pkMovementsId: String,
    @SerializedName("Descripcion") val description: String,
    @SerializedName("Fecha") val date: String,
    @SerializedName("Monto") val amount: String,
)

data class MovementDomain(
    val pkMovementsId: String,
    val description: String,
    val date: String,
    val amount: String,
)

fun MovementDTO.toMovementDomain() = MovementDomain(
    pkMovementsId = pkMovementsId,
    description = description,
    date = date,
    amount = amount,
)

