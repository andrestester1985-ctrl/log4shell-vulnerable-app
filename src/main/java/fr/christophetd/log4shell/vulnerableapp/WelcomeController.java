package fr.christophetd.log4shell.vulnerableapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Vulnerable App is Online");
        response.put("message", "DevSecOps Pipeline: DAST stage in progress");
        response.put("target", "CVE-2021-44228 Analysis");
        return response;
    }
}