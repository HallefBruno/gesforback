package com.gesforback;

import com.gesforback.service.EnviarEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GesforbackApplication {
    
    @Autowired
    EnviarEmailService enviarEmailService;
    
    public static void main(String[] args) {
        SpringApplication.run(GesforbackApplication.class, args);
    }

}
