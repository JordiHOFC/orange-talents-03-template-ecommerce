package br.com.zup.mercadolivre.pagamento;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PagamentosController {

    @PostMapping("return-paypal/{id}")
    public String retornoPayPal(@PathVariable Long id){
        return id.toString();
    }
    @PostMapping("return-pagseguro/{id}")
    public String retornoPagSeguro(@PathVariable Long id){
        return id.toString();
    }
}
