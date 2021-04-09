package br.com.zup.mercadolivre.compra;

public enum MetodoPagamento{
    PAYPAL("paypal"),
    PAGSEGURO("pagseguro");

    private String gatway;

    MetodoPagamento(String gatway) {
        this.gatway=gatway;
    }

    public String getGatway() {
        return gatway;
    }
}
