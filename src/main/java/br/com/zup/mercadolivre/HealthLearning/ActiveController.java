package br.com.zup.mercadolivre.HealthLearning;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActiveController {

    @GetMapping("/verify")
    public ResponseEntity<?> active(){
        return ResponseEntity.ok("true");
    }
}