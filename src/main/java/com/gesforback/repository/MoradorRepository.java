
package com.gesforback.repository;

import com.gesforback.entity.Morador;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, UUID>{
    Optional<Morador> findByCpf(String cpf);
}
