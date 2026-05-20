package com.example.ecotrip1.ui.navigation
import kotlinx.serialization.Serializable

@Serializable
object FormularioRoute

@Serializable
data class ResumenRoute(
    val nombreViajero: String,
    val destino: String,
    val diasDuracion: Int,
    val tipoTransporte: String,
    val soloRutasBajaHuella: Boolean,
    val esViajeGrupal: Boolean
)