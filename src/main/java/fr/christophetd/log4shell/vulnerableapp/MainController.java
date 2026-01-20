package fr.christophetd.log4shell.vulnerableapp;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class MainController {

    private static final Logger logger = LogManager.getLogger("HelloWorld");

    @GetMapping("/")
    public String index(@RequestHeader("X-Api-Version") String apiVersion) {
        logger.info("Received a request for API version " + apiVersion);
        return "Hello, world!";
    }

    @GetMapping("/read")
public String readFile(@RequestParam("file") String fileName) {
    try {
        // VULNERABILIDAD: Se toma el nombre del archivo directamente del usuario
        // sin validar si contiene "../" o rutas absolutas.
        return new String(Files.readAllBytes(Paths.get(fileName)));
    } catch (IOException e) {
        return "Error al leer el archivo: " + e.getMessage();
    }
}

}