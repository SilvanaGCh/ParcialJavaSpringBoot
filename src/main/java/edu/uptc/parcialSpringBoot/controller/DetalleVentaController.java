package edu.uptc.parcialSpringBoot.controller;

import edu.uptc.parcialSpringBoot.handler.ResponseHandler;
import edu.uptc.parcialSpringBoot.service.DetalleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/detalles-venta")
public class DetalleVentaController {

    @Autowired
    private DetalleService detalleVentaService;

    @Operation(summary = "Listar detalles de una venta específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de detalles encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<Object> listarDetallesPorVenta(@PathVariable Integer ventaId) {
        try {
            return ResponseHandler.generateResponse(
                    "Success",
                    HttpStatus.OK,
                    detalleVentaService.listarDetallesPorVenta(ventaId)
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    @Operation(summary = "Actualizar la cantidad de un detalle de venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Cantidad inválida o insuficiente stock")
    })
    @PutMapping("/{id}/cantidad")
    public ResponseEntity<Object> actualizarCantidad(
            @PathVariable Integer id,
            @RequestParam Integer cantidad) {
        try {
            return ResponseHandler.generateResponse(
                    "Cantidad actualizada exitosamente",
                    HttpStatus.OK,
                    detalleVentaService.actualizarCantidad(id, cantidad)
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
    }

}
