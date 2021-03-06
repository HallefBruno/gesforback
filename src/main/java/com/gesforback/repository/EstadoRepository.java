
package com.gesforback.repository;

import com.gesforback.entity.Estado;
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
public interface EstadoRepository extends JpaRepository<Estado, UUID>{
    public Page<Estado> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    public Optional<Estado> findByNomeContainingIgnoreCase(String nome);
}
