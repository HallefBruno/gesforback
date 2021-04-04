
package com.gesforback.service;

import com.gesforback.entity.Fabricante;
import com.gesforback.repository.FabricanteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FabricanteService {
    
    @Autowired
    private FabricanteRepository fabricanteRepository;
    
    public List<Fabricante> fabricantes() {
        return fabricanteRepository.findAll();
    }
    
}
