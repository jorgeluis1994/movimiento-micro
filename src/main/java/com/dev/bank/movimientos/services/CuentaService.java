package com.dev.bank.movimientos.services;

import com.dev.bank.movimientos.dto.CrearCuentaDTO;
import com.dev.bank.movimientos.models.Cuenta;
import com.dev.bank.movimientos.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final RestTemplate restTemplate;

    public CuentaService(CuentaRepository cuentaRepository, RestTemplate restTemplate) {
        this.cuentaRepository = cuentaRepository;
        this.restTemplate = restTemplate;
    }

    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta obtenerCuenta(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
    }

    // MÉTODO COMPLETO PARA CREAR CUENTA
    public Cuenta crearCuenta(CrearCuentaDTO dto) {
        // 1. Buscamos al cliente en el microservicio de Clientes (puerto 4000)
        String url = "http://localhost:4000/clientes/buscar?nombre=" + dto.getNombreCliente();

        // Obtenemos la respuesta como una lista de mapas
        List<Map<String, Object>> clientes = restTemplate.getForObject(url, List.class);

        // 2. Validamos que el cliente exista
        if (clientes == null || clientes.isEmpty()) {
            throw new RuntimeException("Error: El cliente '" + dto.getNombreCliente() + "' no existe.");
        }

        // 3. Extraemos el ID del cliente (del primer resultado encontrado)
        // El "id" viene del JSON del micro de clientes
        Long idEncontrado = Long.valueOf(clientes.get(0).get("id").toString());

        // 4. Creamos el objeto Cuenta con los datos del DTO y el ID encontrado
        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setNumeroCuenta(dto.getNumeroCuenta());
        nuevaCuenta.setTipoCuenta(dto.getTipoCuenta());
        // Convertimos el BigDecimal del DTO al Double de la Entidad
        nuevaCuenta.setSaldo(dto.getSaldoInicial().doubleValue());

        nuevaCuenta.setEstado(dto.getEstado());
        nuevaCuenta.setClienteId(idEncontrado); // Guardamos el Long clienteId de tu entidad

        // 5. Guardamos en la base de datos de Movimientos
        return cuentaRepository.save(nuevaCuenta);
    }

    public Cuenta actualizarCuenta(Long id, Cuenta cuenta) {
        Cuenta existente = obtenerCuenta(id);
        existente.setNumeroCuenta(cuenta.getNumeroCuenta());
        existente.setTipoCuenta(cuenta.getTipoCuenta());
        existente.setSaldo(cuenta.getSaldo());
        existente.setEstado(cuenta.getEstado());
        return cuentaRepository.save(existente);
    }

    public void eliminarCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }
}
