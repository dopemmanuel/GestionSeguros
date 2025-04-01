package iesra.prog2425.repository

import iesra.prog2425.model.PerfilUsuario
import iesra.prog2425.model.Usuario
import java.io.File

class RepoUsuarios(private val archivo: String) {
    fun cargarUsuarios(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val file = File(archivo)

        if (!file.exists()) return usuarios

        file.forEachLine { linea ->
            if (linea.isNotBlank()) {
                val datos = linea.split(";")
                if (datos.size == 3) {
                    usuarios.add(
                        Usuario(
                            datos[0],
                            datos[1],
                            PerfilUsuario.valueOf(datos[2].uppercase())
                        )
                    )
                }
            }
        }
        return usuarios
    }

    fun guardarUsuario(usuario: Usuario) {
        File(archivo).appendText("${usuario.username};${usuario.passwordHash};${usuario.perfil}\n")
    }

    fun crearUsuarioAdminInicial(): Usuario {
        println("No se encontró ningún usuario admin. Creando uno inicial...")
        print("Ingrese nombre de usuario admin: ")
        val username = readLine() ?: "admin"
        print("Ingrese contraseña: ")
        val password = readLine() ?: "admin123"

        val usuario = Usuario(
            username,
            BCryptUtil.encriptar(password),
            PerfilUsuario.ADMIN
        )

        guardarUsuario(usuario)
        return usuario
    }
}