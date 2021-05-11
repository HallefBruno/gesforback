
package com.gesforback.repository.querys.morador;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Morador;
//import com.gesforback.entity.MoradorSecundario;
import com.gesforback.entity.filtros.FiltrosMorador;

public interface MoradorRepositoryCustom {
    DataTable<Morador> filtrar(FiltrosMorador filtrosMorador, int draw, int start);
//    DataTable<MoradorSecundario> filtrarMoradorSecundario(FiltrosMorador filtrosMorador, int draw, int start);
}
