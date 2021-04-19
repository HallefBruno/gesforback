
package com.gesforback.service;

import com.gesforback.entity.Morador;
import com.gesforback.exception.NonNullRuntimeException;
import com.gesforback.exception.NotFoundRuntimeException;
import com.gesforback.repository.MoradorRepository;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoradorService {
    
    @Autowired
    private MoradorRepository moradorRepository;
    
    @Transactional
    public Morador salvar(Morador morador) {
        Morador novoMorador = moradorRepository.save(morador);
        return novoMorador;
    }
    
    public Morador buscarPorId(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Morador> morador = moradorRepository.findById(id);
            if(morador.isPresent()) {
                return morador.get();
            }
            return morador.orElseThrow(() -> new NotFoundRuntimeException("Nenhum morador encontrado!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
}