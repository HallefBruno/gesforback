
package com.gesforback.controller;

import com.gesforback.entity.Bairro;
import com.gesforback.entity.DataTable;
import com.gesforback.service.BairroService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = {"bairros"})
public class BairroController {
    
    @Autowired
    private BairroService bairroService;
    
    @PostMapping(path = {"salvar"})
    public ResponseEntity<?> salvar(@Valid @RequestBody Bairro bairro) {
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/bairros/buscar/{id}").buildAndExpand(bairroService.salvar(bairro).getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    @GetMapping(path = {"todos"})
    public ResponseEntity<?> getAllBairros(
        @RequestParam(name = "draw", required = false) Integer draw, 
        @RequestParam(name = "start", required = false) Integer start,  
        @RequestParam(name = "length", required = false) Integer length,
        @RequestParam(required = false) String nomeEstado,
        @RequestParam(required = false) String nomeCidade
    ){
        if(draw != null) {
            DataTable page = bairroService.todos(nomeEstado, nomeCidade, draw, start, length);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        return ResponseEntity.ok(bairroService.todas());
        
    }
    
}
