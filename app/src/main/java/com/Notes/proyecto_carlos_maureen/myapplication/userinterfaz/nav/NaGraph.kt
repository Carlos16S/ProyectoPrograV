package com.Notes.proyecto_carlos_maureen.myapplication.userinterfaz.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.Notes.proyecto_carlos_maureen.myapplication.userinterfaz.Login.LoginPantalla


@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {  LoginPantalla(viewModel(),navController) }

    }
}