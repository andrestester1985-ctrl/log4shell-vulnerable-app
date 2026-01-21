package fr.christophetd.log4shell.vulnerableapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", "UP");
        body.put("service", "Log4Shell-Vulnerable-API");
        body.put("version", "1.0.0");
        body.put("checks", "DevSecOps Pipeline Active");
        
        // Devolvemos un 200 OK con el cuerpo JSON
        return ResponseEntity.ok(body);
    }
}