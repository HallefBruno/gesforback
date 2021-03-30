
package com.gesforback.repository;

import com.gesforback.entity.Portaria;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sud
 */
@Repository
public interface PortariaRepository extends JpaRepository<Portaria, UUID> {
    public Page<Portaria> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    public Optional<Portaria> findByNomeContainingIgnoreCase(String nome);
}
