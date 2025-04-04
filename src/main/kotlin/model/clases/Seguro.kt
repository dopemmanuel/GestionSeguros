package iesra.prog2425.model.clases


abstract class Seguro(
    val numPoliza: Int,
    private val dniTitular: String,
    protected var importe: Double
) : IExportable {

    abstract fun calcularImporteAnioSiguiente(interes: Double): Double
    abstract fun tipoSeguro(): String

    override fun serializar(separador: String): String {
        return listOf(numPoliza, dniTitular, importe).joinToString(separator = separador)
    }

    override fun toString(): String {
        return "Seguro(numPoliza=$numPoliza, dniTitular=$dniTitular, importe=${"%.2f".format(importe)})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Seguro) return false
        return numPoliza == other.numPoliza
    }

    override fun hashCode(): Int {
        return numPoliza.hashCode()
    }
}