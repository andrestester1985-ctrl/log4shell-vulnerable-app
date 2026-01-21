package fr.christophetd.log4shell.vulnerableapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity; // IMPORTANTE: Corrige el error de ResponseEntity
import java.util.LinkedHashMap;              // IMPORTANTE: Corrige el error de LinkedHashMap
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
        
        // Retorna un JSON con código 200 OK
        return ResponseEntity.ok(body);
    }
}