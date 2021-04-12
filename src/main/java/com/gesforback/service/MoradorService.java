
package com.gesforback.service;

import com.gesforback.entity.Morador;
import com.gesforback.exception.NegocioException;
import com.gesforback.repository.MoradorRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoradorService {
    
    @Autowired
    private MoradorRepository moradorRepository;
    
    @Transactional
    public Morador salvar(Morador morador) {
        Optional<Morador> moradorCadastrado = moradorRepository.findByCpfOrResidencia(morador.getCpf(), morador.getResidencia());
        moradorCadastrado.ifPresent((f) -> {
            throw new NegocioException("Esse morador já foi cadastrada "+f.getCpf());
        });
        Morador novoMorador = moradorRepository.save(morador);
        return novoMorador;
        
    }   
}