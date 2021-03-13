
package com.gesforback.service;

import com.gesforback.entity.Bairro;
import com.gesforback.entity.Cidade;
import com.gesforback.entity.DataTable;
import com.gesforback.exception.NegocioException;
import com.gesforback.exception.NonNullRuntimeException;
import com.gesforback.exception.NotFoundRuntimeException;
import com.gesforback.repository.BairroRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BairroService {
    
    @Autowired
    private BairroRepository bairroRepository;
    
    @Transactional
    public Bairro salvar(Bairro bairro) {
        Optional<Bairro> bairroCadastrado = bairroRepository.findByNomeContainingIgnoreCaseAndCidade(bairro.getNome(), bairro.getCidade());
        if(bairroCadastrado.isPresent()) {
            throw new NegocioException("Esse bairro já está cadastrada!");
        }
        bairro.setId(UUID.randomUUID());
        Bairro novoBairro = bairroRepository.save(bairro);
        return novoBairro;
    }
    
    @Transactional
    public Bairro update(Bairro bairroUpdate, UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Bairro> bairroAtual = bairroRepository.findById(id);
            if (bairroAtual.isPresent()) {
                BeanUtils.copyProperties(bairroUpdate,bairroAtual.get(), "id");
                bairroUpdate = bairroRepository.save(bairroAtual.get());
                return bairroUpdate;
            }
            return bairroAtual.orElseThrow(() -> new NotFoundRuntimeException("Nenhum bairro encontrado!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    @Transactional
    public void deletar(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Bairro> bairro = bairroRepository.findById(id);
            if (bairro.isPresent()) {
                bairroRepository.deleteById(id);
            } else {
                bairro.orElseThrow(() -> new NotFoundRuntimeException("Nenhum bairro encontrado!"));
            }
        } else {
            throw new NonNullRuntimeException("Id não pode ser null");
        }
    }
    
    public Bairro buscarPorId(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Bairro> bairro = bairroRepository.findById(id);
            if(bairro.isPresent()) {
                return bairro.get();
            }
            return bairro.orElseThrow(() -> new NotFoundRuntimeException("Nenhum bairro encontrado!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    public DataTable<Bairro> todos(String nomeEstado, String nomeCidade,int draw, int start,int length) {
        return bairroRepository.filtrar(nomeEstado, nomeCidade, draw, start, length);
    }
    
    public List<Bairro> todos() {
        return bairroRepository.findAll();
    }
}
