package edu.uptc.parcialSpringBoot.repository;

import edu.uptc.parcialSpringBoot.entities.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    List<DetalleVenta> findByVentaId(Integer ventaId);


}
