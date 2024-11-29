package edu.uptc.parcialSpringBoot.repository;

import edu.uptc.parcialSpringBoot.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
}
