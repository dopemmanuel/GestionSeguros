package iesra.prog2425

import iesra.prog2425.model.PerfilUsuario
import iesra.prog2425.model.Usuario
import iesra.prog2425.model.enums.NivelRiesgo
import iesra.prog2425.model.enums.TipoAuto
import iesra.prog2425.model.seguros.Seguro
import iesra.prog2425.model.seguros.SeguroHogar
import iesra.prog2425.model.seguros.SeguroVida
import iesra.prog2425.model.seguros.SeguroAuto
import iesra.prog2425.repository.RepoSeguros
import iesra.prog2425.repository.RepoUsuarios
import iesra.prog2425.service.SeguroService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun main() {
    val repositorioUsuarios = RepoUsuarios("Usuarios.txt")
    val usuarios = repositorioUsuarios.cargarUsuarios()

    if (usuarios.none { it.perfil == PerfilUsuario.ADMIN }) {
        repositorioUsuarios.crearUsuarioAdminInicial()
        println("Usuario admin creado. Reinicie la aplicación.")
        return
    }

    println("=== Sistema de Gestión de Seguros ===")
    print("Usuario: ")
    val username = readlnOrNull() ?: ""
    print("Contraseña: ")
    val password = readlnOrNull() ?: ""

    val usuario = usuarios.find { it.username == username && it.verificarPassword(password) }

    if (usuario == null) {
        println("Credenciales incorrectas")
        return
    }

    println("\nBienvenido, ${usuario.username} (${usuario.perfil})")

    println("\nSeleccione el modo de ejecución:")
    println("1. SIMULACIÓN (solo en memoria)")
    println("2. ALMACENAMIENTO (usar ficheros)")
    print("Opción: ")
    val modo = readlnOrNull()?.toIntOrNull() ?: 1

    val usarFicheros = modo == 2
    val archivoSeguros = if (usarFicheros) "Seguros.txt" else ""

    val repositorioSeguros = RepoSeguros(archivoSeguros)
    val seguroService = SeguroService(repositorioSeguros)

    when (usuario.perfil) {
        PerfilUsuario.ADMIN -> mostrarMenuAdmin(seguroService, repositorioUsuarios)
        PerfilUsuario.GESTION -> mostrarMenuGestion(seguroService)
        PerfilUsuario.CONSULTA -> mostrarMenuConsulta(seguroService)
    }
}

// ==================== MENÚ ADMIN ====================
fun mostrarMenuAdmin(seguroService: SeguroService, repositorioUsuarios: RepoUsuarios) {
    var opcion: Int
    do {
        println("\n=== MENÚ ADMINISTRADOR ===")
        println("1. Usuarios")
        println("2. Seguros")
        println("3. Salir")
        print("Seleccione una opción: ")

        opcion = readlnOrNull()?.toIntOrNull() ?: 0

        when (opcion) {
            1 -> menuUsuarios(repositorioUsuarios)
            2 -> menuSegurosAdmin(seguroService)
            3 -> println("Saliendo del sistema...")
            else -> println("Opción no válida")
        }
    } while (opcion != 3)
}

fun menuUsuarios(repositorioUsuarios: RepoUsuarios) {
    var opcion: Int
    do {
        println("\n=== GESTIÓN DE USUARIOS ===")
        println("1. Nuevo usuario")
        println("2. Eliminar usuario")
        println("3. Cambiar contraseña")
        println("4. Consultar usuarios")
        println("5. Volver")
        print("Seleccione una opción: ")

        opcion = readlnOrNull()?.toIntOrNull() ?: 0

        when (opcion) {
            1 -> crearNuevoUsuario(repositorioUsuarios)
            2 -> eliminarUsuario(repositorioUsuarios)
            3 -> cambiarContrasenaUsuario(repositorioUsuarios)
            4 -> consultarUsuarios(repositorioUsuarios)
            5 -> println("Volviendo al menú principal...")
            else -> println("Opción no válida")
        }
    } while (opcion != 5)
}

fun menuSegurosAdmin(seguroService: SeguroService) {
    var opcion: Int
    do {
        println("\n=== GESTIÓN DE SEGUROS ===")
        println("1. Contratar seguro")
        println("2. Eliminar seguro")
        println("3. Consultar seguros")
        println("4. Volver")
        print("Seleccione una opción: ")

        opcion = readln().toIntOrNull() ?: 0

        when (opcion) {
            1 -> menuContratarSeguro(seguroService)
            2 -> eliminarSeguro(seguroService)
            3 -> menuConsultarSeguros(seguroService)
            4 -> println("Volviendo al menú principal...")
            else -> println("Opción no válida")
        }
    } while (opcion != 4)
}

