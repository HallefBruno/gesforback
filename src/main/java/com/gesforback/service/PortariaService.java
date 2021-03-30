
package com.gesforback.service;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Portaria;
import com.gesforback.exception.NegocioException;
import com.gesforback.exception.NonNullRuntimeException;
import com.gesforback.exception.NotFoundRuntimeException;
import com.gesforback.repository.PortariaRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sud
 */
@Service
public class PortariaService {
    
    @Autowired
    private PortariaRepository portariaRepository;
    
    @Transactional
    public Portaria salvar(Portaria portaria) {
        
        Optional<Portaria> portariaCadastrada = portariaRepository.findByNomeContainingIgnoreCase(portaria.getNome());
        if(portariaCadastrada.isPresent()) {
            throw new NegocioException("Essa portaria já está cadastrada!");
        }
        portaria.setId(UUID.randomUUID());
        Portaria novaPortaria = portariaRepository.save(portaria);
        return novaPortaria;
        
    }
    
    @Transactional
    public Portaria update(Portaria portariaUpdate, UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Portaria> portariaAtual = portariaRepository.findById(id);
            if (portariaAtual.isPresent()) {
                BeanUtils.copyProperties(portariaUpdate,portariaAtual.get(), "id");
                portariaUpdate = portariaRepository.save(portariaAtual.get());
                return portariaUpdate;
            }
            return portariaAtual.orElseThrow(() -> new NotFoundRuntimeException("Nenhuma portaria encontrada!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    @Transactional
    public void deletar(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Portaria> portaria = portariaRepository.findById(id);
            if (portaria.isPresent()) {
                portariaRepository.deleteById(id);
            } else {
                portaria.orElseThrow(() -> new NotFoundRuntimeException("Nenhuma portaria encontrada!"));
            }
        } else {
            throw new NonNullRuntimeException("Id não pode ser null");
        }
    }
    
    public Portaria buscarPorId(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Portaria> portaria = portariaRepository.findById(id);
            if(portaria.isPresent()) {
                return portaria.get();
            }
            return portaria.orElseThrow(() -> new NotFoundRuntimeException("Nenhuma portaria encontrada!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    public DataTable todos(int draw, int start,int length, String nome) {
        int page = start/length;
        Pageable paging = PageRequest.of(page,length,Sort.by("nome").ascending());
        Page<Portaria> pagedResult = portariaRepository.findByNomeContainingIgnoreCase(nome,paging);
        DataTable<Portaria> dataTable = new DataTable<>();
        dataTable.setData(pagedResult.getContent());
        dataTable.setRecordsTotal(pagedResult.getTotalElements());
        dataTable.setRecordsFiltered(pagedResult.getTotalElements());
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        return dataTable;
    }
     
    public List<Portaria> todos() {
        return portariaRepository.findAll();
    }
    
}
