package com.example.ecotrip1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecotrip1.presentation.viewmodel.EcoTripViewModel
import com.example.ecotrip1.ui.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    viewModel: EcoTripViewModel,
    onIrResumen: (
        nombreViajero: String,
        destino: String,
        diasDuracion: Int,
        tipoTransporte: String,
        soloRutasBajaHuella: Boolean,
        esViajeGrupal: Boolean
    ) -> Unit
){
    var nombreViajero by remember { mutableStateOf("") }
    var tipoTransporte by remember { mutableStateOf("Tren") }
    var soloRutasBajaHuella by remember { mutableStateOf(false) }

    val opcionesTransporte = listOf("Tren", "Bicicleta", "Vehículo eléctrico")
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EcoTrip 2026") },
                colors = TopAppBarDefaults.topAppBarColors(
                        containerColor =
                            MaterialTheme.colorScheme.primaryContainer,
                titleContentColor =
                MaterialTheme.colorScheme.onPrimaryContainer)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val dias = viewModel.diasDuracion.toIntOrNull()

                    if (
                        nombreViajero.isNotBlank() &&
                        viewModel.destino.isNotBlank() &&
                        dias != null &&
                        dias > 0
                    ) {
                        viewModel.saveName(nombreViajero)
                        viewModel.saveTipoTransporte(tipoTransporte)
                        viewModel.saveSoloRutasBajaHuella(soloRutasBajaHuella)

                        onIrResumen(
                            nombreViajero,
                            viewModel.destino,
                            dias,
                            tipoTransporte,
                            soloRutasBajaHuella,
                            viewModel.esViajeGrupal
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Text("Ir")
            }
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            OutlinedTextField(
                value=nombreViajero,
                onValueChange = { nombreViajero = it },
                label = { Text("Nombre del viajero") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value=viewModel.destino,
                onValueChange = { viewModel.updateDestino(it) },
                label = { Text("Destino") },
                modifier = Modifier.fillMaxWidth(),

            )
            OutlinedTextField(
                value =viewModel.diasDuracion,
                onValueChange = { viewModel.updateDiasDuracion(it)},
                label = { Text("Dias duración") },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = tipoTransporte,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de transporte") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opcionesTransporte.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                tipoTransporte = opcion
                                expanded = false
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Solo rutas de baja huella")
                Switch(
                    checked = soloRutasBajaHuella,
                    onCheckedChange = { soloRutasBajaHuella = it }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("¿Viaje grupal?")
                Switch(
                    checked = viewModel.esViajeGrupal,
                    onCheckedChange = { viewModel.updateEsViajeGrupal(it) }
                )
            }
        }
    }
}