
package com.gesforback.service;

import com.gesforback.entity.Morador;
import com.gesforback.entity.Telefone;
import com.gesforback.exception.NegocioException;
import com.gesforback.repository.MoradorRepository;
import java.util.List;
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
            throw new NegocioException("Essa filipe já foi cadastrada");
        });
        morador.setId(UUID.randomUUID());
        morador.getTelefones().forEach(telefone -> {
            telefone.setId(UUID.randomUUID());
            Morador m = new Morador();
            m.setId(morador.getId());
            telefone.setMorador(m);
        });
        Morador nova = moradorRepository.save(morador);
        return nova;
    }
    
}
