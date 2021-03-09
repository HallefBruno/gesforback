
package com.gesforback.service;

import com.gesforback.entity.Bairro;
import com.gesforback.entity.DataTable;
import com.gesforback.exception.NegocioException;
import com.gesforback.repository.BairroRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    
    public DataTable<Bairro> todos(String nomeEstado, String nomeCidade,int draw, int start,int length) {
        return bairroRepository.filtrar(nomeEstado, nomeCidade, draw, start, length);
    }
    
    public List<Bairro> todas() {
        return bairroRepository.findAll();
    }
}
