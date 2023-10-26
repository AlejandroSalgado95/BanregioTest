package com.example.banregio_interview.features.domain.response

import com.google.gson.annotations.SerializedName

data class CreditCardDTO(
    @SerializedName("pkTarjetaCreditoID") val pkCreditCard: Int,
    @SerializedName("Nombre_Banco") val bankName: String,
    @SerializedName("Numero_Tarjeta") val creditCardNumber: String,
    @SerializedName("Titular_Tarjeta") val cardOwner: String,
    @SerializedName("Fecha_Exp") val expirationDate: String,
    @SerializedName("CVV") val cvv: Int,
    @SerializedName("Monto") val amount: Int
)

data class CreditCardDomain(
    val pkCreditCard: Int,
    val bankName: String,
    val creditCardNumber: String,
    val cardOwner: String,
    val expirationDate: String,
    val cvv: Int,
    val amount: Int
)

fun CreditCardDTO.toCreditCardDomain() = CreditCardDomain(
     pkCreditCard = pkCreditCard,
     bankName = bankName,
     creditCardNumber = creditCardNumber,
     cardOwner = cardOwner,
     expirationDate = expirationDate,
     cvv = cvv,
     amount = amount
)
