package edu.uptc.parcialSpringBoot.service;

import edu.uptc.parcialSpringBoot.dtos.DetalleVentaDTO;
import edu.uptc.parcialSpringBoot.dtos.ProductoDTO;
import edu.uptc.parcialSpringBoot.dtos.VentaDTO;
import edu.uptc.parcialSpringBoot.entities.Cliente;
import edu.uptc.parcialSpringBoot.entities.DetalleVenta;
import edu.uptc.parcialSpringBoot.entities.Producto;
import edu.uptc.parcialSpringBoot.entities.Venta;
import edu.uptc.parcialSpringBoot.repository.ClienteRepository;
import edu.uptc.parcialSpringBoot.repository.DetalleVentaRepository;
import edu.uptc.parcialSpringBoot.repository.ProductoRepository;
import edu.uptc.parcialSpringBoot.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public VentaDTO crearVenta(VentaDTO ventaDTO) {
        // Buscar cliente
        Cliente cliente = clienteRepository.findById(ventaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Crear la venta
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(new Date());
        venta.setEstado("PENDIENTE");
        venta.setTotal(0.0f);

        // Guardar la venta primero para tener el ID
        venta = ventaRepository.save(venta);

        // Procesar los detalles
        float totalVenta = 0.0f;
        List<DetalleVenta> detalles = new ArrayList<>();

        for(DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if(producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente");
            }

            // Crear y guardar el detalle
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            detalle.setSubtotal(detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario());

            detalles.add(detalle);
            totalVenta += detalle.getSubtotal();

            // Actualizar stock del producto
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);
        }

        // Guardar detalles
        detalleVentaRepository.saveAll(detalles);

        // Actualizar total de la venta
        venta.setTotal(totalVenta);
        venta.setDetalleVentas(detalles);
        venta = ventaRepository.save(venta);

        return convertToDTO(venta);
    }

    public List<VentaDTO> listarVentas() {
        return ventaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VentaDTO obtenerVenta(Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        return convertToDTO(venta);
    }

    private VentaDTO convertToDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());
        dto.setClienteId(venta.getCliente().getId());
        return dto;
    }

    private Venta convertToEntity(VentaDTO dto) {
        Venta venta = new Venta();
        if (dto.getId() != null) {
            venta.setId(dto.getId());
        }
        venta.setTotal(dto.getTotal());
        venta.setEstado(dto.getEstado());
        return venta;
    }
}
