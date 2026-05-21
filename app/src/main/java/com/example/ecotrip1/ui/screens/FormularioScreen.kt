package com.example.ecotrip1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecotrip1.presentation.viewmodel.EcoTripViewModel
import com.example.ecotrip1.ui.navigation.*
import androidx.compose.runtime.livedata.observeAsState

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
    //Nombre viajero desde disco
    val nameDisk by viewModel.nameFromDisk.observeAsState("")
    var nameViajero by remember { mutableStateOf(nameDisk) }
    //Tipo transporte desde disco
    val transporteFromDisk by viewModel.tipoTransporteFromDisk.observeAsState("")
    var tipoTransporte by remember { mutableStateOf(transporteFromDisk) }
    //Opciones transporte
    val opcionesTransporte = listOf("Tren", "Bicicleta", "Vehículo eléctrico")
    var expanded by remember { mutableStateOf(false) }
    //switch rutas baja huella
    val rutasBajaHuellaDisk by viewModel.soloRutasBajaHuellaFromDisk.observeAsState("")
    var soloRutasBajaHuella by remember { mutableStateOf(rutasBajaHuellaDisk) }

    //Leer datos del disco
    LaunchedEffect(nameDisk) {
        nameViajero = nameDisk
    }
    LaunchedEffect(transporteFromDisk) {
        tipoTransporte = transporteFromDisk
    }
    LaunchedEffect(rutasBajaHuellaDisk) {
        soloRutasBajaHuella = rutasBajaHuellaDisk
    }

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
                        viewModel.nombreViajero.isNotBlank() &&
                        viewModel.destino.isNotBlank() &&
                        dias != null &&
                        dias > 0
                    ) {
                        viewModel.saveName(viewModel.nombreViajero)
                        viewModel.saveTipoTransporte(tipoTransporte)
                        viewModel.saveSoloRutasBajaHuella(soloRutasBajaHuella)

                        onIrResumen(
                            viewModel.nombreViajero,
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
                value = viewModel.nombreViajero,
                onValueChange = { viewModel.updateNombreViajero(it)
                    viewModel.saveName(it)},
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
                                viewModel.saveTipoTransporte(opcion)
                                viewModel.updateTipoTransporte(opcion)
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
                    onCheckedChange = {
                        soloRutasBajaHuella = it
                        viewModel.saveSoloRutasBajaHuella(it)  // disco
                    }
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