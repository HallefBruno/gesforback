package com.gesforback.repository;

import com.gesforback.entity.Cidade;
import com.gesforback.entity.DataTable;

/**
 *
 * @author sud
 */
public interface CidadeRepositoryCustom {
    
    DataTable<Cidade> findByCidade(String nomeCidade, String nomeEstado, int draw, int start,int length);
    
}
