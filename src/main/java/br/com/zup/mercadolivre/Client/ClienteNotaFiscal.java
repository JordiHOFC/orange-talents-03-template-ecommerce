package br.com.zup.mercadolivre.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "notafiscal", url = "http://localhost:8080/compras")
public interface ClienteNotaFiscal {
    @RequestMapping(method = RequestMethod.POST, value = "/{idCompra}/{idUsuario}")
    void solicitaNF(@PathVariable Long idUsuario,@PathVariable Long idCompra,@RequestHeader("Authorization") String token);

}
