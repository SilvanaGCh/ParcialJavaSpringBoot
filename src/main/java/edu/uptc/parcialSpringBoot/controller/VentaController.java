package edu.uptc.parcialSpringBoot.controller;

import edu.uptc.parcialSpringBoot.dtos.VentaDTO;
import edu.uptc.parcialSpringBoot.handler.ResponseHandler;
import edu.uptc.parcialSpringBoot.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Listar todas las ventas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ventas encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Object> listarVentas() {
        try {
            return ResponseHandler.generateResponse(
                    "Success",
                    HttpStatus.OK,
                    ventaService.listarVentas()
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    @Operation(summary = "Obtener una venta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerVenta(@PathVariable Integer id) {
        try {
            return ResponseHandler.generateResponse(
                    "Success",
                    HttpStatus.OK,
                    ventaService.obtenerVenta(id)
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
    }

    @Operation(summary = "Crear una nueva venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de venta inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> crearVenta(@RequestBody VentaDTO ventaDTO) {
        try {
            return ResponseHandler.generateResponse(
                    "Venta creada exitosamente",
                    HttpStatus.CREATED,
                    ventaService.crearVenta(ventaDTO)
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
