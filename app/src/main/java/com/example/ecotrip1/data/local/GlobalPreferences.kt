package com.example.ecotrip1.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crear instancia de DataStore
private val Context.dataStore by preferencesDataStore(name = "global_prefs")

class GlobalPreferences (private val context: Context){
    private val NAME_KEY = stringPreferencesKey("user_name")
    private val TRANSPORTE_KEY = stringPreferencesKey("tipo_transporte")
    private val HUELLA_CARBONO_KEY = booleanPreferencesKey("huella_carbono")
    //Leer el nombre
    val user_name: Flow<String> = context.dataStore.data
            .map { preferences ->
                preferences[NAME_KEY] ?: ""
            }
    //Leer interruptor
    val soloRutasBajaHuella: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[HUELLA_CARBONO_KEY] ?: false
        }
    val tipoTransporte: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[TRANSPORTE_KEY] ?: "Tren"
        }

    // Función para guardar el nombre en disco
    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }
    // Función para guardar el tipo de transporte en disco
    suspend fun saveTipoTransporte(tipoTransporte: String) {
        context.dataStore.edit { preferences ->
            preferences[TRANSPORTE_KEY] = tipoTransporte
        }
    }
    //Función para guardar el interruptor
    suspend fun saveSoloRutasBajaHuella(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[HUELLA_CARBONO_KEY] = value
        }
    }
}