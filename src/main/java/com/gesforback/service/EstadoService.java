
package com.gesforback.service;

import com.gesforback.entity.Estado;
import com.gesforback.repository.EstadoRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sud
 */
@Service
public class EstadoService {
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    @Transactional
    public Estado salvar(Estado estado) {
        estado.setId(UUID.randomUUID());
        Estado novoEstado = estadoRepository.save(estado);
        return novoEstado;
    }
    
    @Transactional
    public Estado update(Estado estado, UUID id) {
        if(id != null) {
            Optional<Estado> estadoAtual = estadoRepository.findById(id);
            if(estadoAtual.isPresent()) {
                
            }
        }
        return null;
    }
    
}
