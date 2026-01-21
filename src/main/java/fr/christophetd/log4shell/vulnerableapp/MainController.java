package fr.christophetd.log4shell.vulnerableapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import java.util.Random;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class MainController {

    private static final Logger logger = LogManager.getLogger("HelloWorld");

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> index(
            @RequestHeader(value = "X-Api-Version", required = false) String apiVersion) {
        
        // Si el header está presente, disparamos el log vulnerable (Log4Shell)
        if (apiVersion != null) {
            logger.info("Received a request for API version " + apiVersion);
        }

        // Devolvemos JSON para que ZAP y el Pipeline detecten el 200 OK
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("endpoints", new String[]{"/", "/read", "/hotspots"});
        response.put("vulnerabilities", "SCA, SAST & DAST Demo Active");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/read")
    public String readFile(@RequestParam("file") String fileName) {
        try {
            // VULNERABILIDAD: Path Traversal
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            return "Error al leer el archivo: " + e.getMessage();
        }
    }

    @GetMapping("/hotspots")
    public String securityHotspotsDemo() throws Exception {
        // 1. HOTSPOT: Uso de Random (predecible)
        Random bruteForceTarget = new Random();
        int weakToken = bruteForceTarget.nextInt();

        // 2. HOTSPOT: Uso de algoritmo débil (DES)
        String secret = "secret12"; 
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES"); 
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return "Hotspots generados. Revisa el panel de SonarCloud para ver Vulnerabilidades de Criptografía.";
    }
}