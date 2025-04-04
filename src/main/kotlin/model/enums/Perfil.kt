package iesra.prog2425.model.enums

enum class Perfil(val desc: String) {
    ADMIN("Administrador"),
    GESTION("Gestión"),
    CONSULTA("Consulta");

    companion object {
        fun getPerfil(valor: String): Perfil {
            return entries.find { it.name.equals(valor, ignoreCase = true) } ?: CONSULTA
        }
    }
}