
package com.gesforback.service;

import com.gesforback.entity.Estado;
import com.gesforback.exception.NotFoundRuntimeException;
import com.gesforback.exception.NonNullRuntimeException;
import com.gesforback.repository.EstadoRepository;
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
public class EstadoService {
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    @Transactional
    public Estado salvar(Estado estado) {
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
    
}
