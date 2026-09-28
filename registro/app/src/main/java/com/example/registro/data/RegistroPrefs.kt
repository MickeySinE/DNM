package com.example.registro.data

import android.content.Context

data class Registro(
    val matricula: String = "",
    val nombre: String = "",
    val carrera: String = "",
    val turno: String = "Matutino",
    val activo: Boolean = true
)

class RegistroPrefs(context: Context) {
    private val prefs = context.getSharedPreferences("RegistroPrefs", Context.MODE_PRIVATE)

    private companion object {
        const val KEY_MATRICULA = "matricula"
        const val KEY_NOMBRE = "nombre"
        const val KEY_CARRERA = "carrera"
        const val KEY_TURNO = "turno"
        const val KEY_ACTIVO = "activo"
    }

    fun guardar(registro: Registro) {
        prefs.edit()
            .putString(KEY_MATRICULA, registro.matricula)
            .putString(KEY_NOMBRE, registro.nombre)
            .putString(KEY_CARRERA, registro.carrera)
            .putString(KEY_TURNO, registro.turno)
            .putBoolean(KEY_ACTIVO, registro.activo)
            .apply()
    }

    fun cargar(): Registro = Registro(
        matricula = prefs.getString(KEY_MATRICULA, "") ?: "",
        nombre = prefs.getString(KEY_NOMBRE, "") ?: "",
        carrera = prefs.getString(KEY_CARRERA, "") ?: "",
        turno = prefs.getString(KEY_TURNO, "Matutino") ?: "Matutino",
        activo = prefs.getBoolean(KEY_ACTIVO, true)
    )
}