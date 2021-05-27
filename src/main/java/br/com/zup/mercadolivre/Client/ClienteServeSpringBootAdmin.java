package br.com.zup.mercadolivre.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "springbootadmn", url = "http://localhost:8080/verify")
public interface ClienteServeSpringBootAdmin {

    @RequestMapping(method = RequestMethod.GET)
    String verficaDisponibilidade();
}