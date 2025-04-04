package iesra.prog2425.data

import iesra.prog2425.model.clases.Usuario
import iesra.prog2425.model.enums.Perfil


interface IRepoUsuarios {
    fun agregar(usuario: Usuario): Boolean
    fun buscar(nombreUsuario: String): Usuario?
    fun eliminar(usuario: Usuario): Boolean
    fun eliminar(nombreUsuario: String): Boolean
    fun obtenerTodos(): List<Usuario>
    fun obtener(perfil: Perfil): List<Usuario>
    fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean
}