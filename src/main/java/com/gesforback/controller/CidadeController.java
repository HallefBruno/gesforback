
package com.gesforback.controller;

import com.gesforback.entity.Cidade;
import com.gesforback.entity.DataTable;
import com.gesforback.service.CidadeService;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author sud
 */
@RestController
@RequestMapping(path = {"cidades"})
public class CidadeController {
    
    @Autowired
    private CidadeService cidadeService;
    
    @PostMapping(path = {"salvar"})
    public ResponseEntity<?> salvar(@Valid @RequestBody Cidade cidade) {
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/cidades/buscar/{id}").buildAndExpand(cidadeService.salvar(cidade).getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    @GetMapping(path = {"todos"})
    public ResponseEntity<?> getAllCidades(
        @RequestParam(name = "draw", required = false) Integer draw, 
        @RequestParam(name = "start", required = false) Integer start,  
        @RequestParam(name = "length", required = false) Integer length,
        @RequestParam(required = false) String nomeEstado,
        @RequestParam(required = false) String nomeCidade
    ){
        if(draw != null) {
            DataTable page = cidadeService.todos(draw, start, length, nomeEstado,nomeCidade);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        return ResponseEntity.ok(cidadeService.todos());
        
    }
    
    @PutMapping(path = {"alterar/{id}"})
    public ResponseEntity<Cidade> alterar(@Valid @RequestBody Cidade cidade, @PathVariable(required = true) UUID id) { 
        Cidade cidadeUpdate = cidadeService.update(cidade, id);
        return ResponseEntity.ok(cidadeUpdate);
    }
    
    @GetMapping(path = {"buscar/{id}"})
    public ResponseEntity<Cidade> getCidade(@PathVariable(required = true) UUID id) {
        return ResponseEntity.ok(cidadeService.buscarPorId(id));
    }
    
    @DeleteMapping(path = {"excluir/{id}"})
    public ResponseEntity<?> deletar(@PathVariable(required = true) UUID id) {
        cidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
