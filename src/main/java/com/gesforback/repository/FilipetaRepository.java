
package com.gesforback.repository;

import com.gesforback.entity.Filipeta;
import com.gesforback.entity.Portaria;
import com.gesforback.repository.querys.filipeta.FilipetaRepositoryCustom;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilipetaRepository extends JpaRepository<Filipeta, UUID>, FilipetaRepositoryCustom {
    Optional<Filipeta> findByNumeroAndPortaria(String numero, Portaria portaria);
}
