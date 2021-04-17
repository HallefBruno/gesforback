
package com.gesforback.service;

import com.gesforback.entity.Morador;
import com.gesforback.exception.NegocioException;
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
        Optional<Morador> moradorCadastrado = moradorRepository.findByCpfOrRgOrResidencia(morador.getCpf(),morador.getRg(), morador.getResidencia());
        moradorCadastrado.ifPresent((f) -> {
            String mensagem = "Esse morador já foi cadastrada ";
            if(f.getCpf().equals(morador.getCpf()))
                mensagem = mensagem + "CPF: "+f.getCpf();
            if(f.getRg().equals(morador.getRg()))
                mensagem = mensagem + "Rg: "+f.getCpf();
            if(f.getResidencia().equals(morador.getResidencia()))
                mensagem = mensagem + "Residência: "+f.getResidencia();
            throw new NegocioException(mensagem);
        });
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