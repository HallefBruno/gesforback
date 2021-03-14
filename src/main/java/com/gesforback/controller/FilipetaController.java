
package com.gesforback.controller;


import com.gesforback.entity.DataTable;
import com.gesforback.entity.Filipeta;
import com.gesforback.service.FilipetaService;
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

@RestController
@RequestMapping(path = {"filipetas"})
public class FilipetaController {
    
    @Autowired
    private FilipetaService filipetaService;
    
    @PostMapping(path = {"salvar"})
    public ResponseEntity<?> salvar(@Valid @RequestBody Filipeta filipeta) {
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/filipetas/buscar/{id}").buildAndExpand(filipetaService.salvar(filipeta).getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    @GetMapping(path = {"todos"})
    public ResponseEntity<?> filtrar(
        @RequestParam(name = "draw", required = false) Integer draw, 
        @RequestParam(name = "start", required = false) Integer start,  
        @RequestParam(name = "length", required = false) Integer length,
        @RequestParam(required = false) String numero,
        @RequestParam(required = false) String nomePortaria
    ){
        if(draw != null) {
            DataTable page = filipetaService.todos(numero, nomePortaria, draw, start, length);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        return ResponseEntity.ok(filipetaService.todos());
        
    }
    
    @PutMapping(path = {"alterar/{id}"})
    public ResponseEntity<Filipeta> alterar(@Valid @RequestBody Filipeta filipeta, @PathVariable(required = true) UUID id) { 
        Filipeta filipeUpdate = filipetaService.update(filipeta, id);
        return ResponseEntity.ok(filipeUpdate);
    }
    
    @DeleteMapping(path = {"excluir/{id}"})
    public ResponseEntity<?> deletar(@PathVariable(required = true) UUID id) {
        filipetaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = {"buscar/{id}"})
    public ResponseEntity<Filipeta> buscarPorId(@PathVariable(required = true) UUID id) {
        return ResponseEntity.ok(filipetaService.buscarPorId(id));
    }
    
}
