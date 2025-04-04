package iesra.prog2425.model.clases

import iesra.prog2425.model.enums.TipoAuto



class SeguroAuto(
    numPoliza: Int,
    dniTitular: String,
    id: Int,
    importe: Double,
    val descripcion: String,
    val combustible: String,
    val tipoAuto: TipoAuto,
    val tipoCobertura: String,
    val asistenciaCarretera: Boolean,
    val numPartes: Int
) : Seguro(numPoliza, dniTitular, importe) {

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val incremento = if (numPartes > 0) interes + (2 * numPartes) else interes
        return getImporte() * (1 + incremento / 100)
    }

    override fun tipoSeguro(): String = "SeguroAuto"

    override fun serializar(): String {
        return "$numPoliza;$dniTitular;$importe;$descripcion;$combustible;$tipoAuto;$tipoCobertura;$asistenciaCarretera;$numPartes;${tipoSeguro()}"
    }
}