package br.com.zup.mercadolivre.compra;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

public enum MetodoPagamento{
    paypal{
        @Override
       public URI criaUrlRetorno(Compra compra) {
            return UriComponentsBuilder.fromUriString("/return-paypal/{id}").port(8080).buildAndExpand(Map.of("id", compra.getId())).encode().toUri();
        }
    },
    pagseguro{
        @Override
      public  URI criaUrlRetorno(Compra compra) {
            return UriComponentsBuilder.fromUriString("/return-pagseguro/{id}").port(8080).buildAndExpand(Map.of("id", compra.getId())).encode().toUri();
        }
    };

    public abstract URI criaUrlRetorno(Compra compra);
}
