
package com.gesforback.repository.querys;

import com.gesforback.entity.Bairro;
import com.gesforback.entity.DataTable;


public interface BairroRepositoryCustom {
    DataTable<Bairro> filtrar(String nomeBairro, String nomeCidade, int draw, int start,int length);
}
