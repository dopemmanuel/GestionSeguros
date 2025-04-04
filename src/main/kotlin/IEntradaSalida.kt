package iesra.prog2425

interface IEntradaSalida {
    fun mostrar(msj: String, salto: Boolean = true, pausa: Boolean = false){}
    fun mostrarError(msj: String, salto: Boolean = true){}
    fun pedirInfo(msj: String = ""): String

}