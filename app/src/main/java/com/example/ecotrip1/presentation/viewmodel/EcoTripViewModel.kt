package com.example.ecotrip1.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecotrip1.data.local.GlobalPreferences
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class EcoTripViewModel (
    private val stateHandle: SavedStateHandle,
    private val globalPrefs: GlobalPreferences
) : ViewModel(){

    var destino by mutableStateOf(stateHandle.get<String>("destino_key") ?: "")
    private set
    var diasDuracion by mutableStateOf(stateHandle.get<String>("dias_duracion") ?: "")
    private set
    var esViajeGrupal by mutableStateOf(stateHandle.get<Boolean>("esViajeGrupal") ?: false)
    private set
    var nombreViajero by mutableStateOf(stateHandle.get<String>("nombre_viajero") ?: "")
        private set
    var bajaHuellaCarbono by mutableStateOf(stateHandle.get<Boolean>("huella_carbono") ?: false)
        private set
    var tipoTransporte by mutableStateOf(stateHandle.get<String>("tipo_transporte") ?: "Tren")
        private set

    //Sobrevivir al stateHandle
    fun updateNombreViajero(newNombre: String) {
        nombreViajero = newNombre
        stateHandle["nombre_viajero"] = newNombre
    }
    fun updateBajaHuellaCarbono (newBajaHuellaCarbono: Boolean){
        bajaHuellaCarbono = newBajaHuellaCarbono
        stateHandle["huella_carbono"] = newBajaHuellaCarbono
    }
    fun updateTipoTransporte (newTipoTransporte: String){
        tipoTransporte = newTipoTransporte
        stateHandle["tipo_transporte"] =newTipoTransporte
    }
    fun updateDestino(newDestino: String) {
        destino = newDestino
        stateHandle["destino_key"] = newDestino
    }
    fun updateDiasDuracion(newDiasDuracion: String) {
        diasDuracion = newDiasDuracion
        stateHandle["dias_duracion"] = newDiasDuracion
    }
    fun updateEsViajeGrupal(newViajeGrupal: Boolean) {
        esViajeGrupal = newViajeGrupal
        stateHandle["esViajeGrupal"] = newViajeGrupal
    }

    val nameFromDisk = globalPrefs.user_name.asLiveData()
    val tipoTransporteFromDisk = globalPrefs.tipoTransporte.asLiveData()
    val soloRutasBajaHuellaFromDisk = globalPrefs.soloRutasBajaHuella.asLiveData()

    //Llamamos a la función para guardar en disco esos datos
    fun saveName(newName: String) {
        viewModelScope.launch {
            globalPrefs.saveName(newName)
        }
    }
    fun saveTipoTransporte(newTipoTransporte: String) {
        viewModelScope.launch {
            globalPrefs.saveTipoTransporte(newTipoTransporte)
        }
    }
    fun saveSoloRutasBajaHuella(interruptor: Boolean) {
        viewModelScope.launch {
            globalPrefs.saveSoloRutasBajaHuella(interruptor)
        }
    }



}