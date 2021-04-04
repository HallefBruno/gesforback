package com.gesforback;

import com.gesforback.service.EnviarEmailService;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GesforbackApplication {

    @Autowired
    EnviarEmailService enviarEmailService;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        SpringApplication.run(GesforbackApplication.class, args);
    }

}