// ==================== MENÚ GESTIÓN ====================
fun mostrarMenuGestion(seguroService: SeguroService) {
    var opcion: Int
    do {
        println("\n=== MENÚ GESTIÓN ===")
        println("1. Seguros")
        println("2. Salir")
        print("Seleccione una opción: ")

        opcion = readln().toIntOrNull() ?: 0

        when (opcion) {
            1 -> menuSegurosGestion(seguroService)
            2 -> println("Saliendo del sistema...")
            else -> println("Opción no válida")
        }
    } while (opcion != 2)
}

fun menuSegurosGestion(seguroService: SeguroService) {
    var opcion: Int
    do {
        println("\n=== GESTIÓN DE SEGUROS ===")
        println("1. Contratar seguro")
        println("2. Eliminar seguro")
        println("3. Consultar seguros")
        println("4. Volver")
        print("Seleccione una opción: ")

        opcion = readln().toIntOrNull() ?: 0

        when (opcion) {
            1 -> menuContratarSeguro(seguroService)
            2 -> eliminarSeguro(seguroService)
            3 -> menuConsultarSeguros(seguroService)
            4 -> println("Volviendo al menú principal...")
            else -> println("Opción no válida")
        }
    } while (opcion != 4)
}

// ==================== MENÚ CONSULTA ====================
fun mostrarMenuConsulta(seguroService: SeguroService) {
    var opcion: Int
    do {
        println("\n=== MENÚ CONSULTA ===")
        println("1. Consultar seguros")
        println("2. Salir")
        print("Seleccione una opción: ")

        opcion = readln().toIntOrNull() ?: 0

        when (opcion) {
            1 -> menuConsultarSeguros(seguroService)
            2 -> println("Saliendo del sistema...")
            else -> println("Opción no válida")
        }
    } while (opcion != 2)
}

// ==================== FUNCIONES COMPARTIDAS ====================
fun menuContratarSeguro(seguroService: SeguroService) {
    var opcion: Int
    do {
        println("\n=== CONTRATAR SEGURO ===")
        println("1. Seguro de Hogar")
        println("2. Seguro de Auto")
        println("3. Seguro de Vida")
        println("4. Volver")
        print("Seleccione una opción: ")

        opcion = readln().toIntOrNull() ?: 0

        when (opcion) {
            1 -> contratarSeguroHogar(seguroService)
            2 -> contratarSeguroAuto(seguroService)
            3 -> contratarSeguroVida(seguroService)
            4 -> println("Volviendo al menú anterior...")
            else -> println("Opción no válida")
        }
    } while (opcion != 4)
}

fun menuConsultarSeguros(seguroService: SeguroService) {
    var opcion: Int
    do {
        println("\n=== CONSULTAR SEGUROS ===")
        println("1. Todos los seguros")
        println("2. Seguros de Hogar")
        println("3. Seguros de Auto")
        println("4. Seguros de Vida")
        println("5. Volver")
        print("Seleccione una opción: ")

        opcion = readln().toIntOrNull() ?: 0

        val seguros = seguroService.listarSeguros()

        when (opcion) {
            1 -> mostrarSeguros(seguros, "TODOS LOS SEGUROS")
            2 -> mostrarSeguros(seguros.filter { it.tipoSeguro() == "SeguroHogar" }, "SEGUROS DE HOGAR")
            3 -> mostrarSeguros(seguros.filter { it.tipoSeguro() == "SeguroAuto" }, "SEGUROS DE AUTO")
            4 -> mostrarSeguros(seguros.filter { it.tipoSeguro() == "SeguroVida" }, "SEGUROS DE VIDA")
            5 -> println("Volviendo al menú anterior...")
            else -> println("Opción no válida")
        }
    } while (opcion != 5)
}

// ==================== FUNCIONES AUXILIARES ====================
fun crearNuevoUsuario(repositorio: RepoUsuarios) {
    println("\n=== NUEVO USUARIO ===")
    print("Nombre de usuario: ")
    val username = readlnOrNull() ?: return

    print("Contraseña: ")
    val password = readlnOrNull() ?: return

    println("Seleccione perfil:")
    println("1. Admin")
    println("2. Gestión")
    println("3. Consulta")
    print("Opción: ")
    val perfilOp = readlnOrNull()?.toIntOrNull() ?: 0

    val perfil = when (perfilOp) {
        1 -> PerfilUsuario.ADMIN
        2 -> PerfilUsuario.GESTION
        3 -> PerfilUsuario.CONSULTA
        else -> {
            println("Perfil no válido")
            return
        }
    }

    val usuario = Usuario(username, BCryptUtil.encriptar(password), perfil)
    repositorio.guardarUsuario(usuario)
    println("Usuario creado exitosamente")
}

