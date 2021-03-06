
package com.gesforback.service;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Estado;
import com.gesforback.exception.NegocioException;
import com.gesforback.exception.NotFoundRuntimeException;
import com.gesforback.exception.NonNullRuntimeException;
import com.gesforback.repository.EstadoRepository;
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
public class EstadoService {
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    @Transactional
    public Estado salvar(Estado estado) {
        
        Optional<Estado> estadoCadastrado = estadoRepository.findByNomeContainingIgnoreCase(estado.getNome());
        if(estadoCadastrado.isPresent()) {
            throw new NegocioException("Esse estado já está cadastrada!");
        }
        estado.setId(UUID.randomUUID());
        Estado novoEstado = estadoRepository.save(estado);
        return novoEstado;
    }
    
    @Transactional
    public Estado update(Estado estadoUpdate, UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Estado> estadoAtual = estadoRepository.findById(id);
            if (estadoAtual.isPresent()) {
                BeanUtils.copyProperties(estadoUpdate,estadoAtual.get(), "id");
                estadoUpdate = estadoRepository.save(estadoAtual.get());
                return estadoUpdate;
            }
            return estadoAtual.orElseThrow(() -> new NotFoundRuntimeException("Nenhum estado encontrado!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    @Transactional
    public void deletar(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Estado> estado = estadoRepository.findById(id);
            if (estado.isPresent()) {
                estadoRepository.deleteById(id);
            } else {
                estado.orElseThrow(() -> new NotFoundRuntimeException("Nenhum estado encontrado!"));
            }
        } else {
            throw new NonNullRuntimeException("Id não pode ser null");
        }
    }
    
    public Estado buscarPorId(UUID id) {
        if(Objects.nonNull(id)) {
            Optional<Estado> estado = estadoRepository.findById(id);
            if(estado.isPresent()) {
                return estado.get();
            }
            return estado.orElseThrow(() -> new NotFoundRuntimeException("Nenhum estado encontrado!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
    }
    
    public DataTable todos(int draw, int start,int length, String nome) {

        int page = start/length;
        Pageable paging = PageRequest.of(page,length,Sort.by("nome").ascending());
        Page<Estado> pagedResult = estadoRepository.findByNomeContainingIgnoreCase(nome,paging);
        DataTable<Estado> dataTable = new DataTable<>();
        dataTable.setData(pagedResult.getContent());
        dataTable.setRecordsTotal(pagedResult.getTotalElements());
        dataTable.setRecordsFiltered(pagedResult.getTotalElements());
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        return dataTable;
    }
    
    public List<Estado> todos() {
        return estadoRepository.findAll();
    }
    
}


//        DataTable dataPage = DataTable.builder()
//                .data(pagedResult.getContent())
//                .recordsTotal(pagedResult.getTotalElements())
//                .recordsFiltered(pagedResult.getTotalElements())
//                .draw(draw)
//                .start(start)
//                .build();