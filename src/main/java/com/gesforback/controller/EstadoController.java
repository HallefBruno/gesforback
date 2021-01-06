
package com.gesforback.controller;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Estado;
import com.gesforback.service.EstadoService;
import java.net.URI;
import java.util.Arrays;
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
@RequestMapping(path = {"estados"})
public class EstadoController {
    
    @Autowired
    private EstadoService estadoService;

    @GetMapping(path = {"buscar/{id}"})
    public ResponseEntity<Estado> getEstado(@PathVariable(required = true) UUID id) {
        return ResponseEntity.ok(estadoService.buscarPorId(id));
    }
    
    @GetMapping(path = {"todos/{filtros}","todos/"})
    public ResponseEntity<?> getAllEstados(
        @PathVariable(required = false) String[] filtros,
        @RequestParam("draw") int draw, 
        @RequestParam("start") int start,  
        @RequestParam("length") int length) 
    {
        Optional<String[]> op = Optional.ofNullable(filtros);
        DataTable page = estadoService.todos(draw, start, length, op.isPresent() ? op.get()[0]:"");
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    
    
    @PostMapping(path = {"salvar"})
    public ResponseEntity<?> salvar(@Valid @RequestBody Estado estado) {//getResponseHeader("Content-Type");
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/estados/buscar/{id}")
                .buildAndExpand(estadoService.salvar(estado).getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }
    
    @PutMapping(path = {"alterar/{id}"})
    public ResponseEntity<Estado> alterar(@Valid @RequestBody Estado estado, @PathVariable(required = true) UUID id) { 
        Estado estadoUpdate = estadoService.update(estado, id);
        return ResponseEntity.ok(estadoUpdate);
    }
    
    @DeleteMapping(path = {"excluir/{id}"})
    public ResponseEntity<?> deletar(@PathVariable(required = true) UUID id) {
        estadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
}


        
//        Gson g = new Gson();
//        RequestParamPageable p = g.fromJson(requestParamPageable, RequestParamPageable.class);
//        Optional<String[]> filters = Optional.ofNullable(filtros);