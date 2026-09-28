package com.example.registro.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.registro.data.Registro
import com.example.registro.data.RegistroPrefs

private val carreras = listOf(
    "Licenciatura en Software",
    "Ingeniería Civil",
    "Ingeniería Electrónica",
    "Ingeniería Industrial",
    "Ingeniería Mecánica"
)

@Composable
fun FormularioScreen(onRegistrar: (Registro) -> Unit) {
    val context = LocalContext.current
    val prefs = remember { RegistroPrefs(context) }

    // Se inicializa con el último registro guardado (persistencia)
    val inicial = remember { prefs.cargar() }
    var matricula by remember { mutableStateOf(inicial.matricula) }
    var nombre by remember { mutableStateOf(inicial.nombre) }
    var carrera by remember { mutableStateOf(inicial.carrera) }
    var turno by remember { mutableStateOf(inicial.turno) }
    var activo by remember { mutableStateOf(inicial.activo) }
    var menuAbierto by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Formulario de Registro", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text("Matrícula") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Carrera: menú desplegable
        Text("Carrera")
        Box {
            OutlinedButton(
                onClick = { menuAbierto = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (carrera.isEmpty()) "Selecciona una carrera" else carrera)
            }
            DropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = { menuAbierto = false }
            ) {
                carreras.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            carrera = opcion
                            menuAbierto = false
                        }
                    )
                }
            }
        }

        // Turno: RadioButton
        Text("Turno")
        Row(verticalAlignment = Alignment.CenterVertically) {
            listOf("Matutino", "Vespertino").forEach { opcion ->
                RadioButton(
                    selected = turno == opcion,
                    onClick = { turno = opcion }
                )
                Text(opcion, modifier = Modifier.padding(end = 16.dp))
            }
        }

        // Estatus: Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Estatus: " + if (activo) "Activo" else "Inactivo")
            Switch(checked = activo, onCheckedChange = { activo = it })
        }

        if (error.isNotEmpty()) {
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                if (matricula.isBlank() || nombre.isBlank() || carrera.isEmpty()) {
                    error = "Completa matrícula, nombre y carrera"
                } else {
                    error = ""
                    val registro = Registro(matricula.trim(), nombre.trim(), carrera, turno, activo)
                    prefs.guardar(registro)   // persistencia
                    onRegistrar(registro)     // navegación
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
    }
}