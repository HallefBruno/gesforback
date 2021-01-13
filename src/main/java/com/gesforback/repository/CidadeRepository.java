
package com.gesforback.repository;

import com.gesforback.entity.Cidade;
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
public interface CidadeRepository extends JpaRepository<Cidade, UUID> {
    
    public Optional<Cidade> findByEstadoAndNomeContainingIgnoreCase(Estado estado, String nomeCidade);
    public Page<Cidade> findByNomeContainingIgnoreCaseAndEstado(String nome,Estado estado, Pageable pageable);
    
    
    
    
    //public Optional<Cidade> findByNomeIgnoreCaseAndCidade_Estado(String nomeCidade,Estado estado);//UUID estadoId
    //repository.findAll(Sort.by(Sort.Order.asc("name").ignoreCase()));
}
