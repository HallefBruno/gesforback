
package com.gesforback.repository.querys.filipeta;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Filipeta;
import java.util.List;
import java.util.UUID;

public interface FilipetaRepositoryCustom {
     DataTable<Filipeta> filtrar(String numero, String nomePortaria, int draw, int start,int length);
     List<Filipeta> existeFilipetaParaPortaria(String numero, UUID portariaId);
}
