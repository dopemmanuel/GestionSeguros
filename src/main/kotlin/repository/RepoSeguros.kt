package iesra.prog2425.repository

import iesra.prog2425.model.seguros.Seguro
import java.io.File
import iesra.prog2425.model.seguros.SeguroAuto
import iesra.prog2425.model.seguros.SeguroHogar
import iesra.prog2425.model.seguros.SeguroVida
import iesra.prog2425.model.enums.TipoAuto
import iesra.prog2425.model.enums.NivelRiesgo

class RepoSeguros(private val archivo: String) {
    private val mapaSeguros = mapOf<String, (List<String>) -> Seguro>(
        "SeguroHogar" to { datos ->
            SeguroHogar(
                datos[0].toInt(),
                datos[1],
                datos[2].toInt(),
                datos[3].toDouble(),
                datos[4].toInt(),
                datos[5].toDouble(),
                datos[6]
            )
        },
        "SeguroAuto" to { datos ->
            SeguroAuto(
                datos[0].toInt(),
                datos[1],
                datos[2].toInt(),
                datos[3].toDouble(),
                datos[4],
                datos[5],
                TipoAuto.valueOf(datos[6]),
                datos[7],
                datos[8].toBoolean(),
                datos[9].toInt()
            )
        },
        "SeguroVida" to { datos ->
            SeguroVida(
                datos[0].toInt(),
                datos[1],
                datos[2].toInt(),
                datos[3].toDouble(),
                datos[4],
                NivelRiesgo.valueOf(datos[5]),
                datos[6].toDouble()
            )
        }
    )

    fun guardarSeguro(seguro: Seguro) {
        File(archivo).appendText("${seguro.serializar()}\n")
    }

    fun cargarSeguros(): List<Seguro> {
        val seguros = mutableListOf<Seguro>()
        val file = File(archivo)

        if (!file.exists()) return seguros

        file.forEachLine { linea ->
            if (linea.isNotBlank()) {
                val datos = linea.split(";")
                val tipoSeguro = datos.last()

                mapaSeguros[tipoSeguro]?.invoke(datos)?.let {
                    seguros.add(it)
                }
            }
        }
        return seguros
    }
}