fun eliminarUsuario(repositorio: RepoUsuarios) {
    println("\n=== ELIMINAR USUARIO ===")
    val usuarios = repositorio.cargarUsuarios()
    if (usuarios.isEmpty()) {
        println("No hay usuarios registrados")
        return
    }

    println("Usuarios disponibles:")
    usuarios.forEachIndexed { index, usuario ->
        println("${index + 1}. ${usuario.username} (${usuario.perfil})")
    }

    print("Seleccione el usuario a eliminar (0 para cancelar): ")
    val opcion = readLine()?.toIntOrNull() ?: 0

    if (opcion in 1..usuarios.size) {
        val usuario = usuarios[opcion - 1]
        // Implementar lógica para eliminar usuario del archivo
        println("Usuario ${usuario.username} eliminado")
    } else {
        println("Operación cancelada")
    }
}

fun cambiarContrasenaUsuario(repositorio: RepoUsuarios) {
    println("\n=== CAMBIAR CONTRASEÑA ===")
    val usuarios = repositorio.cargarUsuarios()
    if (usuarios.isEmpty()) {
        println("No hay usuarios registrados")
        return
    }

    println("Usuarios disponibles:")
    usuarios.forEachIndexed { index, usuario ->
        println("${index + 1}. ${usuario.username} (${usuario.perfil})")
    }

    print("Seleccione el usuario (0 para cancelar): ")
    val opcion = readlnOrNull()?.toIntOrNull() ?: 0

    if (opcion in 1..usuarios.size) {
        val usuario = usuarios[opcion - 1]
        print("Nueva contraseña: ")
        val nuevaContrasena = readlnOrNull() ?: return

        println("Contraseña cambiada para ${usuario.username}")
    } else {
        println("Operación cancelada")
    }
}

fun consultarUsuarios(repositorio: RepoUsuarios) {
    println("\n=== LISTA DE USUARIOS ===")
    val usuarios = repositorio.cargarUsuarios()
    if (usuarios.isEmpty()) {
        println("No hay usuarios registrados")
        return
    }

    usuarios.forEach { usuario ->
        println("Usuario: ${usuario.username}, Perfil: ${usuario.perfil}")
    }
}

fun contratarSeguroHogar(seguroService: SeguroService) {
    println("\n=== CONTRATAR SEGURO DE HOGAR ===")

    val dni = solicitarDato("DNI del titular (8 números y 1 letra):") { it.matches(Regex("^[0-9]{8}[A-Z]$")) }
    val importe = solicitarDato("Importe anual:", String::toDouble)
    val metros = solicitarDato("Metros cuadrados:", String::toInt)
    val valorContenido = solicitarDato("Valor del contenido:", String::toDouble)
    val direccion = solicitarDato("Dirección:")

    val seguro = seguroService.contratarSeguroHogar(dni, importe, metros, valorContenido, direccion)
    println("\nSeguro contratado exitosamente:")
    println("Número de póliza: ${seguro.numPoliza}")
    println("Titular: ${seguro.dniTitular}")
    println("Importe anual: ${seguro.getImporte()} €")
}

fun contratarSeguroAuto(seguroService: SeguroService) {
    println("\n=== CONTRATAR SEGURO DE AUTO ===")

    val dni = solicitarDato("DNI del titular (8 números y 1 letra):") { it.matches(Regex("^[0-9]{8}[A-Z]$")) }
    val importe = solicitarDato("Importe anual:", String::toDouble)
    val descripcion = solicitarDato("Descripción del vehículo:")

    println("Tipo de combustible:")
    println("1. Gasolina")
    println("2. Diésel")
    println("3. Eléctrico")
    println("4. Híbrido")
    val combustible = when (solicitarDato("Opción:", String::toInt)) {
        1 -> "Gasolina"
        2 -> "Diésel"
        3 -> "Eléctrico"
        4 -> "Híbrido"
        else -> "Gasolina"
    }

    println("Tipo de vehículo:")
    println("1. Coche")
    println("2. Moto")
    println("3. Camión")
    val tipoAuto = when (solicitarDato("Opción:", String::toInt)) {
        1 -> TipoAuto.COCHE
        2 -> TipoAuto.MOTO
        3 -> TipoAuto.CAMION
        else -> TipoAuto.COCHE
    }

    println("Tipo de cobertura:")
    println("1. Terceros")
    println("2. Todo riesgo")
    val cobertura = when (solicitarDato("Opción:", String::toInt)) {
        1 -> "Terceros"
        2 -> "Todo Riesgo"
        else -> "Terceros"
    }

    val asistencia = solicitarDato("¿Incluir asistencia en carretera? (S/N):") { it.equals("S", true) || it.equals("N", true) }

    val seguro = seguroService.contratarSeguroAuto(
        dni, importe, descripcion, combustible, tipoAuto, cobertura,
        asistencia.equals("S", true)
    )

    println("\nSeguro contratado exitosamente:")
    println("Número de póliza: ${seguro.numPoliza}")
    println("Titular: ${seguro.dniTitular}")
    println("Importe anual: ${seguro.getImporte()} €")
}

