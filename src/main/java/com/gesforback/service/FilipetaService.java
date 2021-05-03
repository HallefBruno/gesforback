
package com.gesforback.service;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Filipeta;
import com.gesforback.exception.NegocioException;
import com.gesforback.exception.NonNullRuntimeException;
import com.gesforback.exception.NotFoundRuntimeException;
import com.gesforback.repository.FilipetaRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilipetaService {
    
    @Autowired
    private FilipetaRepository filipetaRepository;
    
    @Transactional
    public Filipeta salvar(Filipeta filipeta) {
        Optional<Filipeta> filipetaCadastrada = filipetaRepository.findByNumeroAndPortaria(filipeta.getNumero(), filipeta.getPortaria());
        filipetaCadastrada.ifPresent((f) -> {
            throw new NegocioException("Essa filipe já foi cadastrada");
        });
        Filipeta nova = filipetaRepository.save(filipeta);
        return nova;
    }
    
    @Transactional
    public Filipeta update(Filipeta filipetaUpdate, UUID id) {
        if (Objects.nonNull(id)) {
            Optional<Filipeta> filipetaCadastrada = filipetaRepository.findByNumeroAndPortaria(filipetaUpdate.getNumero(), filipetaUpdate.getPortaria());
            if(filipetaCadastrada.isPresent()) {
                throw new NegocioException("Não é permitido filipeta repetida para a mesma portaria!");
            }
            Optional<Filipeta> filipetaAtual = filipetaRepository.findById(id);
            if (filipetaAtual.isPresent()) {
                BeanUtils.copyProperties(filipetaUpdate, filipetaAtual.get(), "id");
                filipetaUpdate = filipetaRepository.save(filipetaAtual.get());
                return filipetaUpdate;
            }
            return filipetaAtual.orElseThrow(() -> new NotFoundRuntimeException("Nenhuma filipeta encontrado!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    @Transactional
    public void deletar(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Filipeta> filipeta = filipetaRepository.findById(id);
            if (filipeta.isPresent()) {
                filipetaRepository.deleteById(id);
            } else {
                filipeta.orElseThrow(() -> new NotFoundRuntimeException("Nenhuma filipeta encontrado!"));
            }
        } else {
            throw new NonNullRuntimeException("Id não pode ser null");
        }
    }
    
    public Filipeta buscarPorId(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Filipeta> filipeta = filipetaRepository.findById(id);
            if(filipeta.isPresent()) {
                return filipeta.get();
            }
            return filipeta.orElseThrow(() -> new NotFoundRuntimeException("Nenhuma filipeta encontrado!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    public DataTable<Filipeta> todos(String numero, String nomePortaria,int draw, int start,int length) {
        return filipetaRepository.filtrar(numero, nomePortaria, draw, start, length);
    }
    
    public List<Filipeta> todos() {
        return filipetaRepository.findAll();
    }

}


//if (filipetaAtual.isPresent()) {
//    Optional<Filipeta> filipetaCadastrada = filipetaRepository.findByNumeroAndPortaria(filipetaUpdate.getNumero(), filipetaUpdate.getPortaria());
//if(ObjectUtils.notEqual(filipetaAtual, filipetaCadastrada)) {
//    System.out.println("okok");
//}
//                if(filipetaAtual.equals(filipetaCadastrada)) {
//                    if(filipetaCadastrada.isPresent()) {
//                        throw new NegocioException("Não é permitido ter na mesma portaria dois registros iguais!");
//                    }
//                }