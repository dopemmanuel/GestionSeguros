package iesra.prog2425.service

import model.seguros.*
import iesra.prog2425.repository.RepoSeguros
import iesra.prog2425.model.enums.TipoAuto
import iesra.prog2425.model.enums.NivelRiesgo
import iesra.prog2425.model.seguros.SeguroAuto
import iesra.prog2425.model.seguros.SeguroHogar
import iesra.prog2425.model.seguros.SeguroVida
import iesra.prog2425.model.seguros.Seguro

class SeguroService(private val repositorio: RepoSeguros) {
    private var nextHogarId = 100000
    private var nextAutoId = 400000
    private var nextVidaId = 800000

    fun contratarSeguroHogar(dniTitular: String, importe: Double, metrosCuadrados: Int, valorContenido: Double, direccion: String): SeguroHogar {
        val seguro = SeguroHogar(
            nextHogarId++,
            dniTitular,
            generarNumPoliza(),
            importe,
            metrosCuadrados,
            valorContenido,
            direccion
        )
        repositorio.guardarSeguro(seguro)
        return seguro
    }

    fun contratarSeguroAuto(dniTitular: String, importe: Double, descripcion: String, combustible: String,
                            tipoAuto: TipoAuto, tipoCobertura: String, asistenciaCarretera: Boolean): SeguroAuto {
        val seguro = SeguroAuto(
            nextAutoId++,
            dniTitular,
            generarNumPoliza(),
            importe,
            descripcion,
            combustible,
            tipoAuto,
            tipoCobertura,
            asistenciaCarretera,
            0 // numPartes inicializado a 0
        )
        repositorio.guardarSeguro(seguro)
        return seguro
    }

    fun contratarSeguroVida(dniTitular: String, importe: Double, fechaNac: String, nivelRiesgo: NivelRiesgo, indemnizacion: Double): SeguroVida {
        val seguro = SeguroVida(
            nextVidaId++,
            dniTitular,
            generarNumPoliza(),
            importe,
            fechaNac,
            nivelRiesgo,
            indemnizacion
        )
        repositorio.guardarSeguro(seguro)
        return seguro
    }

    fun listarSeguros(): List<Seguro> = repositorio.cargarSeguros()

    private fun generarNumPoliza(): Int {
        // Lógica para generar número de póliza único
        return (100000..999999).random()
    }
}