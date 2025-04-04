package iesra.prog2425.model.clases

import iesra.prog2425.model.enums.NivelRiesgo

class SeguroVida(
    numPoliza: Int,
    dniTitular: String,
    id: Int,
    importe: Double,
    val fechaNac: String,
    val nivelRiesgo: NivelRiesgo,
    val indemnizacion: Double
) : Seguro(numPoliza, dniTitular, importe) {

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        val incremento = when (nivelRiesgo) {
            NivelRiesgo.BAJO -> interes + 2
            NivelRiesgo.MEDIO -> interes + 5
            NivelRiesgo.ALTO -> interes + 10
        }
        return getImporte() * (1 + incremento / 100)
    }

    override fun tipoSeguro(): String = "SeguroVida"

    override fun serializar(): String {
        return "$numPoliza;$dniTitular;$importe;$fechaNac;$nivelRiesgo;$indemnizacion;${tipoSeguro()}"
    }
}