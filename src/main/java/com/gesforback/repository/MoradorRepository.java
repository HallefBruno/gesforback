
package com.gesforback.repository;

import com.gesforback.entity.Morador;
import com.gesforback.repository.querys.morador.MoradorRepositoryCustom;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, UUID>, MoradorRepositoryCustom {
    
}
