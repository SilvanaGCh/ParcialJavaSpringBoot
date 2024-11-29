package edu.uptc.parcialSpringBoot.repository;

import edu.uptc.parcialSpringBoot.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
