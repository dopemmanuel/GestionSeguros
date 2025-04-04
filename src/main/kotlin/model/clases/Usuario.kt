package iesra.prog2425.model.clases

import iesra.prog2425.model.enums.Perfil


class Usuario(
    val nombre: String,
    private var clave: String,
    val perfil: Perfil
) : IExportable {

    fun cambiarClave(nuevaClaveEncriptada: String) {
        clave = nuevaClaveEncriptada
    }

    override fun serializar(separador: String): String {
        return listOf(nombre, clave, perfil.name).joinToString(separador)
    }

    companion object {
        fun crearUsuario(datos: List<String>): Usuario {
            require(datos.size == 3) { "Datos incorrectos para crear usuario" }
            return Usuario(
                datos[0],
                datos[1],
                Perfil.getPerfil(datos[2])
            )
        }
    }
}

