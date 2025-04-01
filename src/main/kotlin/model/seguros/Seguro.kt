package iesra.prog2425.model.seguros

abstract class Seguro(
    val numPoliza: Int,
    val dniTitular: String,
    var importe: Double
) {
    abstract fun calcularImporteAnioSiguiente(interes: Double): Double
    abstract fun tipoSeguro(): String {

    }

    abstract fun serializar(): String

    fun getImporte(): Double = importe
    protected fun setImporte(value: Double) { importe = value }

    companion object {
        fun validarDni(dni: String): Boolean {
            return dni.matches(Regex("^[0-9]{8}[A-Z]$"))
        }
    }
}