package com.notes.notes.InterfazUsuario.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.notes.notes.InterfazUsuario.HomePantalla
import com.notes.notes.InterfazUsuario.Login.LoginPantalla
import com.notes.notes.InterfazUsuario.Login.RegistroPantalla
import com.notes.notes.InterfazUsuario.MostrarNotas
import com.notes.notes.InterfazUsuario.PantallaCrearNotas
import com.notes.notes.viewModel.CrearViewModel
import com.notes.notes.viewModel.HomeViewModel
import com.notes.notes.viewModel.LoginViewModel
import com.notes.notes.viewModel.RegistroVieModel

@Composable
fun NavGraph_() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val registroVieModel:RegistroVieModel= hiltViewModel()
    val homeViewModel:HomeViewModel=hiltViewModel()
    val Crearviewmodel:CrearViewModel= hiltViewModel()
    NavHost(navController = navController, startDestination = "LoginPantalla") {
        composable("LoginPantalla"){

            LoginPantalla(viewModel =  loginViewModel, navController =  navController )

        }
        composable("RegistroPantalla"){

            RegistroPantalla(viewModel =registroVieModel, navController =navController  )

        }
        composable("HomePantalla"){

            HomePantalla(navController = navController, viewModel = homeViewModel)

        }
        composable("CrearNotas"){

            PantallaCrearNotas(navController = navController, viewModel = Crearviewmodel)



        }


    }
}