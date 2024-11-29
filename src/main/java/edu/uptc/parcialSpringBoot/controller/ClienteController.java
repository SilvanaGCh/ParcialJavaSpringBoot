package edu.uptc.parcialSpringBoot.controller;

import edu.uptc.parcialSpringBoot.dtos.ClienteDTO;
import edu.uptc.parcialSpringBoot.handler.ResponseHandler;
import edu.uptc.parcialSpringBoot.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "API para la gestión de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Object> listarClientes() {
        try {
            return ResponseHandler.generateResponse(
                    "Success",
                    HttpStatus.OK,
                    clienteService.listarClientes()
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    @Operation(summary = "Obtener un cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerCliente(@PathVariable Integer id) {
        try {
            ClienteDTO result = clienteService.obtenerCliente(id);
            return ResponseHandler.generateResponse(
                    "Success",
                    HttpStatus.OK,
                    result
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
    }

    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de cliente inválidos")
    })
    @PostMapping
    public ResponseEntity<Object> agregarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            return ResponseHandler.generateResponse(
                    "Cliente creado exitosamente",
                    HttpStatus.CREATED,
                    clienteService.agregarCliente(clienteDTO)
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
    }

    @Operation(summary = "Eliminar un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarCliente(@PathVariable Integer id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseHandler.generateResponse(
                    "Cliente eliminado exitosamente",
                    HttpStatus.OK,
                    null
            );
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
    }

}
