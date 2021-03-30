
package com.gesforback.service;

import com.gesforback.entity.Cidade;
import com.gesforback.entity.DataTable;
import com.gesforback.exception.NegocioException;
import com.gesforback.exception.NonNullRuntimeException;
import com.gesforback.exception.NotFoundRuntimeException;
import com.gesforback.repository.CidadeRepository;
import com.gesforback.repository.CidadeRepositoryCustom;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sud
 */
@Service
public class CidadeService {
    
    @Autowired
    private CidadeRepository cidadeRepository;
    
    @Autowired
    private CidadeRepositoryCustom cidadeRepositoryCustom;

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Optional<Cidade> cidadeCadastrada = cidadeRepository.findByEstadoAndNomeContainingIgnoreCase(cidade.getEstado(), cidade.getNome());
        if(cidadeCadastrada.isPresent()) {
            throw new NegocioException("Essa cidade já está cadastrada!");
        }
        cidade.setId(UUID.randomUUID());
        Cidade novaCidade = cidadeRepository.save(cidade);
        return novaCidade;
    }
    
    @Transactional
    public Cidade update(Cidade cidadeUpdate, UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Cidade> cidadeAtual = cidadeRepository.findById(id);
            if (cidadeAtual.isPresent()) {
                BeanUtils.copyProperties(cidadeUpdate,cidadeAtual.get(), "id");
                cidadeUpdate = cidadeRepository.save(cidadeAtual.get());
                return cidadeUpdate;
            }
            return cidadeAtual.orElseThrow(() -> new NotFoundRuntimeException("Nenhuma cidade encontrada!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    @Transactional
    public void deletar(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Cidade> cidade = cidadeRepository.findById(id);
            if (cidade.isPresent()) {
                cidadeRepository.deleteById(id);
            } else {
                cidade.orElseThrow(() -> new NotFoundRuntimeException("Nenhuma cidade encontrada!"));
            }
        } else {
            throw new NonNullRuntimeException("Id não pode ser null");
        }
    }
    
    public Cidade buscarPorId(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Cidade> cidade = cidadeRepository.findById(id);
            if(cidade.isPresent()) {
                return cidade.get();
            }
            return cidade.orElseThrow(() -> new NotFoundRuntimeException("Nenhuma cidade encontrada!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    public DataTable<Cidade> todos(String nomeEstado, String nomeCidade,int draw, int start,int length) {
        return cidadeRepositoryCustom.findByCidade(nomeEstado, nomeCidade, draw, start, length);
    }
    
    public List<Cidade> todas() {
        return cidadeRepository.findAll();
    }
}
