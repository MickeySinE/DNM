package com.example.registro.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.registro.data.Registro

@Composable
fun DetalleScreen(registro: Registro, onVolver: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Registro confirmado", style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider()

        Fila("Matrícula", registro.matricula)
        Fila("Nombre", registro.nombre)
        Fila("Carrera", registro.carrera)
        Fila("Turno", registro.turno)
        Fila("Estatus", if (registro.activo) "Activo" else "Inactivo")

        HorizontalDivider()
        Button(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al formulario")
        }
    }
}

@Composable
private fun Fila(etiqueta: String, valor: String) {
    Column {
        Text(etiqueta, style = MaterialTheme.typography.labelMedium)
        Text(valor, style = MaterialTheme.typography.titleMedium)
    }
}