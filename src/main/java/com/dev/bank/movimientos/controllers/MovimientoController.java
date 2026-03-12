package com.dev.bank.movimientos.controllers;

import com.dev.bank.movimientos.dto.EstadoCuentaDTO;
import com.dev.bank.movimientos.dto.RegistrarMovimientoDTO;
import com.dev.bank.movimientos.models.Movimiento;
import com.dev.bank.movimientos.services.MovimientoService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    // GET: Listar todos los movimientos (historial general)
    @GetMapping
    public ResponseEntity<List<Movimiento>> listarMovimientos() {
        return ResponseEntity.ok(movimientoService.listarMovimientos());
    }

    // GET: Obtener un movimiento específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtenerMovimiento(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.obtenerMovimiento(id));
    }

    // POST: Registrar un nuevo movimiento (Depósito o Retiro)
    // Usamos el DTO para recibir "numeroCuenta", "tipoMovimiento" y "valor"
    @PostMapping
    public ResponseEntity<Movimiento> registrarMovimiento(@RequestBody RegistrarMovimientoDTO movimientoDto) {
        // El servicio se encarga de validar el saldo y actualizar la cuenta
        Movimiento nuevoMovimiento = movimientoService.registrarMovimiento(movimientoDto);
        return ResponseEntity.ok(nuevoMovimiento);
    }

    // DELETE: Eliminar un registro de movimiento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reportes")
    public ResponseEntity<List<EstadoCuentaDTO>> obtenerReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(movimientoService.generarReporte(clienteId, inicio, fin));
    }

}
