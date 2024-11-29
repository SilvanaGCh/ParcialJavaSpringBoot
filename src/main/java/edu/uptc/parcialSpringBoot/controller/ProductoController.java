package edu.uptc.parcialSpringBoot.controller;

import edu.uptc.parcialSpringBoot.dtos.ProductoDTO;
import edu.uptc.parcialSpringBoot.handler.ResponseHandler;
import edu.uptc.parcialSpringBoot.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Object> listarProductos() {
        try {
            return ResponseHandler.generateResponse(
                    "Success",
                    HttpStatus.OK,
                    productoService.listarProductos()
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de producto inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> agregarProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            return ResponseHandler.generateResponse(
                    "Producto creado exitosamente",
                    HttpStatus.CREATED,
                    productoService.agregarProducto(productoDTO)
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
    }

    @Operation(summary = "Actualizar un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarProducto(
            @PathVariable Integer id,
            @RequestBody ProductoDTO productoDTO) {
        try {
            return ResponseHandler.generateResponse(
                    "Producto actualizado exitosamente",
                    HttpStatus.OK,
                    productoService.actualizarProducto(id, productoDTO)
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
    }

    @Operation(summary = "Actualizar el stock de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}/stock")
    public ResponseEntity<Object> actualizarStock(
            @PathVariable Integer id,
            @RequestParam Integer cantidad) {
        try {
            productoService.actualizarStock(id, cantidad);
            return ResponseHandler.generateResponse(
                    "Stock actualizado exitosamente",
                    HttpStatus.OK,
                    null
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
