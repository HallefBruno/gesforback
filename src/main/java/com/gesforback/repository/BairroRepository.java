package com.gesforback.repository;

import com.gesforback.entity.Bairro;
import com.gesforback.entity.Cidade;
import com.gesforback.repository.querys.bairro.BairroRepositoryCustom;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, UUID>, BairroRepositoryCustom {
    
    Optional<Bairro> findByNomeContainingIgnoreCaseAndCidade(String nomeBairro, Cidade cidade);
    
}
