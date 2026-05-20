package com.example.ecotrip1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ecotrip1.data.local.GlobalPreferences
import com.example.ecotrip1.presentation.viewmodel.EcoTripViewModel
import com.example.ecotrip1.ui.screens.FormularioScreen
import com.example.ecotrip1.ui.screens.ResumenScreen

@Composable
fun AppNavigation(){
    // 1. Instanciamos el NavController, que controla la navegación de la app
    val navController = rememberNavController()
    val context = LocalContext.current
    //View model
    val ecoTripViewModel: EcoTripViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                EcoTripViewModel(
                    stateHandle = createSavedStateHandle(),
                    globalPrefs = GlobalPreferences(context)
                )
            }
        }
    )

    // 2. Definimos el NavHost, que funciona como mapa de rutas
    // startDestination indica qué pantalla aparece primero
    NavHost(
        navController = navController,
        startDestination = FormularioRoute
    ) {
        //Destino 1
        composable<FormularioRoute>{
            FormularioScreen(
                viewModel = ecoTripViewModel,
                onIrResumen = {
                        nombre, destino, dias, transporte, bajaHuella, grupal ->
                    navController.navigate(
                        ResumenRoute(
                            nombreViajero = nombre,
                            destino = destino,
                            diasDuracion = dias,
                            tipoTransporte = transporte,
                            soloRutasBajaHuella = bajaHuella,
                            esViajeGrupal = grupal
                        )
                    ){
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<ResumenRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<ResumenRoute>()
            ResumenScreen(
                nombreViajero = args.nombreViajero,
                destino = args.destino,
                diasDuracion = args.diasDuracion,
                tipoTransporte = args.tipoTransporte,
                soloRutasBajaHuella = args.soloRutasBajaHuella,
                esViajeGrupal = args.esViajeGrupal,
                onVolver = {
                    navController.navigate(FormularioRoute){
                        popUpTo(FormularioRoute){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }

}