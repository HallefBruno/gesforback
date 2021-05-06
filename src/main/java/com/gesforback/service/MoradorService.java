
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
    
    public DataTable<MoradorDTO> todos(FiltrosMorador filtrosMorador, int draw, int start) {
        
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
    
    public List<Morador> todos() {
        return moradorRepository.findAll();
    }
}