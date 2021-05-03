package com.gesforback.repository.querys.filipeta;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Filipeta;

public interface FilipetaRepositoryCustom {
    DataTable<Filipeta> filtrar(String numero, String nomePortaria, int draw, int start, int length);
}
