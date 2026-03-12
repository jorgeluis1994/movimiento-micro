package com.dev.bank.movimientos.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.dev.bank.movimientos.dto.MovimientoDTO;

@Service
public class MovimientoPublisher {


    private final RabbitTemplate rabbitTemplate;

    public MovimientoPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarMovimiento(MovimientoDTO movimiento) {
        rabbitTemplate.convertAndSend(
            "movimientosExchange",   // exchange
            "movimiento.creado",     // routing key
            movimiento               // mensaje
        );
        System.out.println(">>> Movimiento publicado: " + movimiento.getTipoMovimiento() + " $" + movimiento.getValor());
    }

    
}
