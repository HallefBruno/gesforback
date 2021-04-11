
package com.gesforback.service;

import com.gesforback.entity.Morador;
import com.gesforback.exception.NegocioException;
import com.gesforback.repository.MoradorRepository;
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
        Optional<Morador> moradorCadastrado = moradorRepository.findByCpf(morador.getCpf());
        moradorCadastrado.ifPresent((f) -> {
            throw new NegocioException("Esse morador já foi cadastrada");
        });
        morador.setId(UUID.randomUUID());
        morador.getTelefones().forEach(tel -> {
            tel.setId(UUID.randomUUID());
        });
        Morador novoMorador = moradorRepository.save(morador);
        return novoMorador;
    }
    
}
