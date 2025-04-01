package iesra.prog2425.model.seguros

class SeguroHogar(
    numPoliza: Int,
    dniTitular: String,
    id: Int,
    importe: Double,
    val metrosCuadrados: Int,
    val valorContenido: Double,
    val direccion: String
) : Seguro(numPoliza, dniTitular, importe) {

    override fun calcularImporteAnioSiguiente(interes: Double): Double {
        return getImporte() * (1 + interes / 100)
    }

    override fun tipoSeguro(): String = "SeguroHogar"

    override fun serializar(): String {
        return "$numPoliza;$dniTitular;$importe;$metrosCuadrados;$valorContenido;$direccion;${tipoSeguro()}"
    }
}