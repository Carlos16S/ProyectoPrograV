package com.notes.notes.InterfazUsuario

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.base.R
import com.notes.notes.model.NotasFB
import com.notes.notes.viewModel.HomeViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select



@Composable
fun HomePantalla(navController: NavController, viewModel: HomeViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Solo se llama una vez
    LaunchedEffect(Unit) {
        viewModel.mostrarNotas()
    }

    val notas = viewModel.notas

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú", modifier = Modifier.align(Alignment.CenterHorizontally))
                Divider()
                NavigationDrawerItem(
                    label = { Text("Barra lateral Exitosa") },
                    onClick = { navController.navigate("algunlado") },
                    selected = true,
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Notas",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            // Muestra las notas
            MostrarNotas(modifier = Modifier, notasFB = notas)

            // Botón flotante
            btAgregarNotas(navController = navController)
        }
    }
}




    @Composable
    fun btAgregarNotas(navController: NavController) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = { navController.navigate("CrearNotas") },//Poner ruta correcta al "CrearNotasScreen"
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.common_full_open_on_phone),
                    contentDescription = "Agregar nota",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

    }


@Composable
fun MostrarNotas(modifier: Modifier, notasFB: List<NotasFB>) {
    Box(modifier = modifier.fillMaxSize()) {
        if (notasFB.isEmpty()) {
            Text(
                text = "No se encontraron sus notas",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp,
                color = Color.Gray
            )
        } else {
            LazyVerticalGrid(
                modifier = Modifier.align(Alignment.Center),
                columns = GridCells.Adaptive(128.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                )
            ) {
                items(notasFB.size) { index ->
                    val nota = notasFB[index]
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    ) {
                        Text(
                            text = nota.Titulo,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}








