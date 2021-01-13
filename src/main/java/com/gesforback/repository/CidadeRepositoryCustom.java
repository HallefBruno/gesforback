package com.gesforback.repository;

import com.gesforback.entity.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author sud
 */
public interface CidadeRepositoryCustom {
    
    Page<Cidade> findByCidade(String nomeCidade, String nomeEstado, Pageable pageable);
    
}
