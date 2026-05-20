package com.example.ecotrip1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecotrip1.presentation.viewmodel.EcoTripViewModel
import com.example.ecotrip1.ui.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenScreen(
    nombreViajero: String,
    destino: String,
    diasDuracion: Int,
    tipoTransporte: String,
    soloRutasBajaHuella: Boolean,
    esViajeGrupal: Boolean,
    onVolver: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen del viaje") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver al formulario"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ){paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Text(
                text = "Configuración seleccionada",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ){
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    Text(
                        text = "Viajero $nombreViajero",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Destino $destino",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Dias de duración: $diasDuracion",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Transporte ecológico: $tipoTransporte",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Solo rutas de baja huella: ${if (soloRutasBajaHuella) "Sí" else "No"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Viaje grupal: ${if (esViajeGrupal) "Sí" else "No"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

    }
}