package com.dev.bank.movimientos.controllers;

import com.dev.bank.movimientos.dto.CrearCuentaDTO;
import com.dev.bank.movimientos.models.Cuenta;
import com.dev.bank.movimientos.services.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    // GET: Listar todas las cuentas
    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCuentas() {
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    // GET: Obtener cuenta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerCuenta(id));
    }

    // POST: Crear cuenta usando el DTO (que incluye el nombre del cliente)
    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody CrearCuentaDTO cuentaDto) {
        Cuenta nueva = cuentaService.crearCuenta(cuentaDto);
        return ResponseEntity.ok(nueva);
    }

    // PUT: Actualizar cuenta existente
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        Cuenta actualizada = cuentaService.actualizarCuenta(id, cuenta);
        return ResponseEntity.ok(actualizada);
    }

    // DELETE: Eliminar cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
