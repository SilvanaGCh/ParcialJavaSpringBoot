package edu.uptc.parcialSpringBoot.repository;

import edu.uptc.parcialSpringBoot.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
