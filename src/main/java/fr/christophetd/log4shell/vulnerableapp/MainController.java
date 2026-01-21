package fr.christophetd.log4shell.vulnerableapp;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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

@GetMapping("/hotspots")
    public String securityHotspotsDemo() throws Exception {
        // 1. HOTSPOT: Uso de Random (en lugar de SecureRandom)
        // SonarCloud dirá que es predecible y no apto para seguridad.
        Random bruteForceTarget = new Random();
        int weakToken = bruteForceTarget.nextInt();

        // 2. HOTSPOT: Uso de un algoritmo de cifrado débil (DES)
        // DES es obsoleto y fácil de romper. Sonar pedirá usar AES.
        String secret = "secret12"; // 8 bytes para DES
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES"); 
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return "Hotspots generados en el código. Revisa tu panel de SonarCloud.";
    }

}