
package com.gesforback.controller;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Portaria;
import com.gesforback.service.PortariaService;
import java.net.URI;
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
@RequestMapping(path = {"portarias"})
public class PortariaController {
    
    @Autowired
    private PortariaService portariaService;
    
    @PostMapping(path = {"salvar"})
    public ResponseEntity<?> salvar(@Valid @RequestBody Portaria portaria) {//getResponseHeader("Content-Type");
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/portarias/buscar/{id}")
                .buildAndExpand(portariaService.salvar(portaria).getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }
    
    @GetMapping(path = {"todos"})
    public ResponseEntity<?> filtrar(
        @RequestParam(name = "draw", required = false) Integer draw, 
        @RequestParam(name = "start", required = false) Integer start,  
        @RequestParam(name = "length", required = false) Integer length,
        @RequestParam(required = false) String nomePortaria
    ){
        if(draw != null) {
            DataTable page = portariaService.todos(draw, start, length, nomePortaria);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        return ResponseEntity.ok(portariaService.todos());
        
    }
    
    @PutMapping(path = {"alterar/{id}"})
    public ResponseEntity<Portaria> update(@Valid @RequestBody Portaria portaria, @PathVariable(required = true) UUID id) { 
        Portaria portariaUpdate = portariaService.update(portaria, id);
        return ResponseEntity.ok(portariaUpdate);
    }
    
    @DeleteMapping(path = {"excluir/{id}"})
    public ResponseEntity<?> deletar(@PathVariable(required = true) UUID id) {
        portariaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(path = {"buscar/{id}"})
    public ResponseEntity<Portaria> buscarPorId(@PathVariable(required = true) UUID id) {
        return ResponseEntity.ok(portariaService.buscarPorId(id));
    }
    
}
