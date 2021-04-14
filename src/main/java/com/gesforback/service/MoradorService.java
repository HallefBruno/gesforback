
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
        Optional<Morador> moradorCadastrado = moradorRepository.findByCpfOrRgOrResidencia(morador.getCpf(),morador.getRg(), morador.getResidencia());
        moradorCadastrado.ifPresent((f) -> {
            String mensagem = "Esse morador já foi cadastrada ";
            if(f.getCpf().equals(morador.getCpf()))
                mensagem = "CPF: "+f.getCpf();
            if(f.getRg().equals(morador.getRg()))
                mensagem = "Rg: "+f.getCpf();
            if(f.getResidencia().equals(morador.getResidencia()))
                mensagem = "Residência: "+f.getResidencia();
            throw new NegocioException(mensagem);
        });
        Morador novoMorador = moradorRepository.save(morador);
        return novoMorador;
        
    }   
}