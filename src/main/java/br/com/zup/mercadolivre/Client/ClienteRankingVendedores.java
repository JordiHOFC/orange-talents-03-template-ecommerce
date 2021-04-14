package br.com.zup.mercadolivre.Client;

import br.com.zup.mercadolivre.handler.MensageReponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "rankingvendedores", url = "http://localhost:8080/rankingvendedores")
public interface ClienteRankingVendedores {
    @RequestMapping(method = RequestMethod.POST, value = "/{idVendedor}/{idCompra}")
    MensageReponse atualizaRanking(@PathVariable Long idVendedor, @PathVariable Long idCompra, @RequestHeader("Authorization") String token);

}
