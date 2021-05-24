
package com.gesforback.service;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Morador;
import com.gesforback.entity.MoradorSecundario;
import com.gesforback.entity.Telefone;
import com.gesforback.entity.dto.MoradorDTO;
import com.gesforback.entity.filtros.FiltrosMorador;
import com.gesforback.exception.NonNullRuntimeException;
import com.gesforback.exception.NotFoundRuntimeException;
import com.gesforback.repository.MoradorRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoradorService {
    
    @Autowired
    private MoradorRepository moradorRepository;
    
    @Transactional
    public Morador salvar(Morador morador) {
        Morador novoMorador = moradorRepository.save(morador);
        return novoMorador;
    }
    
    @Transactional
    public Morador update(Morador moradorUpdate, UUID id) {
        if (Objects.nonNull(id)) {
            Optional<Morador> moradorAtual = moradorRepository.findById(id);
            if(moradorAtual.isPresent()) {
                BeanUtils.copyProperties(moradorUpdate,moradorAtual.get(), "id");
                moradorUpdate = moradorRepository.save(moradorAtual.get());
                return moradorUpdate;
            }
            return moradorAtual.orElseThrow(() -> new NotFoundRuntimeException("Nenhum morador encontrada!"));
        }
        throw new NonNullRuntimeException("Id não pode ser null");
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
    
    public DataTable<MoradorDTO> todos(String filtros, int draw, int start) {
        Gson converter = new Gson();
        FiltrosMorador filtrosMorador = converter.fromJson(filtros, FiltrosMorador.class);
        DataTable<Morador> moradores = moradorRepository.filtrar(filtrosMorador, draw, start);
        List<MoradorDTO> novaListaMoradores = new ArrayList<>();
        DataTable<MoradorDTO> dataTable = new DataTable<>();
        
        for (Morador morador : moradores.getData()) {
            String telefones = morador.getTelefones().stream().map(Telefone::getNumero).collect(Collectors.joining(", "));
            novaListaMoradores.add(new MoradorDTO(morador.getId(),morador.getNome(), morador.getCpf(), telefones, morador.getResidencia(), morador.getSexo(), morador.getEstadoCivil().getDescricao(), Boolean.TRUE));
            if (!filtrosMorador.isIsProprietario()) {
                for (MoradorSecundario moradorSecundario : morador.getMoradorSecundarios()) {
                    novaListaMoradores.add(new MoradorDTO(morador.getId(),moradorSecundario.getNome(), moradorSecundario.getCpf(), moradorSecundario.getTelefone(), moradorSecundario.getMorador().getResidencia(), moradorSecundario.getSexo(), moradorSecundario.getEstadoCivil().getDescricao(), Boolean.FALSE));
                }
            }
        }
        
        dataTable.setData(novaListaMoradores);
        dataTable.setDraw(moradores.getDraw());
        dataTable.setStart(moradores.getStart());
        dataTable.setRecordsFiltered(moradores.getRecordsFiltered());
        dataTable.setRecordsTotal(moradores.getRecordsTotal());
        return dataTable;
    }
    
    public List<Morador> todos() {
        return moradorRepository.findAll();
    }
}


/**
 * public DataTable<MoradorDTO> todos(String filtros, int draw, int start) {
        Gson converter = new Gson();
        FiltrosMorador filtrosMorador = converter.fromJson(filtros, FiltrosMorador.class);
        DataTable<Morador> moradores = moradorRepository.filtrar(filtrosMorador, draw, start);
        List<MoradorDTO> novaListaMoradores = new ArrayList<>();
        DataTable<MoradorDTO> dataTable = new DataTable<>();
        
        for(Morador morador : moradores.getData()) {
            String telefones = morador.getTelefones().stream().map(Telefone::getNumero).collect(Collectors.joining(", "));
            novaListaMoradores.add(new MoradorDTO(morador.getNome(), morador.getCpf(), telefones, morador.getResidencia(), morador.getSexo(), morador.getEstadoCivil().getDescricao()));
            for(MoradorSecundario moradorSecundario : morador.getMoradorSecundarios()) {
                novaListaMoradores.add(new MoradorDTO(moradorSecundario.getNome(), moradorSecundario.getCpf(), moradorSecundario.getTelefone(), moradorSecundario.getMorador().getResidencia(), moradorSecundario.getSexo(), moradorSecundario.getEstadoCivil().getDescricao()));
            }
        }
        
        dataTable.setData(novaListaMoradores);
        dataTable.setDraw(moradores.getDraw());
        dataTable.setStart(moradores.getStart());
        dataTable.setRecordsFiltered(novaListaMoradores.size());
        dataTable.setRecordsTotal(novaListaMoradores.size());
        return dataTable;
    }
    * //    public DataTable<MoradorDTO> todos(String filtros, int draw, int start) {
//        Gson converter = new Gson();
//        FiltrosMorador filtrosMorador = converter.fromJson(filtros, FiltrosMorador.class);
//        return null;
//    }
 */