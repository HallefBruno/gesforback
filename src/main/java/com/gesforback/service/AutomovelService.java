
package com.gesforback.service;

import com.gesforback.entity.Automovel;
import com.gesforback.entity.dto.AutomovelDTO;
import com.gesforback.entity.dto.ResultSelectAutomoveis;
import com.gesforback.repository.AutomovelRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AutomovelService {
    
    @Autowired
    private AutomovelRepository automovelRepository;
    
    public ResultSelectAutomoveis automoveis(String nome,String fabricanteId, String pagina) {
        AutomovelDTO automovelDTO = new AutomovelDTO();
        ResultSelectAutomoveis resultSelectAutomoveis = new ResultSelectAutomoveis();
        List<AutomovelDTO> automovelDTOs = new ArrayList<>();
        Pageable pageable =  PageRequest.of(Integer.valueOf(pagina), 10, Sort.by("nome").descending());
        Page page = automovelRepository.findByNomeContainingIgnoreCaseAndFabricanteId(nome,Long.valueOf(fabricanteId),pageable);
        resultSelectAutomoveis.setTotalItens(page.getTotalElements());
        if(page.hasContent()) {
            List<Automovel> automovels = page.getContent();
            resultSelectAutomoveis.setTotalItens(page.getTotalElements());
            for(Automovel automovel : automovels) {
                automovelDTO.setId(automovel.getId().toString());
                automovelDTO.setText(automovel.getNome());
                automovelDTO.setTipo(automovel.getTipoAutomovel());
                automovelDTOs.add(automovelDTO);
                automovelDTO = new AutomovelDTO();
            }
            resultSelectAutomoveis.setItems(automovelDTOs);
        }
        return resultSelectAutomoveis;
    }
    
}
