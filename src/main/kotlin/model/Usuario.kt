package iesra.prog2425.model


enum class PerfilUsuario {
    ADMIN, GESTION, CONSULTA
}

class Usuario(
    val username: String,
    val passwordHash: String,
    val perfil: PerfilUsuario
) {
    fun verificarPassword(password: String): Boolean {
        return BCryptUtil.verificar(password, passwordHash)
    }
}