fun contratarSeguroVida(seguroService: SeguroService) {
    println("\n=== CONTRATAR SEGURO DE VIDA ===")

    val dni = solicitarDato("DNI del titular (8 números y 1 letra):") { it.matches(Regex("^[0-9]{8}[A-Z]$")) }
    val importe = solicitarDato("Importe anual:", String::toDouble)

    val fechaNac = solicitarDato("Fecha de nacimiento (AAAA-MM-DD):") { fecha ->
        try {
            LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE)
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }

    println("Nivel de riesgo:")
    println("1. Bajo")
    println("2. Medio")
    println("3. Alto")
    val nivelRiesgo = when (solicitarDato("Opción:", String::toInt)) {
        1 -> NivelRiesgo.BAJO
        2 -> NivelRiesgo.MEDIO
        3 -> NivelRiesgo.ALTO
        else -> NivelRiesgo.MEDIO
    }

    val indemnizacion = solicitarDato("Indemnización:", String::toDouble)

    val seguro = seguroService.contratarSeguroVida(dni, importe, fechaNac, nivelRiesgo, indemnizacion)

    println("\nSeguro contratado exitosamente:")
    println("Número de póliza: ${seguro.numPoliza}")
    println("Titular: ${seguro.dniTitular}")
    println("Importe anual: ${seguro.getImporte()} €")
}

fun eliminarSeguro(seguroService: SeguroService) {
    println("\n=== ELIMINAR SEGURO ===")
    val seguros = seguroService.listarSeguros()
    if (seguros.isEmpty()) {
        println("No hay seguros registrados")
        return
    }

    mostrarSeguros(seguros, "SEGUROS DISPONIBLES")

    print("Ingrese el número de póliza a eliminar (0 para cancelar): ")
    val numPoliza = readLine()?.toIntOrNull() ?: 0

    if (numPoliza != 0) {
        println("Seguro con póliza $numPoliza eliminado")
    } else {
        println("Operación cancelada")
    }
}

fun mostrarSeguros(seguros: List<Seguro>, titulo: String) {
    println("\n=== $titulo ===")
    if (seguros.isEmpty()) {
        println("No hay seguros para mostrar")
        return
    }

    seguros.forEach { seguro ->
        println("\nTipo: ${seguro.tipoSeguro()}")
        println("Número de póliza: ${seguro.numPoliza}")
        println("DNI Titular: ${seguro.dniTitular}")
        println("Importe anual: ${seguro.getImporte()} €")

        when (seguro) {
            is SeguroHogar -> {
                println("Metros cuadrados: ${seguro.metrosCuadrados}")
                println("Valor contenido: ${seguro.valorContenido} €")
                println("Dirección: ${seguro.direccion}")
            }
            is SeguroAuto -> {
                println("Descripción: ${seguro.descripcion}")
                println("Combustible: ${seguro.combustible}")
                println("Tipo: ${seguro.tipoAuto}")
                println("Cobertura: ${seguro.tipoCobertura}")
                println("Asistencia: ${if (seguro.asistenciaCarretera) "Sí" else "No"}")
            }
            is SeguroVida -> {
                println("Fecha nacimiento: ${seguro.fechaNac}")
                println("Nivel riesgo: ${seguro.nivelRiesgo}")
                println("Indemnización: ${seguro.indemnizacion} €")
            }
        }
    }
}

// Función genérica para solicitar datos con validación
fun <T> solicitarDato(mensaje: String, validacion: (String) -> Boolean, transformacion: (String) -> T): T {
    while (true) {
        print("$mensaje ")
        val input = readLine() ?: ""

        if (input.equals("CANCELAR", true)) {
            throw Exception("Operación cancelada por el usuario")
        }

        if (validacion(input)) {
            return transformacion(input)
        } else {
            println("Dato inválido. Intente nuevamente o escriba CANCELAR para salir.")
        }
    }
}

// Sobrecarga para datos que no necesitan transformación (String)
fun solicitarDato(mensaje: String, validacion: (String) -> Boolean = { true }): String {
    return solicitarDato(mensaje, validacion) { it }
}