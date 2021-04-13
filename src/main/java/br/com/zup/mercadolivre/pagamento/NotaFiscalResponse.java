package br.com.zup.mercadolivre.pagamento;

import com.fasterxml.jackson.annotation.JsonCreator;

public class NotaFiscalResponse {
    private String numero;
    private String comprador;

    @JsonCreator
    public NotaFiscalResponse(NotaFiscal notaFiscal) {
        this.numero=notaFiscal.getNumero();
        this.comprador=notaFiscal.getComprador().getUsername();
    }

}
