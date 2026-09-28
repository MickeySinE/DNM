package com.example.registro

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.registro.data.Registro
import com.example.registro.screens.DetalleScreen
import com.example.registro.screens.FormularioScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize().systemBarsPadding()) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "formulario") {

        composable("formulario") {
            FormularioScreen(onRegistrar = { r ->
                // Uri.encode evita problemas con espacios y caracteres especiales
                navController.navigate(
                    "detalle/${Uri.encode(r.matricula)}/${Uri.encode(r.nombre)}/" +
                            "${Uri.encode(r.carrera)}/${Uri.encode(r.turno)}/${r.activo}"
                )
            })
        }

        composable(
            route = "detalle/{matricula}/{nombre}/{carrera}/{turno}/{activo}",
            arguments = listOf(
                navArgument("matricula") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType },
                navArgument("turno") { type = NavType.StringType },
                navArgument("activo") { type = NavType.BoolType }
            )
        ) { entry ->
            val args = entry.arguments
            val registro = Registro(
                matricula = args?.getString("matricula") ?: "",
                nombre = args?.getString("nombre") ?: "",
                carrera = args?.getString("carrera") ?: "",
                turno = args?.getString("turno") ?: "",
                activo = args?.getBoolean("activo") ?: false
            )
            DetalleScreen(registro = registro, onVolver = { navController.popBackStack() })
        }
    }
}