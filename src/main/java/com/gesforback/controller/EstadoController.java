
package com.gesforback.controller;

import com.gesforback.entity.Estado;
import com.gesforback.service.EstadoService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sud
 */
@RestController
@RequestMapping(path = {"estados"})
public class EstadoController {
    
    @Autowired
    private EstadoService estadoService;
    
    @PostMapping(path = {"salvar"})
    public ResponseEntity<Estado> salvar(@Valid @RequestBody Estado estado) {
        return ResponseEntity.ok(estadoService.salvar(estado));
    }
    
}
