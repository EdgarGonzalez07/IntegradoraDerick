package utez.edu.mx.integradoraderick.ui.utils

import android.content.Context
import android.content.SharedPreferences

class ControladorSesiones(context: Context) {

    private val preferencias: SharedPreferences = context.getSharedPreferences("sesiones", Context.MODE_PRIVATE)

    fun guardarUsuario(id: Int, username: String, email: String) {
        val editor = preferencias.edit()
        editor.putInt("id", id)
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putBoolean("logeado", true)
        editor.apply()
    }

    fun obtenerUsuario(): Triple<Int, String, String> {
        val id = preferencias.getInt("id", -1)
        val username = preferencias.getString("username", "") ?: ""
        val email = preferencias.getString("email", "") ?: ""
        return Triple(id, username, email)
    }

    fun cerrarSesion() {
        val editor = preferencias.edit()
        editor.clear()
        editor.apply()
    }
}