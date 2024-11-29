package edu.uptc.parcialSpringBoot.service;

import edu.uptc.parcialSpringBoot.dtos.DetalleVentaDTO;
import edu.uptc.parcialSpringBoot.entities.DetalleVenta;
import edu.uptc.parcialSpringBoot.repository.DetalleVentaRepository;
import edu.uptc.parcialSpringBoot.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public DetalleVentaDTO actualizarCantidad(Integer id, Integer nuevaCantidad) {
        DetalleVenta detalle = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado"));

        // Validar stock
        if(detalle.getProducto().getStock() + detalle.getCantidad() < nuevaCantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        // Actualizar stock del producto
        detalle.getProducto().setStock(
                detalle.getProducto().getStock() + detalle.getCantidad() - nuevaCantidad
        );
        productoRepository.save(detalle.getProducto());

        // Actualizar detalle
        detalle.setCantidad(nuevaCantidad);
        detalle.setSubtotal(detalle.getPrecioUnitario() * nuevaCantidad);

        detalle = detalleVentaRepository.save(detalle);
        return convertToDTO(detalle);
    }

    public List<DetalleVentaDTO> listarDetallesPorVenta(Integer ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DetalleVentaDTO convertToDTO(DetalleVenta detalle) {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        dto.setProductoId(detalle.getProducto().getId());
        return dto;
    }

}
