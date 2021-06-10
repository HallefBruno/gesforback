
package com.gesforback.controller;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.EstadoCivil;
import com.gesforback.entity.GrauParentesco;
import com.gesforback.entity.Morador;
import com.gesforback.entity.TipoResidencia;
import com.gesforback.entity.dto.FabricanteDTO;
import com.gesforback.entity.dto.GrauParentescoDTO;
import com.gesforback.entity.dto.SexoDTO;
import com.gesforback.entity.dto.TipoResidenciaDTO;
import com.gesforback.service.AutomovelService;
import com.gesforback.service.FabricanteService;
import com.gesforback.service.MoradorService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = {"morador"})
public class MoradorController {
    
    private FabricanteService fabricanteService;
    private AutomovelService automovelService;
    private MoradorService moradorService;
    
    @Autowired
    public MoradorController(FabricanteService fabricanteService, AutomovelService automovelService, MoradorService moradorService) {
        this.fabricanteService = fabricanteService;
        this.automovelService = automovelService;
        this.moradorService = moradorService;
    }

    @PostMapping(path = {"salvar"})
    public ResponseEntity<?> salvar(@Valid @RequestBody Morador morador) {
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/morador/buscar/{id}").buildAndExpand(moradorService.salvar(morador).getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    @GetMapping(path = {"buscar/{id}"})
    public ResponseEntity<Morador> buscarPorId(@PathVariable(required = true) UUID id) {
        return ResponseEntity.ok(moradorService.buscarPorId(id));
    }
    
    @GetMapping("estado-civil")
    public ResponseEntity<List<SexoDTO>> estadosCivil() {
        List<SexoDTO> estadoCivil = new ArrayList<>();
        for (EstadoCivil civil : EstadoCivil.values()) {
            estadoCivil.add(new SexoDTO(civil.name(), civil.getDescricao()));
        }
        return new ResponseEntity<>(estadoCivil, HttpStatus.OK);
    }
    
    @GetMapping("tipo-residencia")
    public ResponseEntity<List<TipoResidenciaDTO>> tipoMoradia() {
        List<TipoResidenciaDTO> tiposResidencias = new ArrayList<>();
        for (TipoResidencia tipoMoradia : TipoResidencia.values()) {
            tiposResidencias.add(new TipoResidenciaDTO(tipoMoradia.name(), tipoMoradia.getDescricao()));
        }
        return new ResponseEntity<>(tiposResidencias, HttpStatus.OK);
    }
    
    @GetMapping("grau-parentesco")
    public ResponseEntity<List<GrauParentescoDTO>> grauParentesco() {
        List<GrauParentescoDTO> grauParentescos = new ArrayList<>();
        for (GrauParentesco grauParentesco : GrauParentesco.values()) {
            grauParentescos.add(new GrauParentescoDTO(grauParentesco.name(), grauParentesco.getDescricao()));
        }
        return new ResponseEntity<>(grauParentescos, HttpStatus.OK);
    }
    
    @GetMapping("fabricantes")
    public List<FabricanteDTO> fabricantes() {
        List<FabricanteDTO> fabricantes = new ArrayList<>();
        fabricanteService.fabricantes().forEach((fabricante) -> {
            fabricantes.add(new FabricanteDTO(fabricante.getId().toString(), fabricante.getNome()));
        });
        return fabricantes;
    }
    
    @GetMapping("automoveis")
    public ResponseEntity<?> automoveis(
            @RequestParam(name = "q", required = false) String automovel,
            @RequestParam(name = "page",defaultValue = "0", required = false) String page,
            @RequestParam(name = "fabricanteId", required = true) String fabricanteId) {
        return new ResponseEntity<>(automovelService.automoveis(automovel,fabricanteId, page),HttpStatus.OK);
    }
    
    @GetMapping(path = {"todos"})
    public ResponseEntity<?> filtrar(
        @RequestParam(name = "draw", required = false) Integer draw, 
        @RequestParam(name = "start", required = false) Integer start,  
        @RequestParam(name = "length", required = false) Integer length, 
        @RequestParam(name = "filtrosMorador",required = false) String filtrosMorador)
    {
        if(draw != null) {
            DataTable page = moradorService.todos(filtrosMorador,draw, start);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        return new ResponseEntity<>(moradorService.todos(), HttpStatus.OK);
    }
    
    @PutMapping(path = {"alterar/{id}"})
    public ResponseEntity<Morador> alterar(@Valid @RequestBody Morador morador, @PathVariable(required = true) UUID id) { 
        Morador moradorUpdate = moradorService.update(morador, id);
        return ResponseEntity.ok(moradorUpdate);
    }
    
}
