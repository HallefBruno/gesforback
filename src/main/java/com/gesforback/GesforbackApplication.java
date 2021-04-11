package com.gesforback;

import com.gesforback.entity.Automovel;
import com.gesforback.entity.EstadoCivil;
import com.gesforback.entity.Fabricante;
import com.gesforback.entity.Morador;
import com.gesforback.entity.Telefone;
import com.gesforback.entity.TipoResidencia;
import com.gesforback.service.MoradorService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GesforbackApplication implements CommandLineRunner {

    @Autowired
    private MoradorService moradorService;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        SpringApplication.run(GesforbackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        
//        Morador morador = new Morador();
//        List<Telefone> telefones = new ArrayList<>();
//        
//        morador.setId(UUID.randomUUID());
//        morador.setNome("Antonio");
//        morador.setCpf("00258444444");
//        morador.setRg("987654");
//        morador.setNaturalidade("Ceres");
//        morador.setOrgaoEmissor("SSP-GO");
//        morador.setSexo("Masculino");
//        morador.setAnimalDomestico(Boolean.FALSE);
//        morador.setDataNascimento(new Date());
//        morador.setEstadoCivil(EstadoCivil.CASADO);
//        morador.setQdtMoradores(3);
//        morador.setTipoMoradia(TipoResidencia.CASA);
//        morador.setResidencia("CASA 4");
//        
//        for(int i=0; i<4; i++) {
//            Telefone telefone = new Telefone();
//            telefone.setId(UUID.randomUUID());
//            telefone.setNumero("621234567"+i);
//            telefone.setMorador(morador);
//            telefones.add(telefone);
//        }
        
//        Fabricante fabricate = new Fabricante();
//        fabricate.setId(32L);
//        
//        Automovel automovel1 = new Automovel();
//        automovel1.setId(25L);
//        automovel1.setNome("A6 SEDAN");
//        automovel1.setTipoAutomovel("C");
//        automovel1.setFabricante(fabricate);
//        
//        Automovel automovel2 = new Automovel();
//        automovel2.setId(194L);
//        automovel2.setNome("SIENA");
//        automovel2.setTipoAutomovel("C");
//        automovel2.setFabricante(fabricate);
//        
//        List<Automovel> automovels = new ArrayList<>();
//        automovels.add(automovel1);
//        automovels.add(automovel2);
//        
//        morador.setAutomoveis(automovels);
//        morador.setTelefones(telefones);
//        
//        moradorService.salvar(morador);
    }

}
