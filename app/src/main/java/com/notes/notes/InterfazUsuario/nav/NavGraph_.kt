package com.notes.notes.InterfazUsuario.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.notes.notes.InterfazUsuario.HomePantalla
import com.notes.notes.InterfazUsuario.Login.LoginPantalla
import com.notes.notes.InterfazUsuario.Login.RegistroPantalla
import com.notes.notes.viewModel.LoginViewModel
import com.notes.notes.viewModel.RegistroVieModel

@Composable
fun NavGraph_() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val registroVieModel:RegistroVieModel= hiltViewModel()
    NavHost(navController = navController, startDestination = "LoginPantalla") {
        composable("LoginPantalla"){

            LoginPantalla(viewModel =  loginViewModel, navController =  navController )

        }
        composable("RegistroPantalla"){

            RegistroPantalla(viewModel =registroVieModel, navController =navController  )

        }
        composable("HomePantalla"){

            HomePantalla()

        }


    }
}