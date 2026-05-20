package com.example.ecotrip1.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PreferenciaViaje(
    val nombreViajero: String,
    val tipoTransporte: String,
    val bajaHuellaCarbono: Boolean
)